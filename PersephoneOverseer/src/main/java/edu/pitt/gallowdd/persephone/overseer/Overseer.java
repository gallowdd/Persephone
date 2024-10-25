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

package edu.pitt.gallowdd.persephone.overseer;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Phaser;

import javax.xml.transform.stream.StreamSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.controller.Controller;
import edu.pitt.gallowdd.persephone.controller.ControllerOutput;
import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.messaging.ControllerInitInMessage;
import edu.pitt.gallowdd.persephone.messaging.ControllerInitOutMessage;
import edu.pitt.gallowdd.persephone.messaging.ControllerNormalizeInMessage;
import edu.pitt.gallowdd.persephone.messaging.ControllerNormalizeInMessage.ControllerNormalizeInType;
import edu.pitt.gallowdd.persephone.messaging.Message;
import edu.pitt.gallowdd.persephone.messaging.Message.MessageType;
import edu.pitt.gallowdd.persephone.messaging.MessageFactory;
import edu.pitt.gallowdd.persephone.messaging.record.SynthEnvRec;
import edu.pitt.gallowdd.persephone.network.GenericNetwork;

import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.EnumNotFoundException;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.OverseerPhase;
import edu.pitt.gallowdd.persephone.util.Params;
import edu.pitt.gallowdd.persephone.util.Utils;
import edu.pitt.gallowdd.persephone.xml.synthenv.SyntheticEnvironment;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

/**
 * The Overseer is the process that runs on a single machine. It spawns threads for each Controller and keeps them synchronized.
 * 
 * Since all of the threads are on the same machine as the Overseer, they all have access to the same file system.
 * 
 * @author David Galloway
 *
 */
public class Overseer implements OverseerControllerInterface { //implements CntrllrRPCRcvr {

  private static final Logger LOGGER = LogManager.getLogger(Overseer.class.getName());
  
  
  /**
   * The value to prepend to all Ids of this type
   */
  public static final String ID_PREPEND = "OVRSR";
  
  private static int cpuCount;
  private static int logicalCpuCount;// = Params.getSyntheticEnvironments().size();
  private static long totalMemory;
  private static long availMemory;

  //private static final String[] PHASES = {"Initialize", "Next", "OutOfRange"};
  
  private Id id = null;
  private SynthEnvRec[][] taskSynthEnvArr = null;
  
  /*
   * These HashMaps are used to link each agent and place to the actual Controller that handles 
   * each one
   * 
   * After initialization, these are the only maps that will be populated, as the individual controllers
   *  will thereafter handle the agents and locations
   */
  private Map<Id, Integer> controllerToTaskIndexMap = null;
  private Id[] taskIndexToControllerIdArr = null;
  private Map<Id, Id> agentToControllerMap = null;
  private Map<Id, Id> locationToControllerMap = null;
  private Map<Id, Id> networkToControllerMap = null;
  private Phaser phaser = null;
  private List<FutureTask<ControllerOutput>> taskList = null;
  final private LocalDateTime simDateTime;
  
  
  private ConcurrentLinkedQueue<Message>[] threadCommArr = null;
  
  static
  {
    SystemInfo sysInfo = new SystemInfo();
    HardwareAbstractionLayer hdwrAbstrLyr = sysInfo.getHardware();
    CentralProcessor cpu = hdwrAbstrLyr.getProcessor();
    
    Overseer.cpuCount = cpu.getPhysicalProcessorCount();
    Overseer.logicalCpuCount= cpu.getLogicalProcessorCount();
    Overseer.totalMemory = hdwrAbstrLyr.getMemory().getTotal();
    Overseer.availMemory = hdwrAbstrLyr.getMemory().getAvailable();
  }
  
  /**
   * Constructor
   * 
   * @throws IdException if the id is not valid
   */
  @SuppressWarnings("unchecked")
  public Overseer() throws IdException
  {
    super();
    this.id = Id.create(Overseer.ID_PREPEND);
    
    int month = Params.getStartDate().getMonth().getValue();
    int dayOfMonth = Params.getStartDate().getDayOfMonth();
    int year = Params.getStartDate().getYear();
    this.simDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0);
    
