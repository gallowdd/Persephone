package edu.pitt.gallowdd.persephone.location.initializer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.location.Household;
import edu.pitt.gallowdd.persephone.location.LocationFactory;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.location.School;
import edu.pitt.gallowdd.persephone.location.Workplace;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.EnumConverterMethods;
import edu.pitt.gallowdd.persephone.xml.base.LocationXmlType;
import edu.pitt.gallowdd.persephone.xml.base.LocationXmlType.LocationAttribute.Source;
import edu.pitt.gallowdd.persephone.xml.common.DatatypeXmlEnum;

/**
 * @author David Galloway
 *
 */
public class LocationInitializer {
  
  private static final Logger LOGGER = LogManager.getLogger(LocationInitializer.class.getName());
  
  /**
   * 
   */
  private LocationInitializer()
  {
    // No instance of this Initializer should be created
  }
  
  /**
   * 
   * @param record a record from a CVS Population file
   * @param locationType the Location Type from the parameter file
   * @return A Location Of the Type requested
   */
  public static GenericLocation initialize(CSVRecord record, LocationXmlType locationType)
  {
    GenericLocation retVal = null;
    
    try
    {
      List<LocationXmlType.LocationAttribute> locAttributes = locationType.getLocationAttribute();
      
      // Every record must have a field "id"
      String id = record.get("id");
      // Every record must have a field "latitude"
      double latitude = Double.parseDouble(record.get("latitude"));
      // Every record must have a field "longitude"
      double longitude = Double.parseDouble(record.get("longitude"));
      
      // Elevation is an optional value in the .csv file
      double elevation = Constants.DBL_NULL_LAT_LON_ELEV;
      
      try
      {
        elevation = Double.parseDouble(record.get("elevation"));
      }
      catch(IllegalArgumentException e)
      {
        LocationInitializer.LOGGER.warn("No elevation specified for record");
      }
      
      switch(locationType.getDataType())
      {
        case HOUSEHOLD:
          retVal = LocationFactory.createLocation(LocationTypeEnum.HOUSEHOLD, id, latitude, longitude, elevation);
          LocationInitializer.initializeHousehold((Household)retVal, locAttributes, record);
          LocationInitializer.LOGGER.trace("Initialized: " + retVal.toString());
          break;
        case WORKPLACE:
          retVal = LocationFactory.createLocation(LocationTypeEnum.WORKPLACE, id, latitude, longitude, elevation);
          LocationInitializer.initializeWorkplace((Workplace)retVal, locAttributes, record);
          LocationInitializer.LOGGER.trace("Initialized: " + retVal.toString());
          break;
        case SCHOOL:
          retVal = LocationFactory.createLocation(LocationTypeEnum.SCHOOL, id, latitude, longitude, elevation);
          LocationInitializer.initializeSchool((School)retVal, locAttributes, record);
          LocationInitializer.LOGGER.trace("Initialized: " + retVal.toString());
          break;
        default:
          LocationInitializer.LOGGER.fatal("Tried to initialize Unexpected Location Java Type: " + locationType.getName());
          System.exit(1);
          break;
      }
    }
    catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException | SecurityException e)
    {
      // ABORT
      LocationInitializer.LOGGER.fatal(e);
      System.exit(Constants.EX_DATAERR);
    }
    
