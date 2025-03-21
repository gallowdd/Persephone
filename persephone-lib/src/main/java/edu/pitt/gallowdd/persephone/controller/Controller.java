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

package edu.pitt.gallowdd.persephone.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Phaser;
import java.util.function.Predicate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.agent.AgentTypeEnum;
import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.agent.PopulationManager;
import edu.pitt.gallowdd.persephone.agent.intializer.PopulationInitializer;
import edu.pitt.gallowdd.persephone.container.MixingContainerTypeMatchException;
import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.location.LocationListManager;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.location.initializer.LocationListManagerInitializer;
import edu.pitt.gallowdd.persephone.messaging.ControllerInitInMessage;
import edu.pitt.gallowdd.persephone.messaging.ControllerInitOutMessage;
import edu.pitt.gallowdd.persephone.messaging.ControllerInitOutMessage.ControllerInitOutType;
import edu.pitt.gallowdd.persephone.messaging.ControllerNormalizeInMessage;
import edu.pitt.gallowdd.persephone.messaging.Message;
import edu.pitt.gallowdd.persephone.messaging.Message.MessageType;
import edu.pitt.gallowdd.persephone.messaging.MessageFactory;
import edu.pitt.gallowdd.persephone.network.GenericNetwork;
import edu.pitt.gallowdd.persephone.overseer.OverseerControllerInterface;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.EnumNotFoundException;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.OverseerPhase;
import edu.pitt.gallowdd.persephone.util.Params;

/**
 * This is the default class for any container of agents, locations, and networks that will be running in its own thread
 * 
 * @author David Galloway
 **/
public class Controller implements Comparable<Controller>, Callable<ControllerOutput> {
  
  private static final Logger LOGGER = LogManager.getLogger(Controller.class.getName());
  
  /**
   * The value to prepend to all Ids of this type
   */
  public static final String ID_PREPEND = "CNTRLR";
  
  private final Id id;
  //private final Population<GenericAgent> agents = new Population<>();
  
//  private final Set<GenericAgent> agents = new HashSet<>();
  private final Map<AgentTypeEnum, PopulationManager> agents = new HashMap<>();
  private final Map<LocationTypeEnum, LocationListManager> locations = new HashMap<>();
  private final Map<Id, Set<Id>> locationToAgentSetMap = new HashMap<>();
  
  //private final Map<String, GenericLocation> locationIdMap = new ConcurrentHashMap<>();
  //private final Map<Id, GenericNetwork> networkIdMap = new HashMap<>();
  private final Set<GenericNetwork> networks = new HashSet<>();
  private final ConcurrentLinkedQueue<Message> threadCommQueue;
  private final OverseerControllerInterface overseer;
  
  private final Phaser phaser;
  
  /**
   * @param threadCommQueue
   * @param overseer 
   * @param phaser
   * @throws IdException if the id is not valid
   */
  public Controller(ConcurrentLinkedQueue<Message> threadCommQueue, OverseerControllerInterface overseer, Phaser phaser) throws IdException
  {
    super();
    this.id = Id.create(Controller.ID_PREPEND);
    this.threadCommQueue = threadCommQueue;
    this.overseer = overseer;
    this.phaser = phaser;
    
    //.stream().anyMatch(agent -> )
  }
  
  /**
   * @param id
   * @param threadCommQueue
   * @param overseer 
   * @param phaser
   * @throws IdException if the id is not valid
   */
  public Controller(Id id, ConcurrentLinkedQueue<Message> threadCommQueue, OverseerControllerInterface overseer, Phaser phaser) throws IdException
  {
    super();
    this.id = id;
    this.threadCommQueue = threadCommQueue;
    this.overseer = overseer;
    this.phaser = phaser;
  }
  
  /**
   * @param idString
   * @param threadCommQueue
   * @param overseer 
   * @param phaser
   * @throws IdException if the id is not valid
   */
  public Controller(String idString, ConcurrentLinkedQueue<Message> threadCommQueue, OverseerControllerInterface overseer, Phaser phaser) throws IdException
  {
    super();
    this.id = new Id(idString);
    this.threadCommQueue = threadCommQueue;
    this.overseer = overseer;
    this.phaser = phaser;
  }
  