    this.controllerToTaskIndexMap = new ConcurrentHashMap<>();
    this.agentToControllerMap = new ConcurrentHashMap<>();
    this.locationToControllerMap = new ConcurrentHashMap<>();
    this.networkToControllerMap = new ConcurrentHashMap<>();
    
    this.taskSynthEnvArr = this.initTaskSynthEnvArr();
    
    // After I know how many threads (tasks) I will be using (and Controllers I will be running), I can 
    //  create these arrays
    this.taskIndexToControllerIdArr = new Id[this.taskSynthEnvArr.length];
    this.threadCommArr = new ConcurrentLinkedQueue[this.taskSynthEnvArr.length];
    
    for(int i = 0; i < this.threadCommArr.length; ++i)
    {
      this.threadCommArr[i] = new ConcurrentLinkedQueue<Message>();
    }
  }
  
  /**
   * Constructor
   * @param idString
   * 
   * @throws IdException if the id is not valid
   */
  @SuppressWarnings("unchecked")
  public Overseer(String idString) throws IdException
  {
    super();
    this.id = new Id(idString);
    
    int month = Params.getStartDate().getMonth().getValue();
    int dayOfMonth = Params.getStartDate().getDayOfMonth();
    int year = Params.getStartDate().getYear();
    this.simDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0);
    
    this.controllerToTaskIndexMap = new ConcurrentHashMap<>();
    this.agentToControllerMap = new ConcurrentHashMap<>();
    this.locationToControllerMap = new ConcurrentHashMap<>();
    this.networkToControllerMap = new ConcurrentHashMap<>();
    
    this.taskSynthEnvArr = this.initTaskSynthEnvArr();
    
    // After I know how many threads (tasks) I will be using (and Controllers I will be running), I can 
    //  create these arrays
    this.taskIndexToControllerIdArr = new Id[this.taskSynthEnvArr.length];
    this.threadCommArr = new ConcurrentLinkedQueue[this.taskSynthEnvArr.length];
    
    for(int i = 0; i < this.threadCommArr.length; ++i)
    {
      this.threadCommArr[i] = new ConcurrentLinkedQueue<Message>();
    }
  }
  
//  // Maps population name string to the aggregate info about that population
//  private HashMap<String, PopulationInfo> controllerMap = new HashMap<>();
  
  private HashMap<String, String> cntrllrQueueNameMap = new HashMap<>();
  
  
