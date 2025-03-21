/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2021  David Galloway / University of Pittsburgh
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package edu.pitt.gallowdd.persephone.agent;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.event.AgentEvent;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * A Population holds an ArrayList of at most {@code Constants.POPULATION_MAX_SUBSCRIBER_COUNT} and sends events out to them as necessary.
 * The Population manages a particular type ({@code AgentTypeEnum}) of agents. If a user attempts to add an agent not of the type managed, 
 * an exception will be raised.
 * 
 * @author David Galloway
 *
 */
public class Population extends Object {
  
  private static final Logger LOGGER = LogManager.getLogger(Population.class.getName());
  
  private final Id id;
  private final PublishSubject<AgentEvent> agentEventPublishSubject = PublishSubject.create();
  private final AgentTypeEnum agentTypeManaged;
  private final List<GenericAgent> agentArrLst = new ArrayList<>(Constants.POPULATION_MAX_SUBSCRIBER_COUNT);
  private boolean isInitialized;
  
  /**
   * 
   * @param agentTypeManaged
   * @throws IdException if id String is invalid
   */
  public Population(AgentTypeEnum agentTypeManaged) throws IdException
  {
    this.id = Id.create(agentTypeManaged.toString() + "POP");
    this.agentTypeManaged = agentTypeManaged;
    this.isInitialized = false;
  }
  
  /**
   * Add an agent to this Population. If the agent's type is not the same as the type being managed, 
   * this will throw a PopulationTypeMatchException. 
   * If this Population doesn't have space to add another agent, it will throw a PopulationSizeExceededException
   * 
   * @param agent the GenericAgent to add to this Population
   * @return {@code true} if the location is added correctly to this Location Manager's internal list (as specified by {@code Collection.add})
   * @throws PopulationSizeExceededException if agentArrLst is already at {@code size() == Constants.MANAGER_MAX_SUBSCRIBER_COUNT}
   * @throws PopulationTypeMatchException the agentType of the agent does not match the agentTypeManaged of this AgentManager
   */
  public boolean addAgent(GenericAgent agent) throws PopulationSizeExceededException, PopulationTypeMatchException
  {
    if(agent.getAgentType() == this.agentTypeManaged)
    {
      if(this.agentArrLst.size() < Constants.POPULATION_MAX_SUBSCRIBER_COUNT)
      {
        boolean isAdded =  this.agentArrLst.add(agent);
        
        // If we have already done the bulk registration of observers, then from now on we must register new ones as they are added
        if(this.isInitialized)
        {
          this.agentEventPublishSubject.subscribe(agent);
        }
        return isAdded;
      }
      else
      {
        throw new PopulationSizeExceededException(this.id.getIdString());
      }
    }
    
    throw new PopulationTypeMatchException(this.id.getIdString(), this.agentTypeManaged.toString());
  }
  
  /**
   * Remove a GenericAgent from this Population
   * 
   * @param agent the agent to remove
   * @return {@code true} if the agent is removed from this Population, {@code false} otherwise
   */
  public boolean removeAgent(GenericAgent agent)
  {
    if(this.agentArrLst.contains(agent))
    {
      return this.agentArrLst.remove(agent);
    }
    
    return false;
  }
  
  /**
   * @param agentEventPublisher
   */
   public void observe(PublishSubject<AgentEvent> agentEventPublisher)
   {
     agentEventPublisher.subscribe(this.agentEventPublishSubject);
   }
  
  /**
   * Remove a GenericAgent from this Population
   * 
   * @param agentId the id of the agent to remove
   * @return {@code true} if the agent is removed from this Population, {@code false} otherwise
   */
  public boolean removeAgent(Id agentId)
  {
    GenericAgent agentToRemove = null;
    for(int i = 0; i < this.agentArrLst.size(); ++i)
    {
      if(this.agentArrLst.get(i).getId().equals(agentId))
      {
        agentToRemove = this.agentArrLst.get(i);
        break;
      }
    }
    
    if(agentToRemove != null)
    {
      return this.agentArrLst.remove(agentToRemove);
    }
    
    return false;
  }
  
  /**
   * It is significantly faster to add all of the listeners to a list first, then use parallelStream to bulk register.
   * This method also sets the isInitialized flag so additional listeners will be added dynamically.
   * 
   * This method should be called after all of the initial listeners are added
   * 
   * @return true if the bulk register is successful, false otherwise (this Population has already performed a bulk registration)
   */
  public boolean registerObservers()
  {
    if(!this.isInitialized)
    {
      this.agentArrLst.parallelStream().forEach(tmpObserver -> {
        this.agentEventPublishSubject.subscribe(tmpObserver);
      });
      this.isInitialized = true;
      return true;
    }
    
    return false;
  }
}
