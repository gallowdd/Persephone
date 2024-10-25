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

import java.util.HashMap;
import java.util.Map;

import com.google.common.eventbus.Subscribe;

import edu.pitt.gallowdd.persephone.event.MixEvent;
import edu.pitt.gallowdd.persephone.util.Id;

/**
 * A group of agents
 * 
 * @author David Galloway
 *
 * @param <T> the type of agents in this population (must be a subclass of GenericAgent)
 */
public class PopulationOld<T extends GenericAgent> {
  
  private final Map<Id, T> agentIdMap;
  
  /**
   * 
   */
  public PopulationOld()
  {
    this.agentIdMap = new HashMap<>();
  }
  
  /**
   * 
   * @param agentId
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean contains(Id agentId)
  {
    return this.agentIdMap.containsKey(agentId);
  }
  
  /**
   * 
   * @param agentId
   * @return the agent stored in this controller or null if not found
   */
  public T getAgent(Id agentId)
  {
    return this.agentIdMap.get(agentId);
  }
  
  /**
   * Maps agent's Id to the agent itself in this table.
   * 
   * @param agent the agent to add
   * @return the previous agent associated with key, or @code{null} if there was no mapping for key
   */
  public T addAgent(T agent)
  {
    return this.agentIdMap.put(agent.getId(), agent);
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param agentId the Id of the agent to remove
   * @return the removed agent or @code{null} if not found
   */
  public T removeAgent(Id agentId)
  {
    return this.agentIdMap.remove(agentId);
  }
  
  /**
   * 
   * @param mixEvent
   */
  @Subscribe
  public void handleMixEvent(MixEvent mixEvent)
  {
    
  }
}
