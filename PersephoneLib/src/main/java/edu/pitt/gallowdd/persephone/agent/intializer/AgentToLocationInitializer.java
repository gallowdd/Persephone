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

package edu.pitt.gallowdd.persephone.agent.intializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.agent.AgentFactory;
import edu.pitt.gallowdd.persephone.agent.AgentTypeEnum;
import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.agent.Person;
import edu.pitt.gallowdd.persephone.agent.attribute.PersonBMI;
import edu.pitt.gallowdd.persephone.agent.attribute.PersonHeight;
import edu.pitt.gallowdd.persephone.xml.base.AgentXmlType;
import edu.pitt.gallowdd.persephone.xml.base.AgentXmlType.AgentAttribute.Source;
import edu.pitt.gallowdd.persephone.xml.common.DatatypeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.runtime.SimulationXmlType.SyntheticEnvironmentDescriptor;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.EnumConverterMethods;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class AgentToLocationInitializer {
  
  private static final Logger LOGGER = LogManager.getLogger(AgentToLocationInitializer.class.getName());
  
  private AgentToLocationInitializer()
  {
    // No instance of this Initializer should be created
  }
  
  
  /**
   * @param population
   * @param xmlSyntheticEnvironment
   * @param simDate
   */
  public static void initialize(List<GenericAgent> population, SyntheticEnvironmentDescriptor xmlSyntheticEnvironment, LocalDate simDate)
  {
    String country = xmlSyntheticEnvironment.getCountry().value();
    String version = xmlSyntheticEnvironment.getVersion().value();
    String identifier = xmlSyntheticEnvironment.getIdentifier();
  }
  
  /**
   * 
   * @param record a record from a CVS Population file
   * @param agentXml the Agent Information from the parameter file
   * @param simDate the current date in the simulation
   * @return An agent Of the Type requested
   */
  public static GenericAgent initialize(CSVRecord record, AgentXmlType agentXml, LocalDate simDate)
  {
    GenericAgent retVal = null;
    
    try
    {
      List<AgentXmlType.AgentAttribute> agentAttributes = agentXml.getAgentAttribute();
      
      // Every record must have a field "id"
      String id = record.get("id");
      switch(agentXml.getDataType())
      {
        case PERSON:
          retVal = AgentFactory.createAgent(AgentTypeEnum.PERSON, id);
          AgentToLocationInitializer.initializePerson((Person)retVal, agentAttributes, record, simDate);
          break;
        default:
          break;
      }
    }
    catch(IdException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | SecurityException e)
    {
      // ABORT
      AgentToLocationInitializer.LOGGER.fatal(e);
      System.exit(Constants.EX_DATAERR);
    }
    
    return retVal;
  }
  
  /**
   * 
   * @param person
   * @param agentAttributes
   * @param record
   * @param simDate
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  private static void initializePerson(Person person, List<AgentXmlType.AgentAttribute> agentAttributes, CSVRecord record, LocalDate simDate) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException
  {
    for(AgentXmlType.AgentAttribute agentAtt : agentAttributes)
    {
      DatatypeXmlEnum javaType = agentAtt.getDataType();
      String attrName = agentAtt.getAttrName();
      
      Source attrSrcInfo = agentAtt.getSource();
      // There should be an init source. If not throw an exception
      if(attrSrcInfo == null)
      {
        // ABORT
        AgentToLocationInitializer.LOGGER.fatal("The AgentAttribute [" + attrName + "] is not dynamic, but there is no initial source information defined");
        System.exit(Constants.EX_DATAERR);
      }
      
      if(attrSrcInfo.getInitialFile() != null)
      {
        // The data is in the record already, so we just need to get it from the correct field
        String fieldName = attrSrcInfo.getInitialFile().getCsvFieldName();
        String convertToEnumFunction = attrSrcInfo.getInitialFile().getConvertToEnumFunction();
        
        switch(javaType)
        {
          case DOUBLE:
            double dblVal = Double.parseDouble(record.get(fieldName));
            BeanUtils.setProperty(person, attrName, dblVal);
            break;
          case INT:
            int intVal = Integer.parseInt(record.get(fieldName));
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, int.class);
              BeanUtils.setProperty(person, attrName, convertToEnumMethod.invoke(null, intVal));
            }
            else
            {
              BeanUtils.setProperty(person, attrName, intVal);
            }
            break;
          case STRING:
            String strVal = record.get(fieldName);
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, String.class);
              BeanUtils.setProperty(person, attrName, convertToEnumMethod.invoke(null, strVal));
            }
            else
            {
              BeanUtils.setProperty(person, attrName, strVal);
            }
            break;
          default:
            break;
          
        }
      }
      else if(attrSrcInfo.getLinkFile() != null)
      {
        
      }
      else
      {
        // ABORT
        AgentToLocationInitializer.LOGGER.fatal("The AgentAttribute [" + attrName + "] doesn't seem to have a link defined");
        System.exit(Constants.EX_DATAERR);
      }
      
      person.setBirthdateFromInitAge(false, simDate);
      if(person.getInitAge() >= 0)
      {
        long noOfMonthsOld = ChronoUnit.MONTHS.between(person.getBirthdate(), simDate);
        person.setHeight(PersonHeight.getHeightInInchesByAgeAndSex(noOfMonthsOld, person.getSex()));
        double personBMI = PersonBMI.getBMIByAgeAndSex(noOfMonthsOld, person.getSex());
        person.setWeight(PersonBMI.getWeightInPoundsByHeightInInchesAndBMI(person.getHeight(), personBMI));
      }
    }
  }
}