  /**
   * @return the id
   */
  public Id getId()
  {
    return this.id;
  }
  
  /**
   * Check each LocationListManager to see if it contains the locationId. If one does, then get the GenericLocation and return it.
   * 
   * @param locationId
   * @return the location stored in this controller or null if not found
   */
  public GenericLocation getLocation(Id locationId)
  {
    for(Map.Entry<LocationTypeEnum, LocationListManager> pair : this.locations.entrySet())
    {
      if(pair.getValue().contains(locationId))
      {
        return pair.getValue().getLocation(locationId);
      }
    }
    
    return null;
  }
  
  /**
   * Check each LocationListManager to see if it contains the locationIdString. If one does, then get the GenericLocation 
   * and return it.
   * 
   * @param locationIdString
   * @return the location stored in this controller or null if not found
   */
  public GenericLocation getLocation(String locationIdString)
  {
    try
    {
      final Id locationId = new Id(locationIdString);
      return this.getLocation(locationId);
    }
    catch(IdException e)
    {
      return null;
    }
  }
  
  /**
   * 
   * @param networkId
   * @return the network stored in this controller or null if not found
   */
  public GenericNetwork getNetwork(Id networkId)
  {
    final Predicate<GenericNetwork> predicate = network -> network.getId().equals(networkId);
    final Optional<GenericNetwork> matchingNetwork = this.networks.stream().filter(predicate).findFirst();
    
    return matchingNetwork.orElse(null);
  }
  
  /**
   * 
   * @param networkIdString
   * @return the network stored in this controller or null if not found
   */
  public GenericNetwork getNetwork(String networkIdString)
  {
    try
    {
      final Id networkId = new Id(networkIdString);
      return this.getNetwork(networkId);
    }
    catch(IdException e)
    {
      return null;
    }
  }
  
  /**
   * 
   * @param agentId
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean containsAgent(Id agentId)
  {
    boolean retVal = false;
    
    for(Map.Entry<AgentTypeEnum, PopulationManager> pair : this.agents.entrySet())
    {
      if(pair.getValue().contains(agentId))
      {
        retVal = true;
        break;
      }
    }
    return retVal;
  }
  
  /**
   * 
   * @param agentIdString
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean containsAgent(String agentIdString)
  {
    try
    {
      final Id agentId = new Id(agentIdString);
      return this.containsAgent(agentId);
    }
    catch(IdException e)
    {
      return false;
    }
  }
  
  /**
   * Check each PopulationManager to see if it contains the agentId. If one does, then get the GenericAgent and return it.
   * 
   * @param agentId
   * @return the agent stored in this controller or null if not found
   */
  public GenericAgent getAgent(Id agentId)
  {
    for(Map.Entry<AgentTypeEnum, PopulationManager> pair : this.agents.entrySet())
    {
      if(pair.getValue().contains(agentId))
      {
        return pair.getValue().getAgent(agentId);
      }
    }
    
    return null;
  }
  
  /**
   * Check each PopulationManager to see if it contains the agentId. If one does, then get the GenericAgent and return it.
   * 
   * @param agentIdString
   * @return the agent stored in this controller or null if not found
   */
  public GenericAgent getAgent(String agentIdString)
  {
    try
    {
      final Id agentId = new Id(agentIdString);
      return this.getAgent(agentId);
    }
    catch(IdException e)
    {
      return null;
    }
  }
  
