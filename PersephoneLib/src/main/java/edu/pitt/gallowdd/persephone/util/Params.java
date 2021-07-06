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
import edu.pitt.gallowdd.persephone.parameters.AgentXmlType;
import edu.pitt.gallowdd.persephone.parameters.LocationXmlType;
import edu.pitt.gallowdd.persephone.parameters.PersephoneParameters;
import edu.pitt.gallowdd.persephone.parameters.SimulationXmlType;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;


/**
 * This class contains all of the static parameters used by the Persephone Agent-Based Modeling Platform. 
 * In most cases, these will be set during initialization by reading from the file defaultProps.xml
 * 
 * @author David Galloway
 **/
public final class Params {
  
  private static final Logger LOGGER = LogManager.getLogger(Params.class.getName());
  private static Unmarshaller unmarshaller = null;
  
  static {
    try
    {
      JAXBContext jc = JAXBContext.newInstance(Constants.JAXB_PARAMETERS_CNTXT);
      Params.unmarshaller = jc.createUnmarshaller();
    }
    catch(JAXBException e)
    {
      Params.LOGGER.fatal(e);
      System.exit(1);
    }
    
    // Load the default values
    Params.loadParametersFromDefaultFile();
  }
  
  // Simulation Parameters
  private static String HOME_DIRECTORY;
  private static String POPULATION_DIRECTORY;
  //private static ImmutableList<PopulationType> POPULATIONS;
  private static List<SimulationXmlType.SyntheticEnvironment> SYNTHETIC_ENVIRONMENTS;
  private static ImmutableList<AgentXmlType> AGENT_TYPES;
  private static ImmutableList<LocationXmlType> LOCATION_TYPES;
  private static LocalDate START_DATE;
  private static LocalDate END_DATE;
  
  private static int SEED;
  
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
   * @return the SYNTHETIC_ENVIRONMENTS
   */
  public static List<SimulationXmlType.SyntheticEnvironment> getSyntheticEnvironments()
  {
    return Params.SYNTHETIC_ENVIRONMENTS;
  }
  
  /**
   * @return the AGENT_TYPES
   */
  public static ImmutableList<AgentXmlType> getAgentTypes()
  {
    return Params.AGENT_TYPES;
  }
  
