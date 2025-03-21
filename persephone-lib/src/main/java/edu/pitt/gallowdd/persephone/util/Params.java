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

package edu.pitt.gallowdd.persephone.util;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import javax.xml.transform.stream.StreamSource;

import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.tree.xpath.XPathExpressionEngine;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Ints;

import edu.pitt.gallowdd.persephone.PersephoneException.ErrorCode;
import edu.pitt.gallowdd.persephone.xml.base.AgentXmlType;
import edu.pitt.gallowdd.persephone.xml.base.LocationXmlType;
import edu.pitt.gallowdd.persephone.xml.base.PersephoneBaseParameters;
//import edu.pitt.gallowdd.persephone.xml.common.AgentXmlEnum;
//import edu.pitt.gallowdd.persephone.xml.common.LocationXmlEnum;
import edu.pitt.gallowdd.persephone.xml.runtime.PersephoneRuntimeParameters;
import edu.pitt.gallowdd.persephone.xml.runtime.SimulationXmlType;
import edu.pitt.gallowdd.persephone.xml.runtime.SimulationXmlType.SyntheticEnvironmentDescriptor;
import edu.pitt.gallowdd.persephone.xml.runtime.UserDefinedAgentXmlType;
import edu.pitt.gallowdd.persephone.xml.runtime.UserDefinedLocationXmlType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


/**
 * This class contains all of the static parameters used by the Persephone Agent-Based Modeling Platform. 
 * In most cases, these will be set during initialization by reading from TODO //the file defaultProps.xml
 * 
 * @author David Galloway
 **/
public final class Params {
  
  private static final Logger LOGGER = LogManager.getLogger(Params.class.getName());
  private static Unmarshaller unmarshaller = null;
  
  static {
    try
    {
      JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_BASE_PARAM_CNTXT);
      Params.unmarshaller = jc.createUnmarshaller();
      // Load the base parameter values
      Params.loadBaseParameters();
    }
    catch(JAXBException e)
    {
      Params.LOGGER.fatal(e);
      System.exit(Constants.EX_CONFIG);
    }
    
    try
    {
      JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_RUNTIME_PARAM_CNTXT);
      Params.unmarshaller = jc.createUnmarshaller();
      // Load the base parameter values
      Params.loadDefaultRuntimeParameters();
    }
    catch(JAXBException e)
    {
      Params.LOGGER.fatal(e);
      System.exit(Constants.EX_CONFIG);
    }
  }
  
  // Simulation Parameters
  private static String HOME_DIRECTORY;
  private static String POPULATION_DIRECTORY;
  private static List<SyntheticEnvironmentDescriptor> SYNTH_ENV_DESCRIPTORS;
  private static ImmutableList<AgentXmlType> AGENTS;
  private static ImmutableList<LocationXmlType> LOCATIONS;
  private static Optional<ImmutableList<UserDefinedAgentXmlType>> USER_DEFINED_AGENTS;
  private static Optional<ImmutableList<UserDefinedLocationXmlType>> USER_DEFINED_LOCATIONS;
  private static LocalDate START_DATE;
  private static LocalDate END_DATE;
  
  private static long SEED;
  
  // JMS Queuing
  private static String MQ_HOST;
  private static String MQ_USERNAME;
  private static String MQ_PASSWORD;
  private static List<String> SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES;
  private static List<String> CNTRLLR_RPC_LSTNR_QUEUE_NAMES;
  private static int RPC_TIMEOUT;
  
//  static 
//  { 
//    // Load the default values
//    Params.loadParametersFromDefaultFile();
//  }
  
  /**
   *  Don't let anyone instantiate this class. 
   */
  private Params() {}
  
  /**
   * @return the HOME_DIRECTORY
   */
  public static String getHomeDirectory()
  {
    return Params.HOME_DIRECTORY;
  }  
  
  /**
   * @return the POPULATION_DIRECTORY
   */
  public static String getPopulationDirectory()
  {
    return Params.POPULATION_DIRECTORY;
  }  
  