    return retVal;
  }
  
  /**
   * 
   * @param household
   * @param locAttributes
   * @param record
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  private static void initializeHousehold(Household household, List<LocationXmlType.LocationAttribute> locAttributes, CSVRecord record)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException
  {
    if(household == null)
    {
      LocationInitializer.LOGGER.fatal("Tried to initialize null household");
      System.exit(1);
    }
    
    for(LocationXmlType.LocationAttribute locAtt : locAttributes)
    {
      DatatypeXmlEnum javaType = locAtt.getDataType();
      String attrName = locAtt.getAttrName();
      
      Source attrSrcInfo = locAtt.getSource();
      // There should be an init source. If not throw an exception
      if(attrSrcInfo == null)
      {
        // ABORT
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] is not dynamic, but there is no initial source information defined");
        System.exit(Constants.EX_DATAERR);
      }
      
      //<xsd:element name="source" minOccurs="0" maxOccurs="1">
      //<xsd:choice>
      //    <!-- The initialization file for agents has a field with this information -->
      //    <xsd:element name="initial_file" minOccurs="1" maxOccurs="1">
      //
      //    <!-- The initialization file for agents does NOT have a field with this information, 
      //         so there will be a separate file with agent ID and this field -->
      //    <xsd:element name="link_file" minOccurs="1" maxOccurs="1">
      //</xsd:choice>
      if(attrSrcInfo.getInitialFile() != null)
      {
        // The data is in the record already, so we just need to get it from the correct field
        String fieldName = attrSrcInfo.getInitialFile().getCsvFieldName();
        String convertToEnumFunction = attrSrcInfo.getInitialFile().getConvertToEnumFunction();
        
        switch(javaType)
        {
          case DOUBLE:
            double dblVal = Double.parseDouble(record.get(fieldName));
            BeanUtils.setProperty(household, attrName, dblVal);
            break;
          case INT:
            int intVal = Integer.parseInt(record.get(fieldName));
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, int.class);
              BeanUtils.setProperty(household, attrName, convertToEnumMethod.invoke(null, intVal));
            }
            else
            {
              BeanUtils.setProperty(household, attrName, intVal);
            }
            break;
          case STRING:
            String strVal = record.get(fieldName);
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, String.class);
              BeanUtils.setProperty(household, attrName, convertToEnumMethod.invoke(null, strVal));
            }
            else
            {
              BeanUtils.setProperty(household, attrName, strVal);
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
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] doesn't seem to have a link defined");
        System.exit(Constants.EX_DATAERR);
      }
    }
  }
  
  /**
   * 
   * @param school
   * @param locAttributes
   * @param record
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  private static void initializeSchool(School school, List<LocationXmlType.LocationAttribute> locAttributes, CSVRecord record)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException
  {
    if(school == null)
    {
      LocationInitializer.LOGGER.fatal("Tried to initialize null school");
      System.exit(Constants.EX_DATAERR);
    }
    
    for(LocationXmlType.LocationAttribute locAtt : locAttributes)
    {
      DatatypeXmlEnum javaType = locAtt.getDataType();
      String attrName = locAtt.getAttrName();
      
      Source attrSrcInfo = locAtt.getSource();
      // There should be an init source. If not throw an exception
      if(attrSrcInfo == null)
      {
        // ABORT
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] is not dynamic, but there is no initial source information defined");
        System.exit(Constants.EX_DATAERR);
      }
      
      //<xsd:element name="source" minOccurs="0" maxOccurs="1">
      //<xsd:choice>
      //    <!-- The initialization file for agents has a field with this information -->
      //    <xsd:element name="initial_file" minOccurs="1" maxOccurs="1">
      //
      //    <!-- The initialization file for agents does NOT have a field with this information, 
      //         so there will be a separate file with agent ID and this field -->
      //    <xsd:element name="link_file" minOccurs="1" maxOccurs="1">
      //</xsd:choice>
      if(attrSrcInfo.getInitialFile() != null)
      {
        // The data is in the record already, so we just need to get it from the correct field
        String fieldName = attrSrcInfo.getInitialFile().getCsvFieldName();
        String convertToEnumFunction = attrSrcInfo.getInitialFile().getConvertToEnumFunction();
        
        switch(javaType)
        {
          case DOUBLE:
            double dblVal = Double.parseDouble(record.get(fieldName));
            BeanUtils.setProperty(school, attrName, dblVal);
            break;
          case INT:
            int intVal = Integer.parseInt(record.get(fieldName));
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, int.class);
              BeanUtils.setProperty(school, attrName, convertToEnumMethod.invoke(null, intVal));
            }
            else
            {
              BeanUtils.setProperty(school, attrName, intVal);
            }
            break;
          case STRING:
            String strVal = record.get(fieldName);
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, String.class);
              BeanUtils.setProperty(school, attrName, convertToEnumMethod.invoke(null, strVal));
            }
            else
            {
              BeanUtils.setProperty(school, attrName, strVal);
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
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] doesn't seem to have a link defined");
        System.exit(Constants.EX_DATAERR);
      }
    }
  }
  
  /**
   * 
   * @param workplace
   * @param locAttributes
   * @param record
   * @throws IllegalAccessException
   * @throws InvocationTargetException
   * @throws SecurityException 
   * @throws NoSuchMethodException 
   */
  private static void initializeWorkplace(Workplace workplace, List<LocationXmlType.LocationAttribute> locAttributes, CSVRecord record)
      throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException
  {
    if(workplace == null)
    {
      LocationInitializer.LOGGER.fatal("Tried to initialize null workplace");
      System.exit(Constants.EX_DATAERR);
    }
    
    for(LocationXmlType.LocationAttribute locAtt : locAttributes)
    {
      DatatypeXmlEnum javaType = locAtt.getDataType();
      String attrName = locAtt.getAttrName();
      
      Source attrSrcInfo = locAtt.getSource();
      // There should be an init source. If not throw an exception
      if(attrSrcInfo == null)
      {
        // ABORT
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] is not dynamic, but there is no initial source information defined");
        System.exit(Constants.EX_DATAERR);
      }
      
      //<xsd:element name="source" minOccurs="0" maxOccurs="1">
      //<xsd:choice>
      //    <!-- The initialization file for agents has a field with this information -->
      //    <xsd:element name="initial_file" minOccurs="1" maxOccurs="1">
      //
      //    <!-- The initialization file for agents does NOT have a field with this information, 
      //         so there will be a separate file with agent ID and this field -->
      //    <xsd:element name="link_file" minOccurs="1" maxOccurs="1">
      //</xsd:choice>
      if(attrSrcInfo.getInitialFile() != null)
      {
        // The data is in the record already, so we just need to get it from the correct field
        String fieldName = attrSrcInfo.getInitialFile().getCsvFieldName();
        String convertToEnumFunction = attrSrcInfo.getInitialFile().getConvertToEnumFunction();
        
        switch(javaType)
        {
          case DOUBLE:
            double dblVal = Double.parseDouble(record.get(fieldName));
            BeanUtils.setProperty(workplace, attrName, dblVal);
            break;
          case INT:
            int intVal = Integer.parseInt(record.get(fieldName));
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, int.class);
              BeanUtils.setProperty(workplace, attrName, convertToEnumMethod.invoke(null, intVal));
            }
            else
            {
              BeanUtils.setProperty(workplace, attrName, intVal);
            }
            break;
          case STRING:
            String strVal = record.get(fieldName);
            if(convertToEnumFunction != null && !convertToEnumFunction.trim().equals(""))
            {
              Method convertToEnumMethod = EnumConverterMethods.class.getMethod(convertToEnumFunction, String.class);
              BeanUtils.setProperty(workplace, attrName, convertToEnumMethod.invoke(null, strVal));
            }
            else
            {
              BeanUtils.setProperty(workplace, attrName, strVal);
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
        LocationInitializer.LOGGER.fatal("The LocationAttribute [" + attrName + "] doesn't seem to have a link defined");
        System.exit(Constants.EX_DATAERR);
      }
    }
  }
}