  /**
   * @return the LOCATION_TYPES
   */
  public static ImmutableList<LocationXmlType> getLocationTypes()
  {
    return Params.LOCATION_TYPES;
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
  public static int getSeed()
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
  private static void loadParametersFromDefaultFile()
  {
    
    try 
    {
      //Parameters params = new Parameters();
      if(Utils.validateXmlFile(Constants.DEFAULT_PARAM_FILENAME, Constants.DEFAULT_PARAM_SCHEMA_FILENAME))
      {
        PersephoneParameters persephoneParams = (PersephoneParameters)Params.unmarshaller.unmarshal(new StreamSource(new File(Utils.getResource(Constants.DEFAULT_PARAM_FILENAME))));
        
        Params.HOME_DIRECTORY = persephoneParams.getSimulation().getHomeDirectory();
        Params.LOGGER.trace("Default HOME_DIRECTORY << " + Params.HOME_DIRECTORY);
        Params.POPULATION_DIRECTORY = persephoneParams.getSimulation().getPopulationDirectory();
        Params.LOGGER.trace("Default POPULATION_DIRECTORY << " + Params.POPULATION_DIRECTORY);
        Params.SEED = persephoneParams.getSimulation().getRngSeed().intValue();
        Params.LOGGER.trace("Default SEED << " + Params.SEED);
        
        LocalDate localDate = LocalDate.of(
            persephoneParams.getSimulation().getStartDate().getYear(), 
            persephoneParams.getSimulation().getStartDate().getMonth(), 
            persephoneParams.getSimulation().getStartDate().getDay());
        Params.START_DATE = localDate;
        Params.LOGGER.trace("Default START_DATE << " + Params.START_DATE);
        
        localDate = LocalDate.of(
            persephoneParams.getSimulation().getEndDate().getYear(), 
            persephoneParams.getSimulation().getEndDate().getMonth(), 
            persephoneParams.getSimulation().getEndDate().getDay());
        Params.END_DATE = localDate;
        Params.LOGGER.trace("Default END_DATE << " + Params.END_DATE);
        
        List<SimulationXmlType.SyntheticEnvironment> synthEnvironments = persephoneParams.getSimulation().getSyntheticEnvironment();
        
        List<AgentXmlType> agentTypes = persephoneParams.getSimulation().getModel().getAgent();
        List<LocationXmlType> locationTypes = persephoneParams.getSimulation().getModel().getLocation();
        
        Params.SYNTHETIC_ENVIRONMENTS = ImmutableList.copyOf(synthEnvironments);
        
        Params.LOGGER.trace("Default SYNTHETIC_ENVIRONMENTS:");
        Params.SYNTHETIC_ENVIRONMENTS.forEach(pop -> {
          Params.LOGGER.trace("... << " + pop.getCountry() + ":" + pop.getVersion() + ":" + pop.getIdentifier());
        });
        
        
        Params.AGENT_TYPES = ImmutableList.copyOf(agentTypes);
        Params.LOGGER.trace("Default AGENT_TYPES:");
        Params.AGENT_TYPES.forEach(agentType -> {
          Params.LOGGER.trace("... << " + agentType.getName() + ":" + agentType.getJavaClass().toString());
        });
        
        Params.LOCATION_TYPES = ImmutableList.copyOf(locationTypes);
        Params.LOGGER.trace("Default LOCATION_TYPES:");
        Params.LOCATION_TYPES.forEach(locType -> {
          Params.LOGGER.trace("... << " + locType.getName() + ":" + locType.getJavaClass().toString());
        });

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
   * @param userParamsFilename
   */
  public static void loadParametersFromUserFile(String userParamsFilename)
  {
    try 
    {  
      Parameters params = new Parameters();
      
      FileBasedConfigurationBuilder<XMLConfiguration> builder =
          new FileBasedConfigurationBuilder<XMLConfiguration>(XMLConfiguration.class)
              .configure(params.xml().setFileName(userParamsFilename)
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
  
//  private static String getResource(String filename) throws FileNotFoundException
//  {
//    URL resource = Params.class.getClassLoader().getResource(filename);
//    Objects.requireNonNull(resource);
//    return resource.getFile();
//  }
  
  /*
   *             <xsd:element name="rng_seed" minOccurs="1" maxOccurs="1" type="xsd:integer"/>
            <xsd:element name="population" minOccurs="1" maxOccurs="unbounded">
                <xsd:complexType>
                    <xsd:sequence>
                        <xsd:element name="country" minOccurs="1" maxOccurs="1" type="xsd:string"/>
                        <xsd:element name="version" minOccurs="1" maxOccurs="1" type="xsd:string"/>
                        <xsd:element name="identifier" minOccurs="1" maxOccurs="1" type="xsd:string"/>
                        <xsd:element name="agents" minOccurs="1" maxOccurs="unbounded" type="agentType"/>
                    </xsd:sequence>
                </xsd:complexType>
            </xsd:element>
            
   */
  
//  /**
//   * A transfer class for holding Population Information
//   */
//  public static final class XmlPopulationInfo {
//    private final String identifier;
//    private final String country;
//    private final String version;
//    private final XmlAgentInfo agentInfo;
//    
//    /**
//     * Construct an XmlPopulationInfo
//     * @param identifier the identifier to set
//     * @param country the country to set
//     * @param version the version to set
//     * @param agentInfo the agentInfo to add to this list
//     */
//    public XmlPopulationInfo(String identifier, String country, String version, XmlAgentInfo agentInfo)
//    {
//      this.identifier = identifier.trim();
//      this.country = country.trim();
//      this.version = version.trim();
//      this.agentInfo = agentInfo;
//    }
//    
//    /**
//     * @return the identifiers
//     */
//    public String getIdentifiers()
//    {
//      return this.identifier;
//    }
//    
//    /**
//     * @return the Country
//     */
//    public String getCountry()
//    {
//      return this.country;
//    }
//    
//    /**
//     * @return the version
//     */
//    public String getVersion()
//    {
//      return this.version;
//    }
//    
//    /**
//     * @return the agentInfo
//     */
//    public XmlAgentInfo getAgentInfo()
//    {
//      return this.agentInfo;
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(Object)
//     */
//    @Override
//    public boolean equals(Object obj) {
//      if(this == obj)
//      {
//        return true;
//      }
//      
//      if(obj == null)
//      {
//        return false;
//      }
//      
//      if(getClass() != obj.getClass())
//      {
//        return false;
//      }
//      
//      XmlPopulationInfo rhs = (XmlPopulationInfo)obj;
//      EqualsBuilder equalsBuilder = new EqualsBuilder();
//      equalsBuilder.append(this.identifier, rhs.identifier);
//      equalsBuilder.append(this.country, rhs.country);
//      equalsBuilder.append(this.version, rhs.version);
//      equalsBuilder.append(this.agentInfo, rhs.agentInfo);
//      
//      return equalsBuilder.isEquals();
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString()
//    {
//      return new ToStringBuilder(this)
//          .append("identifier", this.identifier)
//          .append("country", this.country)
//          .append("version", this.version)
//          .append("agentInfo", this.agentInfo)
//          .toString();
//    }
//  }
  
//  /*
//   *             
//                <xsd:complexType name="agentType">
//        <xsd:sequence>
//            <xsd:element name="name" minOccurs="1" maxOccurs="1" type="xsd:string"/>
//            <xsd:element name="java_class" minOccurs="1" maxOccurs="1" type="agentJavaClassTypeEnum"/>
//            <xsd:element name="agent_attribute" minOccurs="0" maxOccurs="unbounded">
//                <xsd:complexType>
//                    <xsd:sequence>
//                        <xsd:element name="name" minOccurs="1" maxOccurs="1" type="xsd:string"/>
//                        <xsd:element name="data_type" minOccurs="1" maxOccurs="1" type="agentAttributeJavaTypeEnum"/>
//                        <xsd:element name="is_dynamic" minOccurs="1" maxOccurs="1" type="xsd:boolean"/>
//                        <!-- The way to assign attributes to agents that are in the initial population files -->
//                        <xsd:element name="initial_source" minOccurs="1" maxOccurs="1">
//                            <xsd:complexType>
//                                <xsd:choice>
//                                    <xsd:element name="link" minOccurs="1" maxOccurs="1" type="xsd:string"/>
//                                    <xsd:element name="distribution" minOccurs="1" maxOccurs="1" type="distributionType"/>
//                                </xsd:choice>
//                            </xsd:complexType>
//                        </xsd:element>
//                        <!-- The way to assign attributes to agents that are created during the course of the simulation -->
//                        <xsd:element name="dynamic_source" minOccurs="0" maxOccurs="1">
//                            <xsd:complexType>
//                                <xsd:choice>
//                                    <xsd:element name="distribution"  minOccurs="1" maxOccurs="1" type="distributionType"/>
//                                </xsd:choice>
//                            </xsd:complexType>
//                        </xsd:element>
//                    </xsd:sequence>
//                </xsd:complexType>
//            </xsd:element>
//            <xsd:element name="agent_assginment" minOccurs="0" maxOccurs="unbounded" type="agentAssignmentType"/>
//        </xsd:sequence>
//    </xsd:complexType>
//   */
//  
//  /**
//   * A transfer class for holding Agent Information
//   */
//  public static final class XmlAgentInfo {
//    private final String name;
//    private final String javaClassname;
//    private final List<XmlAgentAttributeInfo> agentAttributes;
//    private final String agentAssignments = "";
//    
//    /**
//     * Construct an XmlAgentInfo
//     * @param name 
//     * @param javaClassname 
//     * @param agentAttributes 
//     */
//    public XmlAgentInfo(String name, String javaClassname, List<XmlAgentAttributeInfo> agentAttributes)
//    {
//      this.name = name.trim();
//      this.javaClassname = javaClassname.trim();
//      if(agentAttributes != null)
//      {
//        this.agentAttributes = agentAttributes;
//      }
//      else
//      {
//        this.agentAttributes = new ArrayList<>();
//      }
//    }
//    
//    /**
//     * Construct an XmlAgentInfo
//     * @param name 
//     * @param javaClassname 
//     * @param agentAttributeArr 
//     */
//    public XmlAgentInfo(String name, String javaClassname, XmlAgentAttributeInfo [] agentAttributeArr)
//    {
//      this.name = name.trim();
//      this.javaClassname = javaClassname.trim();
//      this.agentAttributes = new ArrayList<>();
//      if(agentAttributeArr != null)
//      {
//        for(int i = 0; i < agentAttributeArr.length; ++i)
//        {
//          this.agentAttributes.add(agentAttributeArr[i]);
//        }
//      }
//    }
//    
//    /**
//     * @return the name
//     */
//    public String getName()
//    {
//      return this.name;
//    }
//    
//    /**
//     * @return the javaClassname
//     */
//    public String getJavaClassName()
//    {
//      return this.javaClassname;
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(Object)
//     */
//    @Override
//    public boolean equals(Object obj) {
//      if(this == obj)
//      {
//        return true;
//      }
//      
//      if(obj == null)
//      {
//        return false;
//      }
//      
//      if(getClass() != obj.getClass())
//      {
//        return false;
//      }
//      
//      XmlAgentInfo rhs = (XmlAgentInfo)obj;
//      EqualsBuilder equalsBuilder = new EqualsBuilder();
////      equalsBuilder.append(this.identifier, rhs.identifier);
////      equalsBuilder.append(this.country, rhs.country);
////      equalsBuilder.append(this.version, rhs.version);
//      return equalsBuilder.isEquals();
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString()
//    {
//      return new ToStringBuilder(this)
////          .append("identifier", this.identifier)
////          .append("country", this.country)
////          .append("version", this.version)
//          .toString();
//    }
//  }
  
//  /**
//   * A transfer class for holding Agent Attribute Information
//   */
//  public static final class XmlAgentAttributeInfo {
//    
//    private final String name;
//    private final String dataType;
//    private final boolean isDynamic;
//    private final String initialSource;
//    private final String dynamicSource;
//    
//    /**
//     * Construct an XmlAgentAttributeInfo
//     * @param name 
//     * @param dataType 
//     * @param isDynamict
//     */
//    public XmlAgentAttributeInfo(String name, String dataType, boolean isDynamic)
//    {
//      this.name = name.trim();
//      this.dataType = dataType.trim();
//      this.isDynamic = isDynamic;
//      this.initialSource = Constants.STRING_UNSET;
//      this.dynamicSource = Constants.STRING_UNSET;
//    }
//    
//    /**
//     * @return the name
//     */
//    public String getName()
//    {
//      return this.name;
//    }
//    
//    /**
//     * @return the dataType
//     */
//    public String getDataType()
//    {
//      return this.dataType;
//    }
//    
//    /**
//     * @return the isDynamic
//     */
//    public boolean isDynamic()
//    {
//      return this.isDynamic;
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#equals(Object)
//     */
//    @Override
//    public boolean equals(Object obj) {
//      if(this == obj)
//      {
//        return true;
//      }
//      
//      if(obj == null)
//      {
//        return false;
//      }
//      
//      if(getClass() != obj.getClass())
//      {
//        return false;
//      }
//      
//      XmlAgentAttributeInfo rhs = (XmlAgentAttributeInfo)obj;
//      EqualsBuilder equalsBuilder = new EqualsBuilder();
//      equalsBuilder.append(this.name, rhs.name);
//      equalsBuilder.append(this.dataType, rhs.dataType);
//      equalsBuilder.append(this.isDynamic, rhs.isDynamic);
//      return equalsBuilder.isEquals();
//    }
//    
//    /* (non-Javadoc)
//     * @see java.lang.Object#toString()
//     */
//    @Override
//    public String toString()
//    {
//      return new ToStringBuilder(this)
////          .append("identifier", this.identifier)
////          .append("country", this.country)
////          .append("version", this.version)
//          .toString();
//    }
//  }
//  /*
//   * <xsd:element name="initial_file_link" minOccurs="1" maxOccurs="1" type="initFileLinkType" />
//                  <xsd:element name="link" minOccurs="1" maxOccurs="1" type="linkType" />
//                  <xsd:element name="distribution" minOccurs="1" maxOccurs="1" type="distributionType" />
//                  
//                          <xsd:element name="init_file_csv_field_name" minOccurs="1" maxOccurs="1" type="xsd:string" />
//        <xsd:element name="convert_to_enum_function" minOccurs="0" maxOccurs="1" type="xsd:string" />
//        
//                <xsd:element name="link_file_name" minOccurs="1" maxOccurs="1" type="xsd:string" />
//        <xsd:element name="link_file_csv_field_name" minOccurs="1" maxOccurs="1" type="xsd:string" />
//        <xsd:element name="convert_to_enum_function" minOccurs="0" maxOccurs="1" type="xsd:string" />
//   */
//  public static final class XmlAgentAttributeInitType {
//    
//    public enum AttributeInitType { 
//      INIT_FILE_LINK,
//      LINK,
//      DISTRIBUTION,
//      UNSET
//    }
//    
//    private AttributeInitType initType = AttributeInitType.UNSET;
//    private String fileCSVFieldName = Constants.STRING_UNSET;
//    private String convertToEnumFunction = Constants.STRING_UNSET;
//    private String linkFileName = Constants.STRING_UNSET;
//  }
}