//  /**
//   * @return the POPULATIONS
//   */
//  public static ImmutableList<PopulationType> getPopulations()
//  {
//    return Params.POPULATIONS;
//  }
  
  /**
   * @return the SYNTH_ENV_DESCRIPTORS
   */
  public static List<SyntheticEnvironmentDescriptor> getSyntheticEnvDescriptors()
  {
    return Params.SYNTH_ENV_DESCRIPTORS;
  }
  
  /**
   * @return the AGENTS
   */
  public static ImmutableList<AgentXmlType> getAgents()
  {
    return Params.AGENTS;
  }
  
  /**
   * @return the LOCATIONS
   */
  public static ImmutableList<LocationXmlType> getLocations()
  {
    return Params.LOCATIONS;
  }
  
  /**
   * @return the USER_DEFINED_AGENTS
   */
  public static Optional<ImmutableList<UserDefinedAgentXmlType>> getUserDefinedAgents()
  {
    return Params.USER_DEFINED_AGENTS;
  }
  
  /**
   * @return the USER_DEFINED_LOCATIONS
   */
  public static Optional<ImmutableList<UserDefinedLocationXmlType>> getUserDefinedLocations()
  {
    return Params.USER_DEFINED_LOCATIONS;
  }
  
  /**
   * @return the days between START_DATE and END_DATE
   */
  public static int getDays()
  {
    return Ints.checkedCast(ChronoUnit.DAYS.between(START_DATE, END_DATE));
  }
  
  /**
   * @return the START_DATE
   */
  public static LocalDate getStartDate()
  {
    return Params.START_DATE;
  }
  
  /**
   * @return the END_DATE
   */
  public static LocalDate getEndDate()
  {
    return Params.END_DATE;
  }
  
  /**
   * @return the SEED
   */
  public static long getSeed()
  {
    return Params.SEED;
  }
  
  /**
   * @return the MQ_HOST
   */
  public static String getMQHost()
  {
    return Params.MQ_HOST;
  }
  
  /**
   * @return the MQ_USERNAME
   */
  public static String getMQUsername()
  {
    return Params.MQ_USERNAME;
  }
  
  /**
   * @return the MQ_PASSWORD
   */
  public static String getMQPassword()
  {
    return Params.MQ_PASSWORD;
  }
  
  /**
   * @return the SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES
   */
  public static List<String> getSprcntrllrRPCLstnrQueueNames()
  {
    return Params.SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES;
  }
  
  /**
   * @return the CNTRLLR_RPC_LSTNR_QUEUE_NAMES
   */
  public static List<String> getCntrllrRPCLstnrQueueNames()
  {
    return Params.CNTRLLR_RPC_LSTNR_QUEUE_NAMES;
  }
  
  /**
   * @return the RPC_TIMEOUT
   */
  public static int getRPCTimeout() {
    return Params.RPC_TIMEOUT;
  }
  
  /**
   * 
   */
  private static void loadBaseParameters()
  {
    
    try 
    {
      //Parameters params = new Parameters();
      if(Utils.validateXmlFile(Constants.BASE_PARAM_FILENAME, Constants.BASE_PARAM_SCHEMA_FILENAME))
      {
        PersephoneBaseParameters persephoneBaseParams = (PersephoneBaseParameters)Params.unmarshaller.unmarshal(new StreamSource(new File(Utils.getResource(Constants.BASE_PARAM_FILENAME))));
        
        List<AgentXmlType> agents = persephoneBaseParams.getAgent();
        List<LocationXmlType> locations = persephoneBaseParams.getLocation();
        
        Params.AGENTS = ImmutableList.copyOf(agents);
        Params.LOGGER.trace("Default AGENTS:");
        Params.AGENTS.forEach(agent -> {
          Params.LOGGER.trace("... << " + agent.getName());
        });
        
        Params.LOCATIONS = ImmutableList.copyOf(locations);
        Params.LOGGER.trace("Default LOCATIONS:");
        Params.LOCATIONS.forEach(loc -> {
          Params.LOGGER.trace("... << " + loc.getName());
        });
      }
      else
      {
        throw new RuntimeException("Error validating xml file, " + Constants.BASE_PARAM_FILENAME + " against xsd file, " + Constants.BASE_PARAM_SCHEMA_FILENAME);
      }
    }
    catch(RuntimeException | IOException | JAXBException e)
    {
      Params.LOGGER.fatal(ErrorCode.ERR_PARAMS.message(), e);
      System.exit(Constants.EX_DATAERR);
    }
  }
  
  /**
   * 
   */
  private static void loadDefaultRuntimeParameters()
  {
    
    try 
    {
      //Parameters params = new Parameters();
      if(Utils.validateXmlFile(Constants.DEFAULT_RUNTIME_PARAM_FILENAME, Constants.DEFAULT_RUNTIME_PARAM_SCHEMA_FILENAME))
      {
        PersephoneRuntimeParameters persephoneRuntimeParams = (PersephoneRuntimeParameters)Params.unmarshaller.unmarshal(new StreamSource(new File(Utils.getResource(Constants.DEFAULT_RUNTIME_PARAM_FILENAME))));
        
        Params.HOME_DIRECTORY = persephoneRuntimeParams.getSimulation().getHomeDirectory();
        Params.LOGGER.trace("Default HOME_DIRECTORY << " + Params.HOME_DIRECTORY);
        Params.POPULATION_DIRECTORY = persephoneRuntimeParams.getSimulation().getPopulationDirectory();
        Params.LOGGER.trace("Default POPULATION_DIRECTORY << " + Params.POPULATION_DIRECTORY);
        Params.SEED = persephoneRuntimeParams.getSimulation().getRngSeed().longValue();
        Params.LOGGER.trace("Default SEED << " + Params.SEED);
        
        LocalDate localDate = LocalDate.of(
            persephoneRuntimeParams.getSimulation().getStartDate().getYear(), 
            persephoneRuntimeParams.getSimulation().getStartDate().getMonth(), 
            persephoneRuntimeParams.getSimulation().getStartDate().getDay());
        Params.START_DATE = localDate;
        Params.LOGGER.trace("Default START_DATE << " + Params.START_DATE);
        
        localDate = LocalDate.of(
            persephoneRuntimeParams.getSimulation().getEndDate().getYear(), 
            persephoneRuntimeParams.getSimulation().getEndDate().getMonth(), 
            persephoneRuntimeParams.getSimulation().getEndDate().getDay());
        Params.END_DATE = localDate;
        Params.LOGGER.trace("Default END_DATE << " + Params.END_DATE);
        
        List<SimulationXmlType.SyntheticEnvironmentDescriptor> synthEnvironments = persephoneRuntimeParams.getSimulation().getSyntheticEnvironmentDescriptor();
        
        List<UserDefinedAgentXmlType> userDefinedAgents = persephoneRuntimeParams.getModel().getUserDefinedAgent();
        List<UserDefinedLocationXmlType> userDefinedLocations = persephoneRuntimeParams.getModel().getUserDefinedLocation();
        
        Params.SYNTH_ENV_DESCRIPTORS = ImmutableList.copyOf(synthEnvironments);
        
        Params.LOGGER.trace("Default SYNTH_ENV_DESCRIPTORS:");
        Params.SYNTH_ENV_DESCRIPTORS.forEach(pop -> {
          Params.LOGGER.trace("... << " + pop.getCountry() + ":" + pop.getVersion() + ":" + pop.getIdentifier());
        });
        
        Params.USER_DEFINED_AGENTS = (userDefinedAgents == null ? Optional.empty() : Optional.of(ImmutableList.copyOf(userDefinedAgents)));
        if(Params.USER_DEFINED_AGENTS.isPresent())
        {
          Params.LOGGER.trace("Default USER_DEFINED_AGENTS:");
          Params.USER_DEFINED_AGENTS.get().forEach(agent -> {
            Params.LOGGER.trace("... << " + agent.getName());
          });
        }
        
        Params.USER_DEFINED_LOCATIONS = (userDefinedLocations == null ? Optional.empty() : Optional.of(ImmutableList.copyOf(userDefinedLocations)));
        if( Params.USER_DEFINED_LOCATIONS.isPresent())
        {
          Params.LOGGER.trace("Default USER_DEFINED_LOCATIONS:");
          Params.USER_DEFINED_LOCATIONS.get().forEach(loc -> {
            Params.LOGGER.trace("... << " + loc.getName());
          });
        }


//      
//      // Default Messaging Parameters
//      Params.MQ_HOST = config.getString("message_queue/host");
//      Params.LOGGER.trace("Default MQ_HOST << " + Params.MQ_HOST);
//      // Default Messaging String Parameters
//      Params.MQ_USERNAME = config.getString("message_queue/string_property[@name='username']/value");
//      Params.LOGGER.trace("Default MQ_USERNAME << " + Params.MQ_USERNAME);
//      Params.MQ_PASSWORD = config.getString("message_queue/string_property[@name='password']/value");
//      Params.LOGGER.trace("Default MQ_PASSWORD << " + Params.MQ_PASSWORD);
//      // Default Messaging String Array Parameters
//      Params.SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES = ImmutableList.copyOf(config.getStringArray("message_queue/string_array_property[@name='rpc_lstnr_queue_name']/value"));
//      Params.LOGGER.trace("Default SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES << " + Params.SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES.toString());
//      Params.CNTRLLR_RPC_LSTNR_QUEUE_NAMES = ImmutableList.copyOf(config.getStringArray("message_queue/string_array_property[@name='cntrllr_rpc_lstnr_queue_name']/value"));
//      Params.LOGGER.trace("Default CNTRLLR_RPC_LSTNR_QUEUE_NAMES << " + Params.CNTRLLR_RPC_LSTNR_QUEUE_NAMES);    
//      // Default Messaging int Parameters
//      Params.RPC_TIMEOUT = config.getInt("message_queue/int_property[@name='rpc_timeout']/value");
//      Params.LOGGER.trace("Default RPC_TIMEOUT << " + Params.RPC_TIMEOUT);
      }
    } 
    catch(RuntimeException | IOException | JAXBException e)
    {
      Params.LOGGER.fatal(ErrorCode.ERR_PARAMS.message(), e);
      System.exit(1);
    }
  }
  
  /**
   * 
   * @param userRuntimeParamsFilename
   */
  public static void loadRuntimeParametersFromUserFile(String userRuntimeParamsFilename)
  {
    try 
    {  
      Parameters params = new Parameters();
      
      FileBasedConfigurationBuilder<XMLConfiguration> builder =
          new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
              .configure(params.xml().setFileName(userRuntimeParamsFilename)
                                     .setExpressionEngine(new XPathExpressionEngine()));
      XMLConfiguration config = builder.getConfiguration();
      
      // User Simulation String Parameters
      if(config.containsKey("simulation/string_property[@name='population_directory']/value"))
      {
        Params.POPULATION_DIRECTORY = config.getString("simulation/string_property[@name='population_directory']/value");
        Params.LOGGER.trace("User POPULATION_DIRECTORY << " + Params.POPULATION_DIRECTORY);
      }
      
//      // User Simulation String Array Parameters
//      if(config.containsKey("simulation/string_array_property[@name='population_files']/value"))
//      {
//        Params.POPULATIONS = ImmutableList.copyOf(config.getStringArray("simulation/string_array_property[@name='population_files']/value"));
//        Params.LOGGER.trace("User POPULATIONS << " + Params.POPULATIONS.toString());
//      }
//  
//      // User Simulation int Parameters
//      if(config.containsKey("simulation/int_property[@name='seed']/value"))
//      {
//        Params.SEED = config.getInt("simulation/int_property[@name='seed']/value");
//        Params.LOGGER.trace("User SEED << " + Params.SEED);
//      }
//      
//      if(config.containsKey("simulation/int_property[@name='days']/value"))
//      {
//        Params.DAYS = config.getInt("simulation/int_property[@name='days']/value");
//        Params.LOGGER.trace("User DAYS << " + Params.DAYS);
//      }
      
      // User Messaging Parameters
      if(config.containsKey("message_queue/host"))
      {
        Params.MQ_HOST = config.getString("message_queue/host");
        Params.LOGGER.trace("User MQ_HOST << " + Params.MQ_HOST);
      }
      
      // User Messaging String Parameters
      if(config.containsKey("message_queue/string_property[@name='username']/value"))
      {
        Params.MQ_USERNAME = config.getString("message_queue/string_property[@name='username']/value");
        Params.LOGGER.trace("User MQ_USERNAME << " + Params.MQ_USERNAME);        
      }
      if(config.containsKey("message_queue/string_property[@name='password']/value"))
      {
        Params.MQ_PASSWORD = config.getString("message_queue/string_property[@name='password']/value");
        Params.LOGGER.trace("User MQ_PASSWORD << " + Params.MQ_PASSWORD);
      }
      
      // User Messaging String Array Parameters
      if(config.containsKey("message_queue/string_array_property[@name='rpc_lstnr_queue_name']/value"))
      {
        Params.SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES = ImmutableList.copyOf(config.getStringArray("message_queue/string_array_property[@name='rpc_lstnr_queue_name']/value"));
        Params.LOGGER.trace("User SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES << " + Params.SPRCNTRLLR_RPC_LSTNR_QUEUE_NAMES.toString());
      }
      if(config.containsKey("message_queue/string_array_property[@name='cntrllr_rpc_lstnr_queue_name']/value"))
      {
        Params.CNTRLLR_RPC_LSTNR_QUEUE_NAMES = ImmutableList.copyOf(config.getStringArray("message_queue/string_array_property[@name='cntrllr_rpc_lstnr_queue_name']/value"));
        Params.LOGGER.trace("User CNTRLLR_RPC_LSTNR_QUEUE_NAMES << " + Params.CNTRLLR_RPC_LSTNR_QUEUE_NAMES);     
      }
      
      // User Messaging int Parameters
      if(config.containsKey("message_queue/int_property[@name='rpc_timeout']/value"))
      {
        Params.RPC_TIMEOUT = config.getInt("message_queue/int_property[@name='rpc_timeout']/value");
        Params.LOGGER.trace("User RPC_TIMEOUT << " + Params.RPC_TIMEOUT);
      }
    } 
    catch(ConfigurationException | RuntimeException e)
    {
      Params.LOGGER.error(ErrorCode.ERR_PARAMS.message(), e);
      System.exit(1);
    }  
  }
}
