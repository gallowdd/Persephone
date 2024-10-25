package edu.pitt.gallowdd.persephone.agent;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * A group of Populations and Agents. The PopulationManager will keep a map of all agentIds to Agents. 
 * It will also add locations to Populations as they are added to the list.
 * 
 * Note: There will be one PopulationManager per AgentType in a given Controller
 * 
 * @author David Galloway
 */
public class PopulationManager {
  
  private static final Logger LOGGER = LogManager.getLogger(PopulationManager.class.getName());
  
  private final Id id;
  private final EventBus eventBus;
  private final Map<Id, GenericAgent> agentIdToAgentMap = new HashMap<>();
  private final Set<Population> populationSet = new HashSet<>();
  private boolean isInitialized;
  
  /**
   * 
   * @throws IdException if id String is invalid
   */
  public PopulationManager() throws IdException
  {
    this.id = Id.create("POPMGR");
    this.eventBus = new EventBus(this.id.getIdString());
    this.isInitialized = false;
  }
  
  /**
   * Determines whether or not this PopulationManager contains the agentId specified
   * 
   * @param agentId the agentId to search for
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean contains(Id agentId)
  {
    return this.agentIdToAgentMap.containsKey(agentId);
  }
  
  /**
   * Determines whether or not this LocationListManager contains the agentId specified
   * by the agentIdString
   * 
   * @param agentIdString the String representation of the agentId
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean contains(String agentIdString)
  {
    try
    {
      return this.agentIdToAgentMap.containsKey(new Id(agentIdString));
    }
    catch(IdException e)
    {
      // The agentIdString is invalid, so it can't be mapped to anything
      return false;
    }
  }
  
  /**
   * 
   * @param agentId
   * @return the agent stored in this controller or null if not found
   */
  public GenericAgent getAgent(Id agentId)
  {
    return this.agentIdToAgentMap.get(agentId);
  }
  
  /**
   * 
   * @param agentIdString
   * @return the agent stored in this controller or null if not found
   */
  public GenericAgent getAgent(String agentIdString)
  {
    try
    {
      return this.agentIdToAgentMap.get(new Id(agentIdString));
    }
    catch(IdException e)
    {
      // The locationIdString is invalid, so it can't be mapped to anything
      return null;
    }
  }
  
  /**
   * Maps agent's Id to the agent itself in this table.
   * 
   * @param agent the agent to add
   * @return the previous agent associated with key, or @code{null} if there was no mapping for key
   */
  public GenericAgent addAgent(GenericAgent agent)
  {
    boolean isAdded = false;
    
    for(Population population : this.populationSet)
    {
      try
      {
        isAdded = population.addAgent(agent);
        break;
      }
      catch(PopulationSizeExceededException e)
      {
        // If this PopualationManager has already done the bulk registration of its
        // listeners, then make sure that the Population has performed ITS bulk registration
        if(this.isInitialized)
        {
          if(population.registerListeners())
          {
            this.eventBus.register(population);
          }
        }
        
        // Try next one
      }
      catch(PopulationTypeMatchException e)
      {
        PopulationManager.LOGGER.fatal(e);
        throw new RuntimeException(e);
      }
    }

    // If all of the LocationManagers are full, create a new one and add it
    if(!isAdded)
    {
      Population newPopulation;
      try
      {
        newPopulation = new Population(agent.getAgentType());
        newPopulation.addAgent(agent);
      }
      catch(PopulationSizeExceededException | PopulationTypeMatchException | IdException e)
      {
        PopulationManager.LOGGER.fatal(e);
        throw new RuntimeException(e);
      }
      this.populationSet.add(newPopulation);
    }

    return this.agentIdToAgentMap.put(agent.getId(), agent);
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param agentId the id of the agent to remove
   * @return the removed agent or @code{null} if not found
   */
  public GenericAgent removeAgent(Id agentId)
  {
    return this.agentIdToAgentMap.remove(agentId);
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param agentIdString the idString of the agent to remove
   * @return the removed agent or @code{null} if not found
   */
  public GenericAgent removeAgent(String agentIdString)
  {
    try
    {
      return this.agentIdToAgentMap.remove(new Id(agentIdString));
    }
    catch(IdException e)
    {
      // The agentIdString is invalid, so it can't be mapped to anything
      return null;
    }
  }
  
  /**
   * It is significantly faster to add all of the listeners to a list first, then use a parallelStream to bulk register.
   * This method also sets the isInitialized flag so additional listeners will be added dynamically.
   * 
   * This method should be called after all of the initial listeners are added
   * 
   * @return true if the bulk register is successful, false otherwise (this PopulationManager has already performed a bulk registration)
   */
  public boolean registerListeners()
  {
    if(!this.isInitialized)
    {
      this.populationSet.parallelStream().forEach(tmpListener ->
      {
        tmpListener.registerListeners();
        this.eventBus.register(tmpListener);
      });
      this.isInitialized = true;
      return true;
    }
    
    return false;
  }
}