////  @Override
////  public void handleDelivery(String consumerTag, Envelope envelope,
////                             AMQP.BasicProperties properties, byte[] body) throws IOException 
////  {
////    String message = new String(body, "UTF-8");
////    System.out.println(" [x] Received '" + envelope.getRoutingKey() + "':'" + message + "'");
////  }
//  
//  @Override
//  public void initResponse(String message)
//  {
//    System.out.println("initResponse: Received message >>>>> " + message);
//    
//  }
  
  
  private SynthEnvRec[][] initTaskSynthEnvArr()
  { 
    final List<SynthEnvRec> myEnvironmentList = new ArrayList<>();
    final List<List<SynthEnvRec>> tempRetVal = new ArrayList<List<SynthEnvRec>>();
    SynthEnvRec[][] retVal = null;
    
    int synthEnvCount = Params.getSyntheticEnvDescriptors().size();
    long totMemRequired = 0L;
    for(int i = 0; i < synthEnvCount; ++i)
    {
      long memRequired = Overseer.calculateApproximateMemoryRequirementsForSyntheticEnvironment(
          Params.getSyntheticEnvDescriptors().get(i).getCountry().value(),
          Params.getSyntheticEnvDescriptors().get(i).getVersion().value(),
          Params.getSyntheticEnvDescriptors().get(i).getIdentifier());
      SynthEnvRec temp = new SynthEnvRec(Params.getSyntheticEnvDescriptors().get(i).getCountry().value(), 
          Params.getSyntheticEnvDescriptors().get(i).getVersion().value(), 
          Params.getSyntheticEnvDescriptors().get(i).getIdentifier(), 
          memRequired);
      myEnvironmentList.add(temp);
      Overseer.LOGGER.debug("Added syntheticEnvironment: " + temp.toString());
      
      totMemRequired += memRequired;
    }
    
    // Memory check
    if(totMemRequired > Overseer.availMemory)
    {
      Overseer.LOGGER.fatal("Total Estimated Memory Required [" +
          totMemRequired + "] exceeds available Memory [" +
          Overseer.availMemory + "]");
      System.exit(1);
    }
    
    // Bug Check
    if(synthEnvCount != myEnvironmentList.size())
    {
      Overseer.LOGGER.fatal("Problem adding Synthetic Environments to List: expected " + synthEnvCount +
          ", but instead added " + myEnvironmentList.size() + " items.");
      System.exit(1);
    }
    
    // Debug output list
    Overseer.LOGGER.debug("---------------------------------------------- myEnvironmentList ----------------------------------------------");
    myEnvironmentList.forEach(item -> Overseer.LOGGER.debug(item.toString()));
    Overseer.LOGGER.debug("--------------------------------------------------------------------------------------------------------------");
    
    myEnvironmentList.sort((synthEnv1, synthEnv2) -> Long.compare(synthEnv1.estMemRequirement(), synthEnv2.estMemRequirement()));
    // Debug output list
    Overseer.LOGGER.debug("------------------------------------------ Sorted: myEnvironmentList ------------------------------------------");
    myEnvironmentList.forEach(item -> Overseer.LOGGER.debug(item.toString()));
    Overseer.LOGGER.debug("--------------------------------------------------------------------------------------------------------------");
    long minMemory = -1L;
    long currentMemory = 0L;
    int currentTaskIndex = 0;
    
    // Start by getting the largest memory requirement and making that one thread
    List<SynthEnvRec> tempArrList = new ArrayList<>();
    minMemory = myEnvironmentList.get(myEnvironmentList.size() - 1).estMemRequirement();
    currentMemory = minMemory;
    tempArrList.add(myEnvironmentList.get(myEnvironmentList.size() - 1));
    tempRetVal.add(tempArrList);
    
    // Now start looping over the ordered list from smallest to biggest
    for(int i = 0; i < myEnvironmentList.size() - 1; ++i)
    {
      SynthEnvRec currentItem = myEnvironmentList.get(i);
      if(currentItem.estMemRequirement() + currentMemory < minMemory)
      {
        // Add this to the current thread
        tempRetVal.get(currentTaskIndex).add(currentItem);
        currentMemory += currentItem.estMemRequirement();
      }
      else
      {
        // Create a new ArrayList
        tempArrList = new ArrayList<>();
        tempArrList.add(currentItem);
        currentMemory = currentItem.estMemRequirement();
        tempRetVal.add(tempArrList);
        ++currentTaskIndex;
      }
    }
    
    retVal = new SynthEnvRec[tempRetVal.size()][];
    for(int i = 0; i < tempRetVal.size(); ++i)
    {
      retVal[i] = new SynthEnvRec[tempRetVal.get(i).size()];
      for(int j = 0; j < tempRetVal.get(i).size(); ++j)
      {
        retVal[i][j] = tempRetVal.get(i).get(j);
      }
    }
    
    Overseer.LOGGER.debug("Array Out:");
    for(int i = 0; i < retVal.length; ++i)
    {
      Overseer.LOGGER.debug(Arrays.toString(retVal[i]));
    }
    return retVal;
  }
  
  @Override
  public boolean containsAgent(String agentId)
  {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public GenericAgent getAgent(String agentId)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public boolean containsLocation(String locationId)
  {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public GenericLocation getLocation(String locationId)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public boolean containsNetwork(String networkId)
  {
    // TODO Auto-generated method stub
    return false;
  }
  
  @Override
  public GenericNetwork getNetwork(String networkId)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  @Override
  public LocalDateTime getSimDateTime()
  {
    return this.simDateTime;
  }
  
//  private void initialize() throws IOException, TimeoutException, ShutdownSignalException, ConsumerCancelledException, InterruptedException, 
//    ControllerException, JsonRpcException
//  {
//    this.parsePopulationFiles(this.controllerMap, this.sendAgentInfoMultimap);
//    this.parseHouseholdFiles(this.controllerMap, this.sendHouseholdInfoMultimap);
//    this.parseWorkplaceFiles(this.controllerMap, this.sendWorkplaceInfoMultimap);
//    this.parseSchoolFiles(this.controllerMap, this.sendSchoolInfoMultimap);
//    this.sendIntializeMessage(this.controllerMap);   
//  }
//  
//  private void sendIntializeMessage(HashMap<String, PopulationInfo> controllerMap) throws IOException, TimeoutException, ShutdownSignalException, 
//    ConsumerCancelledException, InterruptedException, JsonRpcException 
//  {    
//    ConnectionFactory factory = new ConnectionFactory();
//    factory.setUsername(Params.getMQUsername());
//    factory.setPassword(Params.getMQPassword());
//    factory.setHost(Params.getMQHost());
//    Connection connection = factory.newConnection();
//    int i = 0;
//    for(String cntrllrRPCLsnrQueueName : Params.getCntrllrRPCLstnrQueueNames())
//    {
//      System.out.println("Begin Loop " + i);
//      Channel tmpChannel = connection.createChannel();
//      JsonRpcClient client = new JsonRpcClient(tmpChannel, "", cntrllrRPCLsnrQueueName, Params.getRPCTimeout());
//      SprCntrllrRPCRcvr service = (SprCntrllrRPCRcvr)client.createProxy(SprCntrllrRPCRcvr.class);
//      for(String population : Params.getPopulations())
//      {
//        PopulationInfo info = controllerMap.get(population);
//        String message = info.toJSONString();
//        service.initialize(message);
//        System.out.println(" [x] Sent '" + population + "':'" + message + "' to queue '" + cntrllrRPCLsnrQueueName + "'");
//      }
//      tmpChannel.close();
//      System.out.println("End Loop " + i);
//      i++;
//    }
//    connection.close();
//  }
//  
//  private void registerAsRPCListener() throws IOException, TimeoutException 
//  {
//    ConnectionFactory connFactory = new ConnectionFactory();
//    connFactory.setUsername(Params.getMQUsername());
//    connFactory.setPassword(Params.getMQPassword());
//    connFactory.setHost(Params.getMQHost());
//    Connection conn = connFactory.newConnection();
//    final Channel ch = conn.createChannel();
//    ch.queueDeclare(Params.getSprcntrllrRPCLstnrQueueNames().get(0), false, false, false, null);
//    
//    JsonRpcServer server = new JsonRpcServer(ch, Params.getSprcntrllrRPCLstnrQueueNames().get(0), CntrllrRPCRcvr.class, this); 
////                             new CntrllrRPCRcvr() {
////									             @Override
////									             public void initResponse(String message) 
////									             {
////									               this.initResponse(message);
////									             }
////                             });
//    server.mainloop();
//  }  
//
  
  /**
   * 
   * @param argv
   */
  public static void main(String[] argv) 
  {
    try
    {
      final Overseer ovrsr = new Overseer();
      int threadCommArrLen = ovrsr.threadCommArr.length;
      
      // Initialize the Overseer, create the Controllers in their own threads, and start the Controllers
      ExecutorService exec = ovrsr.initializationPhase();
      Overseer.LOGGER.trace("Exec: " + exec.toString());
      Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "]");
      ovrsr.phaser.arriveAndAwaitAdvance();
      
      Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "]");
      ovrsr.phaser.arriveAndAwaitAdvance(); // Wait for Controllers to handle the Initialization Request
      
      Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "]");
      // No longer need to check synthetic Environments, so let the garbage collector clean up threadSynthEnvArr
      ovrsr.taskSynthEnvArr = null;
      
      // Initialize the Overseer's internal mappings with data returned from the Controllers
      Map<TypeMapping, Map<Id, Id>> normalizationMap = ovrsr.initializeOverseerMappingPhase();
      
      
      //Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "] ended");
      
      Overseer.LOGGER.trace("Duplicates to be normalized ...");
      normalizationMap.forEach((typeMap, idMap) -> { 
        Overseer.LOGGER.trace(typeMap + ":");
        idMap.forEach((idKey, idValue) -> {
          Overseer.LOGGER.trace(">> " + idKey.getIdString() + ": " + idValue.getIdString());
        });
      });
      ovrsr.normalizeControllerMappingsPhase(normalizationMap.get(TypeMapping.LOCATION), normalizationMap.get(TypeMapping.NETWORK));
      ovrsr.phaser.arriveAndAwaitAdvance(); 
      Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "]");
      ovrsr.phaser.arriveAndAwaitAdvance(); // Wait for the Controllers to handle the Normalization Request
      Overseer.LOGGER.trace("PHASE[" + OverseerPhase.valueOf(ovrsr.phaser.getPhase()).toString() + "]");
      
      
      for(int i = 0; i < threadCommArrLen; ++i)
      {
        // As it implements Future, we can call get()
        Overseer.LOGGER.trace(ovrsr.taskList.get(i).get());
      }
      