  /**
   * Add the agent to the set of agents in this controller
   * 
   * @param agent the agent to add
   */
  public void addAgent(GenericAgent agent)
  {
    if(this.agents.containsKey(agent.getAgentType()))
    {
      this.agents.get(agent.getAgentType()).addAgent(agent);
    }
    else
    {
      PopulationManager populationMgr =  null;
      try
      {
        populationMgr = new PopulationManager();
      }
      catch(IdException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      populationMgr.addAgent(agent);
      this.agents.put(agent.getAgentType(), populationMgr);
    }
  }
  
  /**
   * Removes the agent the Controller's set of agents
   * 
   * @param agent the agent to remove
   */
  public void removeAgent(GenericAgent agent)
  {
   if(this.agents.containsKey(agent.getAgentType()))
   {
     this.agents.get(agent.getAgentType()).removeAgent(agent.getId());
   }
  }
  
  /**
   * Removes the agent the Controller's set of agents
   * 
   * @param agentId the Id of the agent to remove
   */
  public void removeAgent(Id agentId)
  {
    for(Map.Entry<AgentTypeEnum, PopulationManager> pair : this.agents.entrySet())
    {
      if(pair.getValue().contains(agentId))
      {
        pair.getValue().removeAgent(agentId);
      }
    }
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param agentIdString the idString of the agent to remove
   */
  public void removeAgent(String agentIdString)
  {
    try
    {
      final Id agentId = new Id(agentIdString);
      this.removeAgent(agentId);
    }
    catch(IdException e)
    {
      // Id is invalid so do nothing
      return;
    }
  }
  
  /**
   * Assign an agent of to a location. Both the agent and the location, must be present
   * in this controller or the method will return false immediately.
   * 
   * @param agentId the ID of the agent to add to the location
   * @param locationId the ID of the location
   * @return true if the agent is added, false if the agent is not assigned (for any reason)
   */
  public boolean assignAgentToLocation(Id agentId, Id locationId)
  {
    boolean retVal = false;
    
    if(!this.containsAgent(agentId))
    {
      return false;
    }
    
    if(!this.containsLocation(locationId))
    {
      return false;
    }
    
    try
    {
      for(Map.Entry<LocationTypeEnum, LocationListManager> pair : this.locations.entrySet())
      {
        if(pair.getValue().contains(locationId))
        {
          pair.getValue().getLocation(locationId).getMixingContainer().add(agentId);
          retVal = true;
          break;
        }
      }
    }
    catch(MixingContainerTypeMatchException e)
    {
      Controller.LOGGER.error(e);
    }
    
    return retVal;
  }
  
  /**
   * Determines whether or not this Controller contains the locationId specified
   * 
   * @param locationId
   * @return {@code true} if the internal map contains the location ID, {@code false} otherwise
   */
  public boolean containsLocation(Id locationId)
  {
    boolean retVal = false;
    
    for(Map.Entry<LocationTypeEnum, LocationListManager> pair : this.locations.entrySet())
    {
      if(pair.getValue().contains(locationId))
      {
        retVal = true;
        break;
      }
    }
    return retVal;
  }
  
  /**
   * Determines whether or not this Controller contains the locationId specified
   * 
   * @param locationIdString
   * @return {@code true} if the internal map contains the location ID, {@code false} otherwise
   */
  public boolean containsLocation(String locationIdString)
  {
    try
    {
      final Id locationId = new Id(locationIdString);
      return this.containsLocation(locationId);
    }
    catch(IdException e)
    {
     return false;
    }
  }
  
  /**
   * Maps location's Id to the location itself in this table.
   * 
   * @param loc the location to add
   */
  public void addLocation(GenericLocation loc)
  {
    if(this.locations.containsKey(loc.getLocationType()))
    {
      this.locations.get(loc.getLocationType()).addLocation(loc);
    }
    else
    {
      LocationListManager locListMgr =  null;
      try
      {
        locListMgr = new LocationListManager();
      }
      catch(IdException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
      locListMgr.addLocation(loc);
      this.locations.put(loc.getLocationType(), locListMgr);
    }
  }
  
  /**
   * Maps location's Id to the location itself in this table.
   * 
   * @param loc the location to add
   */
  public void removeLocation(GenericLocation loc)
  {
    if(this.locations.containsKey(loc.getLocationType()))
    {
      this.locations.get(loc.getLocationType()).removeLocation(loc.getId());
    }
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param locationId the Id of the location to remove
   */
  public void removeLocation(Id locationId)
  {
    for(Map.Entry<LocationTypeEnum, LocationListManager> pair : this.locations.entrySet())
    {
      if(pair.getValue().contains(locationId))
      {
        pair.getValue().removeLocation(locationId);
      }
    }
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param locationIdString the idString of the location to remove
   */
  public void removeLocation(String locationIdString)
  {
    try
    {
      final Id locationId = new Id(locationIdString);
      this.removeLocation(locationId);
    }
    catch(IdException e)
    {
      Controller.LOGGER.info(e);
    }
  }
  
  /**
   * Add the network to the set of networks in this controller
   * 
   * @param network the network to add
   * @return the @code{true} if the network was added, or @code{false} if the network is already in the set
   */
  public boolean addNetwork(GenericNetwork network)
  {
    return this.networks.add(network);
  }
  
  private void advanceTimestep(TickType tick)
  {
    // Determine which agents need to be moved to new locations
    switch(tick)
    {
      case INIT:
//        this.agentIdMap.forEach((key, value) ->
//          {
            //TODO startup
//          });
        break;
      case HOUR:
//        this.simCalendarDay.add(Calendar.HOUR, 1);
//        if(++this.simHour == 24)
//        {
//          this.simHour = 0;
//          if(++this.simDay == Params.getDays())
//          {
//            //TODO start shutdown
//          }
//        
//        }
        break;
      case DAY:
//        this.simCalendarDay.add(Calendar.DAY_OF_MONTH, 1);
//        if(++this.simDay == Params.getDays())
//        {
//          //TODO start shutdown
//        }
        break;
    }
  }
  
  
  public enum TickType 
  {
    INIT,
    HOUR,
    DAY
  }
  
  private void initialize()
  {
    final int paramSynthEnvSize = Params.getSyntheticEnvDescriptors().size();
    // This list will keep track of the indices of the synthetic environments in the Parameter XML that we are using for this Controller
    final List<Integer> syntheticEnvironmentIndexList = new ArrayList<>();
    
    while(this.threadCommQueue.peek() != null && this.threadCommQueue.peek() instanceof ControllerInitInMessage)
    {
      Message msg = this.threadCommQueue.poll();
      if(msg == null)
      {
        break;
      }
      ControllerInitInMessage cntrllrMsg = (ControllerInitInMessage)msg;
      
      // Loop over all of the Synthetic Environments that are in the message
      cntrllrMsg.getSyntheticEnvironments().forEach(synthEnv -> {
        String country = null;
        String version = null;
        String popId = null;
        Controller.LOGGER.trace(this.id.getIdString() + ": " + synthEnv.toString());
        
        int synthEcosystemIndex = -1;
        for(int i = 0; i < paramSynthEnvSize; ++i)
        {
          country = Params.getSyntheticEnvDescriptors().get(i).getCountry().value();
          version = Params.getSyntheticEnvDescriptors().get(i).getVersion().value();
          popId = Params.getSyntheticEnvDescriptors().get(i).getIdentifier();
          
          if(country.equals(synthEnv.country()) &&
             version.equals(synthEnv.version()) &&
             popId.equals(synthEnv.identifier()))
          {
            synthEcosystemIndex = i;
            syntheticEnvironmentIndexList.add(synthEcosystemIndex);
            break;
          }
          else
          {
            country = null;
            version = null;
            popId = null;
          }
        }
        
        if(synthEcosystemIndex == -1)
        {
          // ABORT
          Controller.LOGGER.fatal("Unable to find a Synthetic Ecosystem that matches [id: " + synthEnv.identifier() + 
              "], [country: " + synthEnv.country() + "], [version: " + synthEnv.version() + "]");
          System.exit(1);
        }
        
        this.readPopulationFiles(synthEcosystemIndex);
        // With all of the agents being read, we now register them as observers for each population
        this.agents.entrySet().parallelStream().forEach(entry -> entry.getValue().registerObservers());
        
        this.readLocationFiles(synthEcosystemIndex);
        // With all of the locations being read, we now register them as observers for each locationManager
        this.locations.entrySet().parallelStream().forEach(entry -> {
            System.out.println(entry.getKey().toString() + "-> registerObservers");
            System.out.println(entry.getValue().getId());
            entry.getValue().registerObservers();
          });
      });
    }
    this.createDynamicLocations();
    this.createSublocations();
    this.assignLocationsToAgents();
  }
  
  private void normalize()
  {
    int locRemoveTot = 0;
    int networkRemoveTot = 0;
    
    while(this.threadCommQueue.peek() != null)// && this.threadCommQueue.peek() instanceof ControllerNormalizeInMessage)
    {
      Message msg = this.threadCommQueue.poll();
      if(msg.getType() == MessageType.CNTRLLR_INIT_NRMLZ_IN)
      {
        ControllerNormalizeInMessage cntrllrNrmlzMsg = (ControllerNormalizeInMessage)msg;
        
        switch(cntrllrNrmlzMsg.getNomalizeInType())
        {
          case NETWORK_REMOVE:
            //this.removeNetwork();
            ++networkRemoveTot;
            break;
          case PLACE_REMOVE:
            this.removeLocation(cntrllrNrmlzMsg.getToDeleteLocationId());
            ++locRemoveTot;
            break;
          case UNSET:
            Controller.LOGGER.fatal("Unrecognized Request Type for Message, " + cntrllrNrmlzMsg.toString());
            System.exit(Constants.EX_SOFTWARE);
            break;
          default:
            Controller.LOGGER.fatal("Unrecognized Request Type for Message, " + cntrllrNrmlzMsg.toString());
            System.exit(Constants.EX_SOFTWARE);
            break;
          
        }
      }
      else
      {
        Controller.LOGGER.fatal("INCORRECT NORMALIZATION MESSAGE. Expected " + MessageType.CNTRLLR_INIT_NRMLZ_IN.toString() + ", but received " + msg.getType().toString());
        System.exit(1);
      }
    }
    Controller.LOGGER.info("Controller " + this.id.getIdString() + " removed " + networkRemoveTot + " networks during Normalization Phase");
    Controller.LOGGER.info("Controller " + this.id.getIdString() + " removed " + locRemoveTot + " locations during Normalization Phase");
  }
  
  private void assignLocationsToAgents()
  {
    //this.readPopulationFiles();
  }
  
  private void createDynamicLocations()
  {
    //this.readPopulationFiles();
  }
  
  private void createSublocations()
  {
    //this.readPopulationFiles();
  }
  
  private void readPopulationFiles(int synthEcosystemIndex)
  {
    final List<Id> addedAgentIds = new LinkedList<>();
    final List<GenericAgent> addedAgents = new LinkedList<>();
    
    PopulationInitializer.initialize(addedAgents, Params.getSyntheticEnvDescriptors().get(synthEcosystemIndex), this.overseer.getSimDateTime().toLocalDate());
    
    for(GenericAgent agent : addedAgents)
    {
      this.addAgent(agent);
      addedAgentIds.add(agent.getId());
    }
    
    Controller.LOGGER.debug("addedAgents.size() = " + addedAgents.size());
    Controller.LOGGER.debug("addedAgentIds.size() = " + addedAgentIds.size());
    // Let the overseer know all of the agents we added by adding them to the queue
    addedAgentIds.forEach(agentId -> {
      ControllerInitOutMessage retMsg = (ControllerInitOutMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_OUT, this.id);
      retMsg.setInitOutType(ControllerInitOutType.AGENT_ADD);
      retMsg.setAgentId(agentId);
      this.threadCommQueue.add(retMsg);
    });
    
    Controller.LOGGER.debug("threadCommQueue.size() = " + this.threadCommQueue.size());
  }
  
  private void readLocationFiles(int synthEcosystemIndex)
  {
    final List<Id> addedLocationIds = new LinkedList<>();
    final List<GenericLocation> addedLocations = new LinkedList<>();
      
    LocationListManagerInitializer.initialize(addedLocations, Params.getSyntheticEnvDescriptors().get(synthEcosystemIndex));
    
    for(GenericLocation location : addedLocations)
    {
      this.addLocation(location);
      addedLocationIds.add(location.getId());
    }
    
    Controller.LOGGER.debug("addedLocations.size() = " + addedLocations.size());
    Controller.LOGGER.debug("addedLocationIds.size() = " + addedLocationIds.size());
    // Let the overseer know all of the agents we added by adding them to the queue
    addedLocationIds.forEach(locationId -> {
      ControllerInitOutMessage retMsg = (ControllerInitOutMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_OUT, this.id);
      retMsg.setInitOutType(ControllerInitOutType.PLACE_ADD);
      retMsg.setLocationId(locationId);
      this.threadCommQueue.add(retMsg);
    });
    
    Controller.LOGGER.debug("threadCommQueue.size() = " + this.threadCommQueue.size());
  }
  
  private void readAgentToLocationFiles(int synthEcosystemIndex)
  {
    final List<Id> addedAgentIds = new LinkedList<>();
    final List<GenericAgent> addedAgents = new LinkedList<>();
    
    PopulationInitializer.initialize(addedAgents, Params.getSyntheticEnvDescriptors().get(synthEcosystemIndex), this.overseer.getSimDateTime().toLocalDate());
    
    for(GenericAgent agent : addedAgents)
    {
      this.addAgent(agent);
      addedAgentIds.add(agent.getId());
    }
    
    Controller.LOGGER.debug("addedAgents.size() = " + addedAgents.size());
    Controller.LOGGER.debug("addedAgentIds.size() = " + addedAgentIds.size());
    // Let the overseer know all of the agents we added by adding them to the queue
    addedAgentIds.forEach(agentId -> {
      ControllerInitOutMessage retMsg = (ControllerInitOutMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_OUT, this.id);
      retMsg.setInitOutType(ControllerInitOutType.AGENT_ADD);
      retMsg.setAgentId(agentId);
      this.threadCommQueue.add(retMsg);
    });
    
    Controller.LOGGER.debug("threadCommQueue.size() = " + this.threadCommQueue.size());
  }
  
  /* (non-Javadoc)
   * @see java.util.concurrent.Callable#call()
   */
  @Override
  public ControllerOutput call() throws InterruptedException
  {
    try
    {
      Controller.LOGGER.debug(this.id.getIdString() + " PHASE[" + OverseerPhase.valueOf(this.phaser.getPhase()).toString() + "]. Waiting for others to advance ...");
      this.initialize();
      //Controller.LOGGER.debug(this.id.getIdString() + " PHASE[" + OverseerPhase.valueOf(this.phaser.getPhase()).toString() + "]. Waiting for others to advance ...");
      this.phaser.arriveAndAwaitAdvance();
      
      Controller.LOGGER.debug(this.id.getIdString() + " PHASE[" + OverseerPhase.valueOf(this.phaser.getPhase()).toString() + "]. Waiting for others to advance ...");
      this.phaser.arriveAndAwaitAdvance();
      Controller.LOGGER.debug(this.id.getIdString() + " PHASE[" + OverseerPhase.valueOf(this.phaser.getPhase()).toString() + "]. Waiting for others to advance ...");
      this.phaser.arriveAndAwaitAdvance();
      this.normalize();
      Controller.LOGGER.debug(this.id.getIdString() + " PHASE[" + OverseerPhase.valueOf(this.phaser.getPhase()).toString() + "]. Waiting for others to advance ...");
      this.phaser.arriveAndAwaitAdvance();
      
      this.assignLocationsToAgents();
    }
    catch(RuntimeException| EnumNotFoundException e)
    {
      this.phaser.arriveAndDeregister();
      throw new InterruptedException(e.getMessage());
    }
    
    return null;
  }
  
  @Override
  public int compareTo(Controller o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