//      ovrsr.agentToControllerMap.forEach((key, value) -> {
//        System.out.println(key + " -> " + value);
//      });
    }
    catch(InterruptedException | ExecutionException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch(IdException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    catch(EnumNotFoundException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  
      // This method blocks until the result is obtained 
      // The get method can throw checked exceptions 
      // like when it is interrupted. This is the reason 
      // for adding the throws clause to main 

//    try
//    {
////      // Open the main channel
////      ConnectionFactory factory = new ConnectionFactory();
////      factory.setUsername(Params.getInitMsgUsername());
////      factory.setPassword(Params.getInitMsgPassword());
////      factory.setHost(Params.getInitMsgHost());
////      Connection connection = factory.newConnection();
////      Channel channel = connection.createChannel();
//
//      Overseer sc = new Overseer();
//      
//      sc.initialize();
//      
//      //register the SuperController as a listener on the standard queue
//      sc.registerAsRPCListener();
//     
//
//    }
//    catch(IOException | TimeoutException | ShutdownSignalException | ConsumerCancelledException | InterruptedException | ControllerException | JsonRpcException e)
//    {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
    System.exit(1);
  }
  
  /**
   * 
   * @param country
   * @param version
   * @param identifier
   * @return the approximate memory required for the population as a long integer
   */
  private static long calculateApproximateMemoryRequirementsForSyntheticEnvironment(String country, String version, String identifier)
  {
    final long gigaByte = 1024L * 1024L * 1024L;
    final double memoryPerAgent = 2.0 * (double)gigaByte / 1000000.0;    // (2GB / 1 Million agents)
    final double memoryPerLocation = 1.0 * (double)gigaByte / 100000.0;  // (1GB / 100000 locations)
    long retVal = 0L;
    
    try 
    {
      Unmarshaller unmarshaller = null;
      JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_SYNTHENV_CNTXT);
      unmarshaller = jc.createUnmarshaller();
      Path filePath = Paths.get(Params.getPopulationDirectory(), "country", country, version, identifier, Constants.SYNTHENV_FILENAME);
      Overseer.LOGGER.trace("filePath = " + filePath);
      String xmlFilename = filePath.toString();
      if(Utils.validateXmlFile(xmlFilename, Constants.SYNTHENV_SCHEMA_FILENAME))
      {
        SyntheticEnvironment synthEnvInfo = (SyntheticEnvironment)unmarshaller.unmarshal(new StreamSource(new File(xmlFilename)));
        
        int personCount = 0;
        int locationCount = 0;
        for(int i = 0; i < synthEnvInfo.getAgent().size(); ++i)
        {
          personCount += synthEnvInfo.getAgent().get(i).getEntryCount().intValue();
        }
        
        for(int i = 0; i < synthEnvInfo.getLocation().size(); ++i)
        {
          locationCount += synthEnvInfo.getLocation().get(i).getEntryCount().intValue();
        }
        
        retVal = (long)(memoryPerAgent * (double)personCount + memoryPerLocation * (double)locationCount);
      }
    } 
    catch(RuntimeException | JAXBException e)
    {
      Overseer.LOGGER.fatal(e);
      e.printStackTrace();
      System.exit(1);
    }
    
    return retVal;
  }
  
  /*
   * The following private methods are called from main and are simply for readability and concise steps
   */
  
  /**
   * 
   * @param ovrsr the Overseer to initialize
   * @throws IdException the Controller Id is invalid
   */
  private ExecutorService initializationPhase() throws IdException
  {
    /*
     * Initialization Phase
     */
    
    final int threadCommArrLen = this.threadCommArr.length;
    ExecutorService retExec = Executors.newFixedThreadPool(threadCommArrLen);
    
    // Load the Message Queues with initialization information
    for(int i = 0; i < threadCommArrLen; ++i)
    {
      ControllerInitInMessage msg = (ControllerInitInMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_IN, this.id);
      for(int j = 0; j < this.taskSynthEnvArr[i].length; ++j)
      {
        msg.addSyntheticEnvironment(this.taskSynthEnvArr[i][j]);
      }
      
      msg.setSimDateTime(this.simDateTime);
      this.threadCommArr[i].add(msg);
    }
    
    this.phaser = new Phaser(1); // Create a Phaser and expect one party to be registered (this Overseer)
    this.phaser.bulkRegister(threadCommArrLen); // Add each of the additional threads to the phaser
    this.taskList = new ArrayList<>(); // Create an ArrayList of FutureTasks (these will be the Controllers)
    
    // FutureTask is a concrete class that 
    // implements both Runnable and Future 
    for(int i = 0; i < threadCommArrLen; ++i)
    {
      Callable<ControllerOutput> callable = new Controller(this.threadCommArr[i], this, this.phaser);
      
      // Add a mapping from controllerId to the threadComArr index and vice-versa
      Id controllerId = ((Controller)callable).getId();
      this.controllerToTaskIndexMap.put(controllerId, i);
      this.taskIndexToControllerIdArr[i] = controllerId;
      
      FutureTask<ControllerOutput> task = new FutureTask<>(callable);
      
      retExec.submit(task, this.phaser);
      this.taskList.add(task);
    }
    return retExec;
  }
  
  
  /**
   * return a Map of TypeMapping to Id to Id Maps. 
   * @throws IdException the Controller Id is invalid
   */
  private Map<TypeMapping, Map<Id, Id>> initializeOverseerMappingPhase()
  {
    Map<TypeMapping, Map<Id, Id>> retVal = new HashMap<>();
    final Map<Id, Id> locationIdToNotifyControllerId = new HashMap<>();
    final Map<Id, Id> networkIdToNotifyControllerId = new HashMap<>();
    
    /*
     * Initialize Overseer Mappings Phase
     */
    
    for(int i = 0; i < this.threadCommArr.length; ++i)
    {
      Id testControllerId = this.taskIndexToControllerIdArr[i];
      Overseer.LOGGER.trace("Overseer.threadCommArr[i].size() = " + this.threadCommArr[i].size());
      this.threadCommArr[i].forEach((msg) ->
        {
          Id previouslyAddedControllerId = null;
          if(msg == null)
          {
            Overseer.LOGGER.fatal("Null message in threadCommArr");
            System.exit(Constants.EX_NOINPUT);
          }
          if(!msg.getSenderId().equals(testControllerId))
          {
            Overseer.LOGGER.fatal("Message sender [" + msg.getSenderId() + "] is not the expected sender from task [" + testControllerId + "]");
            System.exit(Constants.EX_IOERR);
          }
          if(msg.getType() == MessageType.CNTRLLR_INIT_OUT)
          {
            switch(((ControllerInitOutMessage)msg).getInitOutType())
            {
              case AGENT_ADD:
                if(this.agentToControllerMap.put(((ControllerInitOutMessage)msg).getAgentId(), msg.getSenderId()) != null)
                {
                  Overseer.LOGGER.fatal("Duplicate agent is not allowed (i.e. agents can only be in one controller)");
                  System.exit(Constants.EX_DATAERR);
                }
                break;
              case NETWORK_ADD:
                previouslyAddedControllerId = this.networkToControllerMap.put(((ControllerInitOutMessage)msg).getNetworkId(), msg.getSenderId());
                if(previouslyAddedControllerId != null)
                {
                   networkIdToNotifyControllerId.put(((ControllerInitOutMessage)msg).getNetworkId(), previouslyAddedControllerId);
                }
                break;
              case PLACE_ADD:
                previouslyAddedControllerId = this.locationToControllerMap.put(((ControllerInitOutMessage)msg).getLocationId(), msg.getSenderId());
                if(previouslyAddedControllerId != null)
                {
                  locationIdToNotifyControllerId.put(((ControllerInitOutMessage)msg).getLocationId(), previouslyAddedControllerId);
                }
                break;
              case UNSET:
                break;
              default:
                break;
            }
          }
          else
          {
            Overseer.LOGGER.fatal("INCORRECT RETURN MESSAGE. Expected " + MessageType.CNTRLLR_INIT_OUT.toString() + ", but received " + msg.getType().toString());
            System.exit(Constants.EX_IOERR);
          }
        });
      // Clear out the queue
      this.threadCommArr[i].clear();
    }
    
    retVal.put(TypeMapping.LOCATION, locationIdToNotifyControllerId);
    retVal.put(TypeMapping.NETWORK, networkIdToNotifyControllerId);
    
    return retVal;
  }
  
  /**
   * 
   * @param locationIdToNotifyControllerId
   * @param networkIdToNotifyControllerId
   */
  private void normalizeControllerMappingsPhase(Map<Id, Id> locationIdToNotifyControllerId, Map<Id, Id> networkIdToNotifyControllerId)
  {
    /*
     * Normalize Controller Mappings Phase
     */
    locationIdToNotifyControllerId.forEach((locationId, controllerId) -> {
      ControllerNormalizeInMessage normalizeMsg = (ControllerNormalizeInMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_NRMLZ_IN, this.id);
      normalizeMsg.setNormalizeInType(ControllerNormalizeInType.PLACE_REMOVE);
      normalizeMsg.setToDeleteLocationId(locationId); 
      
      int index = this.controllerToTaskIndexMap.get(controllerId);
      
      this.threadCommArr[index].add(normalizeMsg);
    });
    
    networkIdToNotifyControllerId.forEach((networkId, controllerId) -> {
      ControllerNormalizeInMessage normalizeMsg = (ControllerNormalizeInMessage)MessageFactory.getMessageInstance(MessageType.CNTRLLR_INIT_NRMLZ_IN, this.id);
      normalizeMsg.setNormalizeInType(ControllerNormalizeInType.NETWORK_REMOVE);
      normalizeMsg.setToDeleteNetworkId(networkId); 
      
      int index = this.controllerToTaskIndexMap.get(controllerId);
      
      this.threadCommArr[index].add(normalizeMsg);
    });
  }
  
  private void agentInitializationPhase()
  {
    
  }
  
  private enum TypeMapping 
  {
    NETWORK,
    LOCATION;
  }

}
