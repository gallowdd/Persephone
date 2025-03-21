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

package edu.pitt.gallowdd.persephone.helper;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.agent.attribute.CensusRelationship;
import edu.pitt.gallowdd.persephone.agent.attribute.Race;
import edu.pitt.gallowdd.persephone.helper.database.GroupQuarterDAO;
import edu.pitt.gallowdd.persephone.helper.database.HouseholdDAO;
import edu.pitt.gallowdd.persephone.helper.database.SchoolDAO;
import edu.pitt.gallowdd.persephone.helper.database.WorkplaceDAO;
import edu.pitt.gallowdd.persephone.helper.initialization.AgentInitInfo;
import edu.pitt.gallowdd.persephone.helper.initialization.GroupQuarterInitInfo;
import edu.pitt.gallowdd.persephone.helper.initialization.HouseholdInitInfo;
import edu.pitt.gallowdd.persephone.helper.initialization.SchoolInitInfo;
import edu.pitt.gallowdd.persephone.helper.initialization.WorkplaceInitInfo;
import edu.pitt.gallowdd.persephone.helper.util.GroupQuarterFilenameFilter;
import edu.pitt.gallowdd.persephone.helper.util.GroupQuarterPeopleFilenameFilter;
import edu.pitt.gallowdd.persephone.helper.util.HouseholdFilenameFilter;
import edu.pitt.gallowdd.persephone.helper.util.PeopleFilenameFilter;
import edu.pitt.gallowdd.persephone.helper.util.SchoolFilenameFilter;
import edu.pitt.gallowdd.persephone.helper.util.WorkplaceFilenameFilter;
import edu.pitt.gallowdd.persephone.xml.synthenv.ObjectFactory;
import edu.pitt.gallowdd.persephone.xml.synthenv.SyntheticEnvironment;
import edu.pitt.gallowdd.persephone.xml.synthenv.SyntheticEnvironment.Agent;
import edu.pitt.gallowdd.persephone.xml.synthenv.SyntheticEnvironment.Location;
import edu.pitt.gallowdd.persephone.xml.common.AgentDatatypeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.AgentFilenameXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.CountryCodeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.LocationDatatypeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.LocationFilenameXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.PopVersionXmlEnum;
import edu.pitt.gallowdd.persephone.util.ArraySelector;
import edu.pitt.gallowdd.persephone.util.ArraySelectorException;
import edu.pitt.gallowdd.persephone.util.Constants;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;


/**
 * @author David Galloway
 *
 */
public class RtiToPersephonePopulationFileConverter {

  private static final Logger LOGGER = LogManager.getLogger(RtiToPersephonePopulationFileConverter.class.getName());
  
  private static final CreationStage STAGE = CreationStage.CREATE_FILES;
  private static final String ORIGINAL_RTI_FILE_DIR = "/Volumes/DaveSeagate/FRED_POPULATIONS/US_2010.v3";
  private static final String LOAD_MATCH_DB_DIR = "/Volumes/DaveSeagate/FRED_POPULATIONS/RTI_2010_ver1_Elevation_LOAD_DB";
  private static final String LOAD_MATCH_DB_DIR_COMPLETE = "/Volumes/DaveSeagate/FRED_POPULATIONS/RTI_2010_ver1_Elevation_LOAD_DB_COMPLETE";
  private static final String LOAD_MATCHING_DIR = "/Volumes/DaveSeagate/FRED_POPULATIONS/RTI_2010_ver1_Elevation";
  private static final String LOAD_MATCHING_DIR_COMPLETE = "/Volumes/DaveSeagate/FRED_POPULATIONS/RTI_2010_ver1_Elevation_COMPLETE";
  
  //private static final String ID_UNSET = "XXX_00000000-0000-0000-0000-000000000000";
  
  /**
   * 
   * @param argv
   */
  public static void main(String[] argv)
  {
    //Path topLevelDirPath = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR, "TEST");
    Path topLevelDirPath = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR);
    File topLevelDir = topLevelDirPath.toFile();
    
    String [] popDirectoryArr = topLevelDir.list(new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        if(name.matches("\\d{5}"))
        {
          return true;
        }
        
        return false;
      }
    });
    RtiToPersephonePopulationFileConverter.LOGGER.debug(Arrays.toString(popDirectoryArr));
    
    switch(RtiToPersephonePopulationFileConverter.STAGE)
    {
      case CREATE_FILES:
        break;
      case LOAD_MATCH_DB:
        // Add Schools to matching database 
        for(int i = 0; i < popDirectoryArr.length; ++i)
        {
          Path pathToPopDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCH_DB_DIR, popDirectoryArr[i]);
          RtiToPersephonePopulationFileConverter.storeSchools(pathToPopDir, popDirectoryArr[i]);
        }
        
        // Add Workplaces to matching database
        for(int i = 0; i < popDirectoryArr.length; ++i)
        {
          Path pathToPopDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCH_DB_DIR, popDirectoryArr[i]);
          RtiToPersephonePopulationFileConverter.storeWorkplaces(pathToPopDir, popDirectoryArr[i]);
        }
        
        // Add Group Quarters to matching database
        for(int i = 0; i < popDirectoryArr.length; ++i)
        {
          Path pathToPopDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCH_DB_DIR, popDirectoryArr[i]);
          Path pathToCompletedDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCH_DB_DIR_COMPLETE, popDirectoryArr[i]);
          RtiToPersephonePopulationFileConverter.storeGroupQuarters(pathToPopDir, popDirectoryArr[i]);
          
          // Move files to COMPLETED directory
          try
          {
            Files.move(pathToPopDir, pathToCompletedDir, StandardCopyOption.ATOMIC_MOVE);
          }
          catch(IOException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(2);
          }
        }
        
        break;
      case RESUBMIT_FILES:
        break;
      default:
        break;
      
    }
    
    
    // Load households and create the people files ... and the connection files for people to schools and people to workplaces
    switch(RtiToPersephonePopulationFileConverter.STAGE)
    {
      case CREATE_FILES:
        ObjectFactory factory = new ObjectFactory();
        // For households, I only need to store the information for a single FIPS code (i.e. Household IDs are not used across counties)
        for(int i = 0; i < popDirectoryArr.length; ++i)
        {
          SyntheticEnvironment synthEnv = factory.createSyntheticEnvironment();
          synthEnv.setCountry(CountryCodeXmlEnum.USA);
          synthEnv.setVersion(PopVersionXmlEnum.PERSEPHONE_V_1_0);
          synthEnv.setIdentifier(popDirectoryArr[i]);
          
          Path pathToPopDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR, popDirectoryArr[i]);
          Path pathToCompletedDir = Paths.get(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR_COMPLETE, popDirectoryArr[i]);
          RtiToPersephonePopulationFileConverter.storeHouseholds(pathToPopDir, popDirectoryArr[i]);
          int householdCount = RtiToPersephonePopulationFileConverter.parseAndConvertHouseholdFile(pathToPopDir);
          int schoolCount = RtiToPersephonePopulationFileConverter.parseAndConvertSchoolFile(pathToPopDir);
          int workplaceCount = RtiToPersephonePopulationFileConverter.parseAndConvertWorkplaceFile(pathToPopDir);
          int peopleCount = RtiToPersephonePopulationFileConverter.parseAndConvertPeopleFile(pathToPopDir);
          // C - N - P - M
          int gqCounts[] = RtiToPersephonePopulationFileConverter.parseAndConvertGroupQuartersFile(pathToPopDir);
          int gqPeopleCount = RtiToPersephonePopulationFileConverter.parseAndConvertGroupQuartersPeopleFile(pathToPopDir);
          
          Agent synthAgent = factory.createSyntheticEnvironmentAgent();
          synthAgent.setAgentType(AgentDatatypeXmlEnum.PERSON);
          synthAgent.setEntryCount(BigInteger.valueOf(peopleCount));
          synthAgent.setFilename(AgentFilenameXmlEnum.PEOPLE_CSV);
          
          Agent synthGqAgent = factory.createSyntheticEnvironmentAgent();
          synthGqAgent.setAgentType(AgentDatatypeXmlEnum.PERSON);
          synthGqAgent.setEntryCount(BigInteger.valueOf(gqPeopleCount));
          synthGqAgent.setFilename(AgentFilenameXmlEnum.GQ_PEOPLE_CSV);
          synthEnv.getAgent().add(synthGqAgent);
          
          Location synthHousehold = factory.createSyntheticEnvironmentLocation();
          synthHousehold.setLocationType(LocationDatatypeXmlEnum.HOUSEHOLD);
          synthHousehold.setEntryCount(BigInteger.valueOf(householdCount));
          synthHousehold.setFilename(LocationFilenameXmlEnum.HOUSEHOLDS_CSV);
          synthEnv.getLocation().add(synthHousehold);
          
          Location synthSchool = factory.createSyntheticEnvironmentLocation();
          synthSchool.setLocationType(LocationDatatypeXmlEnum.SCHOOL);
          synthSchool.setEntryCount(BigInteger.valueOf(schoolCount));
          synthSchool.setFilename(LocationFilenameXmlEnum.SCHOOLS_CSV);
          synthEnv.getLocation().add(synthSchool);
          
          Location synthWorkplace = factory.createSyntheticEnvironmentLocation();
          synthWorkplace.setLocationType(LocationDatatypeXmlEnum.WORKPLACE);
          synthWorkplace.setEntryCount(BigInteger.valueOf(workplaceCount));
          synthWorkplace.setFilename(LocationFilenameXmlEnum.WORKPLACES_CSV);
          synthEnv.getLocation().add(synthWorkplace);
          
          Location synthCollege = factory.createSyntheticEnvironmentLocation();
          synthCollege.setLocationType(LocationDatatypeXmlEnum.COLLEGE_DORM);
          synthCollege.setEntryCount(BigInteger.valueOf(gqCounts[0]));
          synthCollege.setFilename(LocationFilenameXmlEnum.COLLEGE_DORMS_CSV);
          synthEnv.getLocation().add(synthCollege);
          
          Location synthNursing = factory.createSyntheticEnvironmentLocation();
          synthNursing.setLocationType(LocationDatatypeXmlEnum.NURSING_FACILITY);
          synthNursing.setEntryCount(BigInteger.valueOf(gqCounts[1]));
          synthNursing.setFilename(LocationFilenameXmlEnum.NURSING_FACILITIES_CSV);
          synthEnv.getLocation().add(synthNursing);
          
          Location synthPrison = factory.createSyntheticEnvironmentLocation();
          synthPrison.setLocationType(LocationDatatypeXmlEnum.PRISON);
          synthPrison.setEntryCount(BigInteger.valueOf(gqCounts[2]));
          synthPrison.setFilename(LocationFilenameXmlEnum.PRISONS_CSV);
          synthEnv.getLocation().add(synthPrison);
          
          Location synthMilitary = factory.createSyntheticEnvironmentLocation();
          synthMilitary.setLocationType(LocationDatatypeXmlEnum.MILITARY_BARRACKS);
          synthMilitary.setEntryCount(BigInteger.valueOf(gqCounts[3]));
          synthMilitary.setFilename(LocationFilenameXmlEnum.MILITARY_BARRACKS_CSV);
          synthEnv.getLocation().add(synthMilitary);
          
          HouseholdDAO.deleteAll();
          
          RtiToPersephonePopulationFileConverter.jaxbObjectToXML(synthEnv, pathToPopDir);
          
          if(!(new File(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR_COMPLETE)).exists())
          {
            try
            {
              FileUtils.forceMkdir(new File(RtiToPersephonePopulationFileConverter.LOAD_MATCHING_DIR_COMPLETE));
            }
            catch(IOException e)
            {
              // TODO Auto-generated catch block
              e.printStackTrace();
              System.exit(2);
            }
          }
          
          // Move files to COMPLETED directory
          try
          {
            Files.move(pathToPopDir, pathToCompletedDir, StandardCopyOption.ATOMIC_MOVE);
          }
          catch(IOException e)
          {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.exit(2);
          }
        }
        break;
      case LOAD_MATCH_DB:
        break;
      case RESUBMIT_FILES:
        break;
      default:
        break;
      
    }
  }

  /**
   * @param pathToPopDir the Java Path to the directory to search
   * @param populationDirName the RTI population directory to search
   */
  private static void storeHouseholds(Path pathToPopDir, String populationDirName)
  {
    HouseholdFilenameFilter origFilt = new HouseholdFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] hhFilenameArr = populationDir.list(origFilt);
    if(hhFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Household file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), hhFilenameArr[0]);
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing " + filePath.toFile().toString() + " ...");
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      int count = 0;
      for(CSVRecord record : records) 
      {
        count++;
        String id = record.get("sp_id");
        
        int hhId = Integer.parseInt(id);
        
        if(!HouseholdDAO.exists(hhId))
        {
          UUID uuid = UUID.randomUUID();
          String hhIdPersephone = "HH_" + uuid.toString();
          RtiToPersephonePopulationFileConverter.LOGGER.debug("Storing [" + hhId +"] >> [" + hhIdPersephone  + "]");
          HouseholdDAO.insert(hhId, hhIdPersephone);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.trace("ID [" + hhId +"] is already stored with a value of [" + HouseholdDAO.findMatchingPersephoneHouseholdId(hhId) + "]");
        }
      }
      
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Count of HH Records in " + filePath.toFile().toString() + " is " +  count);
    }
    catch(IOException e)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error(e);
      System.exit(2);
    }
  }
  
  /**
   * @param pathToPopDir the Java Path to the directory to search
   * @param populationDirName the RTI population directory to search
   */
  private static void storeGroupQuarters(Path pathToPopDir, String populationDirName)
  {
    GroupQuarterFilenameFilter origFilt = new GroupQuarterFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] gqFilenameArr = populationDir.list(origFilt);
    if(gqFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Group Quarter file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), gqFilenameArr[0]);
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing " + filePath.toFile().toString() + " ...");
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      int count = 0;
      for(CSVRecord record : records) 
      {
        count++;
        String id = record.get("sp_id");
        
        String gqType = record.get("gq_type").trim().toUpperCase();
        String prependType = "";
        switch(gqType)
        {
          case "P":
            prependType = "PRS";
            break;
          case "N":
            prependType = "NRS";
            break;
          case "M":
            prependType = "MIL";
            break;
          case "C":
            prependType = "CLG";
            break;
          default:
            RtiToPersephonePopulationFileConverter.LOGGER.error("The gq_type [" + gqType + "] is not recognized");
            System.exit(2);
        }
        
        int gqId = Integer.parseInt(id);
        
        if(!GroupQuarterDAO.exists(gqId))
        {
          UUID uuid = UUID.randomUUID();
          String gqIdPersephone = prependType + "_" + uuid.toString();
          RtiToPersephonePopulationFileConverter.LOGGER.debug("Storing [" + gqId +"] >> [" + gqIdPersephone  + "]");
          GroupQuarterDAO.insert(gqId, gqIdPersephone);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.trace("ID [" + gqId +"] is already stored with a value of [" + GroupQuarterDAO.findMatchingPersephoneGroupQuarterId(gqId) + "]");
        }
      }
      
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Count of GQ Records in " + filePath.toFile().toString() + " is " +  count);
    }
    catch(IOException e)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error(e);
      System.exit(2);
    }
  }
  
  /**
   * @param pathToPopDir the Java Path to the directory to search
   * @param populationDirName the RTI population directory to search
   */
  private static void storeSchools(Path pathToPopDir, String populationDirName)
  {
    SchoolFilenameFilter origFilt = new SchoolFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] schoolFilenameArr = populationDir.list(origFilt);
    if(schoolFilenameArr == null || schoolFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one School file per directory: [" + populationDir + "]");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), schoolFilenameArr[0]);
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing " + filePath.toFile().toString() + " ...");
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      int count = 0;
      for(CSVRecord record : records) 
      {
        count++;
        String id = record.get("sp_id");
        
        int schoolId = Integer.parseInt(id);
        
        if(!SchoolDAO.exists(schoolId))
        {
          UUID uuid = UUID.randomUUID();
          String schoolIdPersephone = "SCL_" + uuid.toString();
          RtiToPersephonePopulationFileConverter.LOGGER.debug("Storing [" + schoolId +"] >> [" + schoolIdPersephone + "]");
          SchoolDAO.insert(schoolId, schoolIdPersephone);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.trace("ID [" + schoolId +"] is already stored with a value of [" + SchoolDAO.findMatchingPersephoneSchoolId(schoolId) + "]");
        }
      }
      
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Count of School Records in " + filePath.toFile().toString() + " is " +  count);
    }
    catch(IOException e)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error(e);
      System.exit(2);
    }
  }
  
  /**
   * @param pathToPopDir the Java Path to the directory to search
   * @param populationDirName the RTI population directory to search
   */
  private static void storeWorkplaces(Path pathToPopDir, String populationDirName)
  {
    WorkplaceFilenameFilter origFilt = new WorkplaceFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] workplaceFilenameArr = populationDir.list(origFilt);
    if(workplaceFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Workplace file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), workplaceFilenameArr[0]);
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing " + filePath.toFile().toString() + " ...");
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      int count = 0;
      for(CSVRecord record : records) 
      {
        count++;
        String id = record.get("sp_id");
        
        int workplaceId = Integer.parseInt(id);
        
        if(!WorkplaceDAO.exists(workplaceId))
        {
          UUID uuid = UUID.randomUUID();
          String workplaceIdPersephone = "WP_" + uuid.toString();
          RtiToPersephonePopulationFileConverter.LOGGER.debug("Storing [" + workplaceId +"] >> [" + workplaceIdPersephone + "]");
          WorkplaceDAO.insert(workplaceId, workplaceIdPersephone);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.trace("ID [" + workplaceId +"] is already stored with a value of [" + WorkplaceDAO.findMatchingPersephoneWorkplaceId(workplaceId) + "]");
        }
      }
      
      RtiToPersephonePopulationFileConverter.LOGGER.debug("Count of School Records in " + filePath.toFile().toString() + " is " +  count);
    }
    catch(IOException e)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error(e);
      System.exit(2);
    }
  }
  
  /**
   * 
   * @param pathToPopDir
   */
  private static int parseAndConvertPeopleFile(Path pathToPopDir)
  {
    int countPeople = 0;
    //sp_id sp_hh_id  age sex race  relate  school_id work_id
    //final String FLD_ID = "sp_id";
    final String FLD_SP_HH_ID = "sp_hh_id";
    final String FLD_AGE = "age";
    final String FLD_SEX = "sex";
    final String FLD_RACE = "race";
    final String FLD_RELATE = "relate";
    final String FLD_SCHL_ID = "school_id";
    final String FLD_WORK_ID = "work_id";
    
    final String FLD_OUT_ID = "id";
    final String FLD_OUT_RACE_CD = "race_code";
    final String FLD_OUT_REL_CD = "relationship_code";
    final String FLD_OUT_HH_ID = "hh_id";
    final String FLD_OUT_SCHL_ID = "school_id";
    final String FLD_OUT_WORK_ID = "workplace_id";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing people file in: " + pathToPopDir.toString() + " ...");
    
    PeopleFilenameFilter origFilt = new PeopleFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] populationFilenameArr = populationDir.list(origFilt);
    if(populationFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one People file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), populationFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outFilePathPeople = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("people.txt", "people.csv"));
    Path outFilePathPeopleHousehold = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("people.txt", "Person_to_Household.csv"));
    Path outFilePathPeopleSchool = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("people.txt", "Person_to_School.csv"));
    Path outFilePathPeopleWorkplace = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("people.txt", "Person_to_Workplace.csv"));
    
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer outPeople = new FileWriter(outFilePathPeople.toFile());
        final Writer outPeopleHousehold = new FileWriter(outFilePathPeopleHousehold.toFile());
        final Writer outPeopleSchool = new FileWriter(outFilePathPeopleSchool.toFile());
        final Writer outPeopleWorkplace = new FileWriter(outFilePathPeopleWorkplace.toFile());
        final CSVPrinter csvPrinterPeople = new CSVPrinter(outPeople, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_AGE, FLD_SEX, FLD_OUT_RACE_CD, FLD_OUT_REL_CD));
        final CSVPrinter csvPrinterPeopleHousehold = new CSVPrinter(outPeopleHousehold, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_HH_ID));
        final CSVPrinter csvPrinterPeopleSchool = new CSVPrinter(outPeopleSchool, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_SCHL_ID));
        final CSVPrinter csvPrinterPeopleWorkplace = new CSVPrinter(outPeopleWorkplace, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_WORK_ID));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var agentInfo = new AgentInitInfo();
        
        /*
         * E.g.
         * sp_id,sp_hh_id,serialno,stcotrbg,age,sex,race,sporder,relate,sp_school_id,sp_work_id
         * 164281596,11048181,2007000951052,"420659503005",42,1,1,1,0,,514269692
         */
        UUID uuid = UUID.randomUUID();
        agentInfo.setId("PER_" + uuid.toString());
        agentInfo.setAge(Integer.parseInt(record.get(FLD_AGE)));
        agentInfo.setSexCode(record.get(FLD_SEX));
        agentInfo.setRaceCode(Integer.parseInt(record.get(FLD_RACE)));
        agentInfo.setHhRelationshipCode(Integer.parseInt(record.get(FLD_RELATE)));
        
        // FLD_ID, FLD_AGE, FLD_SEX, FLD_RACE
        csvPrinterPeople.printRecord(agentInfo.getId(), agentInfo.getAge(), agentInfo.getSexCode(), agentInfo.getRaceCode(), agentInfo.getHhRelationshipCode());
        RtiToPersephonePopulationFileConverter.LOGGER.trace("Person [" + agentInfo.getId() + "] added");
        
        // NOW >>>>
        // Create the household link
        // Everyone should have a household
        final int spHouseholdId = Integer.parseInt(record.get(FLD_SP_HH_ID));
        if(HouseholdDAO.exists(spHouseholdId))
        {
          String newId = HouseholdDAO.findMatchingPersephoneHouseholdId(spHouseholdId);
          
          csvPrinterPeopleHousehold.printRecord(agentInfo.getId(), newId);
          RtiToPersephonePopulationFileConverter.LOGGER.trace(agentInfo.getId() + " >> " + newId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.debug("HH_ID [" + spHouseholdId +"] was not found");
          System.exit(2);
        }
        
        // Create the School Link if there is one
        String temp = null;
        temp = record.get(FLD_SCHL_ID);
        if((temp != null && !temp.trim().equals("") && StringUtils.containsOnly(temp.trim(), "0123456789")))
        {
          int spSchoolId = Integer.parseInt(temp);
          if(SchoolDAO.exists(spSchoolId))
          {
            String newId = SchoolDAO.findMatchingPersephoneSchoolId(spSchoolId);
            
            csvPrinterPeopleSchool.printRecord(agentInfo.getId(), newId);
            RtiToPersephonePopulationFileConverter.LOGGER.trace("Person -> School: " + agentInfo.getId() + " -> " + newId);
          }
          else
          {
            RtiToPersephonePopulationFileConverter.LOGGER.debug("SCHOOL_ID [" + spSchoolId +"] was not found");
            System.exit(2);
          }
        }
        
        // Create the Workplace link if there is one
        temp = record.get(FLD_WORK_ID);
        if((temp != null && !temp.trim().equals("") && StringUtils.containsOnly(temp.trim(), "0123456789")))
        {
          int spWorkplacelId = Integer.parseInt(temp);
          if(WorkplaceDAO.exists(spWorkplacelId))
          {
            String newId = WorkplaceDAO.findMatchingPersephoneWorkplaceId(spWorkplacelId);
            
            csvPrinterPeopleWorkplace.printRecord(agentInfo.getId(), newId);
            RtiToPersephonePopulationFileConverter.LOGGER.trace("Person -> Workplace: " + agentInfo.getId() + " -> " + newId);
          }
          else
          {
            RtiToPersephonePopulationFileConverter.LOGGER.debug("WP_ID [" + spWorkplacelId +"] was not found");
            System.exit(2);
          }
        }
        ++countPeople;
      }
      csvPrinterPeople.flush();
      csvPrinterPeopleHousehold.flush();
      csvPrinterPeopleSchool.flush();
      csvPrinterPeopleWorkplace.flush();
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    
    return countPeople;
  }
  
  /**
   * 
   * @param pathToPopDir
   */
  private static int parseAndConvertHouseholdFile(Path pathToPopDir) //, String populationDirName)
  {
    int countHouseholds = 0;
    final String FLD_ID = "id";
    final String FLD_SP_ID = "sp_id";
    final String FLD_STCOTRBG = "stcotrbg";
    final String FLD_STATE_CD = "state_fips_cd";
    final String FLD_COUNTY_CD = "county_fips_cd";
    final String FLD_CENSUS_TRCT = "tract_fips_cd";
    final String FLD_HH_INC = "hh_income";
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_ELEVATION = "elevation";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing household file in: " + pathToPopDir.toString() + " ...");
    
    HouseholdFilenameFilter origFilt = new HouseholdFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] hhFilenameArr = populationDir.list(origFilt);
    if(hhFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Household file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), hhFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outFilePath = Paths.get(directory.toString(), hhFilenameArr[0].replaceAll("households.txt", "households.csv"));
    
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer out = new FileWriter(outFilePath.toFile());
        final CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_HH_INC, FLD_LAT, FLD_LON, FLD_ELEVATION));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var hhInfo = new HouseholdInitInfo();
        
        /*
         * e.g.
         * sp_id,serialno,stcotrbg,hh_race,hh_income,hh_size,hh_age,latitude,longitude
         * 7472361,2009000653014,"471690901001",1,32000,3,38,36.3890346,-86.2615538
         */
        final int householdId = Integer.parseInt(record.get(FLD_SP_ID));
        String newId = "";
        final String stcotrbg = record.get(FLD_STCOTRBG);
        
        hhInfo.setStateCode(Integer.parseInt(stcotrbg.substring(0, 2)));
        hhInfo.setCountyCode(Integer.parseInt(stcotrbg.substring(2, 5)));
        hhInfo.setCensusTract(Integer.parseInt(stcotrbg.substring(5, 10)));
        hhInfo.setIncome(Double.parseDouble(record.get(FLD_HH_INC)));
        hhInfo.setLatitude(Double.parseDouble(record.get(FLD_LAT)));
        hhInfo.setLongitude(Double.parseDouble(record.get(FLD_LON)));
        // Make sure to set elevation after lat/lon to catch weird 0.0 elevations
        hhInfo.setElevation(Double.parseDouble(record.get(FLD_ELEVATION)));
        
        if(HouseholdDAO.exists(householdId))
        {
          newId = HouseholdDAO.findMatchingPersephoneHouseholdId(householdId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.debug("HH_ID [" + householdId +"] was not found");
          System.exit(2);
        }
        
        // FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_HH_INC, FLD_LAT, FLD_LON, FLD_ALT
        csvPrinter.printRecord(newId, String.format("%02d", hhInfo.getStateCode()), 
            String.format("%03d", hhInfo.getCountyCode()), 
            String.format("%06d", hhInfo.getCensusTract()), 
            hhInfo.getIncome(), hhInfo.getLatitude(), hhInfo.getLongitude(), hhInfo.getElevation());
        RtiToPersephonePopulationFileConverter.LOGGER.trace("Household [" + newId + "] added to csv file");
        ++countHouseholds;
      }
      csvPrinter.flush();
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return countHouseholds;
  }
  
  /**
   * 
   * @param pathToPopDir
   */
  private static int parseAndConvertSchoolFile(Path pathToPopDir)
  {
    int countSchools = 0;
    final String FLD_ID = "id";
    final String FLD_SP_ID = "sp_id";
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_STCO = "stco";
    final String FLD_STATE_CD = "state_fips_cd";
    final String FLD_COUNTY_CD = "county_fips_cd";
    final String FLD_ELEVATION = "elevation";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing school file in: " + pathToPopDir.toString() + " ...");
    
    SchoolFilenameFilter origFilt = new SchoolFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] schoolFilenameArr = populationDir.list(origFilt);
    if(schoolFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one School file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), schoolFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outFilePath = Paths.get(directory.toString(), schoolFilenameArr[0].replaceAll("txt", "csv"));
    
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer out = new FileWriter(outFilePath.toFile());
        final CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_LAT, FLD_LON, FLD_STATE_CD, FLD_COUNTY_CD, FLD_ELEVATION));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var schoolInfo = new SchoolInitInfo();
        
        /*
         * e.g.
         * sp_id,name,stabbr,address,city,county,zipcode,zip4,nces_id,total,prek,kinder,gr01_gr12,ungraded,latitude,longitude,source,stco
         * 450105714,"ALLEN D NEASE SENIOR HIGH SCHOOL","FL",,,"ST. JOHNS COUNTY",,,"120174002262",1655,0,0,1655,,30.076269,-81.449617,"NCES","12109"
         */
        final int schoolId = Integer.parseInt(record.get(FLD_SP_ID));
        String newId = "";
        final String stco = record.get(FLD_STCO);
        
        schoolInfo.setLatitude(Double.parseDouble(record.get(FLD_LAT)));
        schoolInfo.setLongitude(Double.parseDouble(record.get(FLD_LON)));
        schoolInfo.setStateCode(Integer.parseInt(stco.substring(0, 2)));
        schoolInfo.setCountyCode(Integer.parseInt(stco.substring(2, 5)));
        // Make sure to set elevation after lat/lon to catch weird 0.0 elevations
        schoolInfo.setElevation(Double.parseDouble(record.get(FLD_ELEVATION)));
        
        
        if(SchoolDAO.exists(schoolId))
        {
          if(schoolInfo.getLatitude() == Constants.DBL_NULL_LAT_LON_ELEV|| schoolInfo.getLongitude() == Constants.DBL_NULL_LAT_LON_ELEV || schoolInfo.getElevation() == Constants.DBL_NULL_LAT_LON_ELEV)
          {
            SchoolInitInfo tempInfo = findSchoolInformationInSchoolFile(stco, schoolId);
            schoolInfo.setLatitude(tempInfo.getLatitude());
            schoolInfo.setLongitude(tempInfo.getLongitude());
            schoolInfo.setElevation(tempInfo.getElevation());
          }
          
          newId = SchoolDAO.findMatchingPersephoneSchoolId(schoolId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.debug("SCHL_ID [" + schoolId +"] was not found");
          System.exit(2);
        }
        
        // FLD_ID, FLD_LAT, FLD_LON, FLD_STATE_CD, FLD_COUNTY_CD,
        csvPrinter.printRecord(newId, schoolInfo.getLatitude(), schoolInfo.getLongitude(), 
            String.format("%02d", schoolInfo.getStateCode()) , 
            String.format("%03d", schoolInfo.getCountyCode()), schoolInfo.getElevation());
        RtiToPersephonePopulationFileConverter.LOGGER.info("School [" + newId + "] added to csv file");
        ++countSchools;
      }
      csvPrinter.flush();
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return countSchools;
  }
  
  /**
   * 
   * @param pathToPopDir
   */
  private static int parseAndConvertWorkplaceFile(Path pathToPopDir)
  {
    int countWorkplaces = 0;
    final String FLD_ID = "id";
    final String FLD_SP_ID = "sp_id";
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_ELEVATION = "elevation";
    
    WorkplaceFilenameFilter origFilt = new WorkplaceFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] workplaceFilenameArr = populationDir.list(origFilt);
    if(workplaceFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Workplace file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), workplaceFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outFilePath = Paths.get(directory.toString(), workplaceFilenameArr[0].replaceAll("txt", "csv"));
    
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer out = new FileWriter(outFilePath.toFile());
        final CSVPrinter csvPrinter = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_LAT, FLD_LON, FLD_ELEVATION));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var workplaceInfo = new WorkplaceInitInfo();
        
        /*
         * e.g.
         * sp_id,workers,latitude,longitude
         * 500148573,1292,32.4291539,-87.0236461
         */
        final int workplaceId = Integer.parseInt(record.get(FLD_SP_ID));
        String newId = "";
        
        workplaceInfo.setLatitude(Double.parseDouble(record.get(FLD_LAT)));
        workplaceInfo.setLongitude(Double.parseDouble(record.get(FLD_LON)));
        // Make sure to set elevation after lat/lon to catch weird 0.0 elevations
        workplaceInfo.setElevation(Double.parseDouble(record.get(FLD_ELEVATION)));
        
        if(WorkplaceDAO.exists(workplaceId))
        {
          if(workplaceInfo.getElevation() == 0.0 || workplaceInfo.getElevation() == Constants.DBL_NULL_LAT_LON_ELEV)
          {
            workplaceInfo.setElevation(RtiToPersephonePopulationFileConverter.findMeanElevationInWorkplaceFile(filePath));
          }
          newId = WorkplaceDAO.findMatchingPersephoneWorkplaceId(workplaceId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.debug("WP_ID [" + workplaceId +"] was not found");
          System.exit(2);
        }
        
        // FLD_ID, FLD_LAT, FLD_LON
        csvPrinter.printRecord(newId, workplaceInfo.getLatitude(), workplaceInfo.getLongitude(), workplaceInfo.getElevation());
        RtiToPersephonePopulationFileConverter.LOGGER.info("Workplace [" + newId + "] added to csv file");
        ++countWorkplaces;
      }
      csvPrinter.flush();
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return countWorkplaces;
  }
  
  
 // sp_id gq_type stcotrbg  persons latitude  longitude elevation
  /**
   * 
   * @param pathToPopDir
   * @param populationDirName
   */
  private static int[] parseAndConvertGroupQuartersFile(Path pathToPopDir)
  {
    int[] retArr = {0, 0, 0, 0};
    
    final String FLD_ID = "id";
    final String FLD_SP_ID = "sp_id";
    final String FLD_GQ_TYPE = "gq_type";
    final String FLD_STCOTRBG = "stcotrbg";
    final String FLD_STATE_CD = "state_fips_cd";
    final String FLD_COUNTY_CD = "county_fips_cd";
    final String FLD_CENSUS_TRCT = "tract_fips_cd";
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_ELEVATION = "elevation";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing Group Quarters file in: " + pathToPopDir.toString() + " ...");
    
    GroupQuarterFilenameFilter origFilt = new GroupQuarterFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] gqFilenameArr = populationDir.list(origFilt);
    if(gqFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one Group Quarters file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), gqFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outPrisonFilePath = Paths.get(directory.toString(), gqFilenameArr[0].replaceAll("gq.txt", "prisons.csv"));
    Path outNursingFilePath = Paths.get(directory.toString(), gqFilenameArr[0].replaceAll("gq.txt", "nursing_facilities.csv"));
    Path outCampusFilePath = Paths.get(directory.toString(), gqFilenameArr[0].replaceAll("gq.txt", "college_dorms.csv"));
    Path outMilitaryFilePath = Paths.get(directory.toString(), gqFilenameArr[0].replaceAll("gq.txt", "military_barracks.csv"));
    
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer outPrisonFileWiter = new FileWriter(outPrisonFilePath.toFile());
        final Writer outNursingFileWriter = new FileWriter(outNursingFilePath.toFile());
        final Writer outCampusFileWriter = new FileWriter(outCampusFilePath.toFile());
        final Writer outMilitaryFileWriter = new FileWriter(outMilitaryFilePath.toFile());
        final CSVPrinter csvPrisonPrinter = new CSVPrinter(outPrisonFileWiter, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ELEVATION));
        final CSVPrinter csvNursingPrinter = new CSVPrinter(outNursingFileWriter, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ELEVATION));
        final CSVPrinter csvCampusPrinter = new CSVPrinter(outCampusFileWriter, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ELEVATION));
        final CSVPrinter csvMilitaryPrinter = new CSVPrinter(outMilitaryFileWriter, CSVFormat.DEFAULT.withHeader(FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ELEVATION));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var gqInfo = new GroupQuarterInitInfo();
        
        /*
         * e.g.
         * sp_id,serialno,stcotrbg,latitude,longitude
         * 7472361,2009000653014,"471690901001",1,32000,3,38,36.3890346,-86.2615538
         */
        final int gqId = Integer.parseInt(record.get(FLD_SP_ID));
        String newId = "";
        final String stcotrbg = record.get(FLD_STCOTRBG);
        
        gqInfo.setStateCode(Integer.parseInt(stcotrbg.substring(0, 2)));
        gqInfo.setCountyCode(Integer.parseInt(stcotrbg.substring(2, 5)));
        gqInfo.setCensusTract(Integer.parseInt(stcotrbg.substring(5, 10)));
        gqInfo.setGroupQuartersType(record.get(FLD_GQ_TYPE));
        gqInfo.setLatitude(Double.parseDouble(record.get(FLD_LAT)));
        gqInfo.setLongitude(Double.parseDouble(record.get(FLD_LON)));
        // Make sure to set elevation after lat/lon to catch weird 0.0 elevations
        gqInfo.setElevation(Double.parseDouble(record.get(FLD_ELEVATION)));
        
        if(GroupQuarterDAO.exists(gqId))
        {
          newId = GroupQuarterDAO.findMatchingPersephoneGroupQuarterId(gqId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.error("GQ_ID [" + gqId +"] was not found");
          System.exit(2);
        }
        //groupQuartersType.equals("C") || groupQuartersType.equals("N") || groupQuartersType.equals("P") || groupQuartersType.equals("M")
        if(gqInfo.getGroupQuartersType().equals("C"))
        {
          // FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ALT
          csvCampusPrinter.printRecord(newId, String.format("%02d", gqInfo.getStateCode()), 
              String.format("%03d", gqInfo.getCountyCode()), 
              String.format("%06d", gqInfo.getCensusTract()), 
              gqInfo.getLatitude(), gqInfo.getLongitude(), gqInfo.getElevation());
          RtiToPersephonePopulationFileConverter.LOGGER.trace("Campus [" + newId + "] added to csv file");
          ++retArr[0];
        }
        else if(gqInfo.getGroupQuartersType().equals("N"))
        {
          // FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ALT
          csvNursingPrinter.printRecord(newId, String.format("%02d", gqInfo.getStateCode()), 
              String.format("%03d", gqInfo.getCountyCode()), 
              String.format("%06d", gqInfo.getCensusTract()), 
              gqInfo.getLatitude(), gqInfo.getLongitude(), gqInfo.getElevation());
          RtiToPersephonePopulationFileConverter.LOGGER.trace("Nursing Home [" + newId + "] added to csv file");
          ++retArr[1];
        }
        else if(gqInfo.getGroupQuartersType().equals("P"))
        {
          // FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ALT
          csvPrisonPrinter.printRecord(newId, String.format("%02d", gqInfo.getStateCode()), 
              String.format("%03d", gqInfo.getCountyCode()), 
              String.format("%06d", gqInfo.getCensusTract()), 
              gqInfo.getLatitude(), gqInfo.getLongitude(), gqInfo.getElevation());
          RtiToPersephonePopulationFileConverter.LOGGER.trace("Prison [" + newId + "] added to csv file");
          ++retArr[2];
        }
        else if(gqInfo.getGroupQuartersType().equals("M"))
        {
          // FLD_ID, FLD_STATE_CD, FLD_COUNTY_CD, FLD_CENSUS_TRCT, FLD_LAT, FLD_LON, FLD_ALT
          csvMilitaryPrinter.printRecord(newId, String.format("%02d", gqInfo.getStateCode()), 
              String.format("%03d", gqInfo.getCountyCode()), 
              String.format("%06d", gqInfo.getCensusTract()), 
              gqInfo.getLatitude(), gqInfo.getLongitude(), gqInfo.getElevation());
          RtiToPersephonePopulationFileConverter.LOGGER.trace("Military Barracks [" + newId + "] added to csv file");
          ++retArr[3];
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.error("GQ_TYPE [" + gqInfo.getGroupQuartersType() +"] was not recognized");
          System.exit(2);
        }
      }
      csvCampusPrinter.flush();
      csvNursingPrinter.flush();
      csvPrisonPrinter.flush();
      csvMilitaryPrinter.flush();
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return retArr;
  }
  
  
  /**
   * 
   * @param pathToPopDir
   * @param populationDirName
   */
  private static int parseAndConvertGroupQuartersPeopleFile(Path pathToPopDir)
  {
    int countGqPeople = 0;
    //sp_id sp_gq_id  age sex
    //final String FLD_ID = "sp_id";
    final String FLD_SP_GQ_ID = "sp_gq_id";
    final String FLD_AGE = "age";
    final String FLD_SEX = "sex";
    
    final String FLD_OUT_ID = "id";
    final String FLD_OUT_RACE_CD = "race_code";
    final String FLD_OUT_REL_CD = "relationship_code";
    final String FLD_OUT_PRISON_ID = "prison_id";
    final String FLD_OUT_COLLEGE_DORM_ID = "college_dorm_id";
    final String FLD_OUT_NURSING_FACILITY_ID = "nursing_facility_id";
    final String FLD_OUT_MILTARY_BARRACKS_ID = "military_barracks_id";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Parsing people file in: " + pathToPopDir.toString() + " ...");
    
    GroupQuarterPeopleFilenameFilter origFilt = new GroupQuarterPeopleFilenameFilter();
    File populationDir = pathToPopDir.toFile();
    //Find the original file
    String [] populationFilenameArr = populationDir.list(origFilt);
    if(populationFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one People file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(pathToPopDir.toString(), populationFilenameArr[0]);
    File directory = new File(pathToPopDir.toString()
        .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
        .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0"));
    if(!directory.exists())
    {
      try
      {
        FileUtils.forceMkdir(directory);
      }
      catch(IOException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
        System.exit(2);
      }
    }
    
    Path outFilePathGroupQuartersPeople = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("gq_people.txt", "gq_people.csv"));
    Path outFilePathPeoplePrison = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("gq_people.txt", "Person_to_Prison.csv"));
    Path outFilePathPeopleCollegeDorm = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("gq_people.txt", "Person_to_College_Dorm.csv"));
    Path outFilePathPeopleNursingFacility = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("gq_people.txt", "Person_to_Nursing_Facility.csv"));
    Path outFilePathPeopleMilitaryBarracks = Paths.get(directory.toString(), populationFilenameArr[0].replaceAll("gq_people.txt", "Person_to_Military_Barracks.csv"));
    
    //https://www.bop.gov/about/statistics/statistics_inmate_race.jsp
    try(
        final Reader in = new FileReader(filePath.toFile());
        final Writer outGqPeople = new FileWriter(outFilePathGroupQuartersPeople.toFile());
        final Writer outGqPeoplePrison = new FileWriter(outFilePathPeoplePrison.toFile());
        final Writer outGqPeopleCollegeDorm = new FileWriter(outFilePathPeopleCollegeDorm.toFile());
        final Writer outGqPeopleNursingFacility = new FileWriter(outFilePathPeopleNursingFacility.toFile());
        final Writer outGqPeopleMilitaryBarracks = new FileWriter(outFilePathPeopleMilitaryBarracks.toFile());
        final CSVPrinter csvPrinterGqPeople = new CSVPrinter(outGqPeople, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_AGE, FLD_SEX, FLD_OUT_RACE_CD, FLD_OUT_REL_CD));
        final CSVPrinter csvPrinterGqPeoplePrison = new CSVPrinter(outGqPeoplePrison, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_PRISON_ID));
        final CSVPrinter csvPrinterGqPeopleCollegeDorm = new CSVPrinter(outGqPeopleCollegeDorm, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_COLLEGE_DORM_ID));
        final CSVPrinter csvPrinterGqPeopleNursingFacility = new CSVPrinter(outGqPeopleNursingFacility, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_NURSING_FACILITY_ID));
        final CSVPrinter csvPrinterGqPeopleMilitaryBarracks = new CSVPrinter(outGqPeopleMilitaryBarracks, CSVFormat.DEFAULT.withHeader(FLD_OUT_ID, FLD_OUT_MILTARY_BARRACKS_ID));
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        var agentInfo = new AgentInitInfo();
        
        /*
         * E.g.
         * sp_id,sp_hh_id,serialno,stcotrbg,age,sex,race,sporder,relate,sp_school_id,sp_work_id
         * 164281596,11048181,2007000951052,"420659503005",42,1,1,1,0,,514269692
         */
        UUID uuid = UUID.randomUUID();
        int age = Integer.parseInt(record.get(FLD_AGE));
        agentInfo.setId("PER_" + uuid.toString());
        
        agentInfo.setAge(age);
        agentInfo.setSexCode(record.get(FLD_SEX));
        
        // NOW >>>>
        // Create the household link
        // Everyone should have a group quarter Id
        final int spGqId = Integer.parseInt(record.get(FLD_SP_GQ_ID));
        
        if(GroupQuarterDAO.exists(spGqId))
        {
          String newId = GroupQuarterDAO.findMatchingPersephoneGroupQuarterId(spGqId);
          
          String firstThree = newId.substring(0, 3);
          
          // The synthetic population created by RTI does not include race code for agents in group quarters. 
          // I am randomly assigning race code based on published rates as best as I can
          int raceCode = switch(firstThree) {
            case "PRS" -> {
              // These values come from https://www.bop.gov/about/statistics/statistics_inmate_race.jsp
              // Asian 2,280 1.5%
              // Black 58,405  38.6%
              // Native American 3,596 2.4%
              // White 87,065  57.5%
              Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(),
                  Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
              double[] raceWeightArray = {57.5, 38.6, 1.5, 2.4, 0.9, 0.9, 0.9};
              ArraySelector<Integer> raceSelector = new ArraySelector<>();
              Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
              if(raceValue.isPresent())
              {
                yield raceValue.get().intValue();
              }
              yield Constants.INT_UNSET;
            }
            case "NRS" -> {
              // These values come from https://www.cdc.gov/nchs/data/nnhsd/Estimates/nnhs/Estimates_Demographics_Tables.pdf#Table01
              // Age       White          Black         Other
              // Under 65: 10.0 * 127100, 21.9 * 40700, 23.7 * 7200
              // 65–74:    10.5 * 134200, 18.6 * 34500, 17.8 * 5300
              // 75–84:    31.8 * 405800, 29.3 * 54600, 27.5 * 8300
              // 85+:      47.7 * 608900, 30.2 * 56300, 31.0 * 9300
              if(age < 65)
              {
                Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(), 
                    Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
                double[] raceWeightArray = {10.0 * 127100, 21.9 * 40700, 23.7 * 7200 / 5.0, 23.7 * 7200 / 5.0,
                    23.7 * 7200 / 5.0, 23.7 * 7200 / 5.0, 23.7 * 7200 / 5.0};
                ArraySelector<Integer> raceSelector = new ArraySelector<>();
                
                Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
                if(raceValue.isPresent())
                {
                  yield raceValue.get().intValue();
                }
                yield Constants.INT_UNSET;
              }
              else if(age < 75)
              {
                Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(), 
                    Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
                double[] raceWeightArray = {10.5 * 134200, 18.6 * 34500, 17.8 * 5300 / 5.0, 17.8 * 5300 / 5.0,
                    17.8 * 5300 / 5.0, 17.8 * 5300 / 5.0, 17.8 * 5300 / 5.0};
                ArraySelector<Integer> raceSelector = new ArraySelector<>();
                Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
                if(raceValue.isPresent())
                {
                  yield raceValue.get().intValue();
                }
                yield Constants.INT_UNSET;
              }
              else if(age < 85)
              {
                Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(), 
                    Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
                double[] raceWeightArray = {31.8 * 405800, 29.3 * 54600, 27.5 * 8300 / 5.0, 27.5 * 8300 / 5.0,
                    27.5 * 8300 / 5.0, 27.5 * 8300 / 5.0, 27.5 * 8300 / 5.0};
                ArraySelector<Integer> raceSelector = new ArraySelector<>();
                Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
                if(raceValue.isPresent())
                {
                  yield raceValue.get().intValue();
                }
                yield Constants.INT_UNSET;
              }
              else
              {
                Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(), 
                    Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
                double[] raceWeightArray = {47.7 * 608900, 30.2 * 56300, 31.0 * 9300 / 5.0, 31.0 * 9300 / 5.0,
                    31.0 * 9300 / 5.0, 31.0 * 9300 / 5.0, 31.0 * 9300 / 5.0};
                ArraySelector<Integer> raceSelector = new ArraySelector<>();
                Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
                if(raceValue.isPresent())
                {
                  yield raceValue.get().intValue();
                }
                yield Constants.INT_UNSET;
              }
            }
            case "MIL" -> {
              // These values come from https://download.militaryonesource.mil/12038/MOS/Reports/2018-demographics-report.pdf
              Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(),
                  Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
              double[] raceWeightArray = {70.8, 16.8, 4.4, 0.7, 0.3, 1.0, 2.5};
              ArraySelector<Integer> raceSelector = new ArraySelector<>();
              Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
              if(raceValue.isPresent())
              {
                yield raceValue.get().intValue();
              }
              yield Constants.INT_UNSET;
            }
            case "CLG" -> {
              // These values come from https://nces.ed.gov/programs/coe/indicator_cha.asp
              Integer[] raceArray = {Race.WHITE.getValue(), Race.BLACK.getValue(), Race.ASIAN.getValue(), Race.AMERICAN_INDIAN.getValue(),
                  Race.ALASKA_NATIVE.getValue(), Race.PACIFIC_ISLANDER.getValue(), Race.TWO_OR_MORE.getValue()};
              double[] raceWeightArray = {11.3, 3.1, 1.4, 0.5, 0.5, 0.5, 0.6};
              ArraySelector<Integer> raceSelector = new ArraySelector<>();
              Optional<Integer> raceValue = raceSelector.getRandomItemFromArrayGivenArrayOfWeights(raceArray, raceWeightArray);
              if(raceValue.isPresent())
              {
                yield raceValue.get().intValue();
              }
              yield Constants.INT_UNSET;
            }
            default -> Constants.INT_UNSET;
          };
          
          if(raceCode == Constants.INT_UNSET)
          {
            RtiToPersephonePopulationFileConverter.LOGGER.debug("Unrecognized Group Quarters ID prefix [" + firstThree +"]");
            System.exit(2);
          }
          
          // Set that race code for the agent
          agentInfo.setRaceCode(raceCode);
          
          // The synthetic population created by RTI does not include relationship code for agents in group quarters. 
          // I am trying to assign these values in the most appropirate match that I can
          int censusRelationshipCode = switch(firstThree) {
            case "PRS" -> CensusRelationship.INST_GROUP_QUARTER.getValue();
            case "NRS" -> CensusRelationship.ROOMER_BOARDER.getValue();
            case "MIL" -> CensusRelationship.NONINST_GROUP_QUARTER.getValue();
            case "CLG" -> CensusRelationship.ROOMER_BOARDER.getValue();
            default -> Constants.INT_UNSET;
          };
          
          if(censusRelationshipCode == Constants.INT_UNSET)
          {
            RtiToPersephonePopulationFileConverter.LOGGER.debug("Unrecognized Group Quarters ID prefix [" + firstThree +"]");
            System.exit(2);
          }
          
          // Set that relationship code for the agent
          agentInfo.setHhRelationshipCode(censusRelationshipCode);
          
          // FLD_ID, FLD_AGE, FLD_SEX, FLD_RACE
          csvPrinterGqPeople.printRecord(agentInfo.getId(), agentInfo.getAge(), agentInfo.getSexCode(), agentInfo.getRaceCode(), agentInfo.getHhRelationshipCode());
          RtiToPersephonePopulationFileConverter.LOGGER.trace("GQ Person [" + agentInfo.getId() + "] added");
          
          // Now create a link for each agent based on the type of group quarter
          switch(firstThree)
          {
            case "PRS":
              csvPrinterGqPeoplePrison.printRecord(agentInfo.getId(), newId);
              break;
            case "NRS":
              csvPrinterGqPeopleNursingFacility.printRecord(agentInfo.getId(), newId);
              break;
            case "MIL":
              csvPrinterGqPeopleMilitaryBarracks.printRecord(agentInfo.getId(), newId);
              break;
            case "CLG":
              csvPrinterGqPeopleCollegeDorm.printRecord(agentInfo.getId(), newId);
              break;
            default:
              RtiToPersephonePopulationFileConverter.LOGGER.debug("Unrecognized Group Quarters ID prefix [" + firstThree +"]");
              System.exit(2);
              break;
          }
          RtiToPersephonePopulationFileConverter.LOGGER.trace(firstThree + " link: " + agentInfo.getId() + " >> " + newId);
        }
        else
        {
          RtiToPersephonePopulationFileConverter.LOGGER.debug("GQ_ID [" + spGqId +"] was not found");
          System.exit(2);
        }
        ++countGqPeople;
      }
      csvPrinterGqPeople.flush();
      csvPrinterGqPeoplePrison.flush();
      csvPrinterGqPeopleNursingFacility.flush();
      csvPrinterGqPeopleMilitaryBarracks.flush();
      csvPrinterGqPeopleCollegeDorm.flush();
    }
    catch(IOException | ArraySelectorException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return countGqPeople;
  }
  
  /**
   * 
   * @param populationDirName
   * @param findSchoolSpId
   * @return
   */
  private static SchoolInitInfo findSchoolInformationInSchoolFile(String populationDirName, int findSchoolSpId)
  {
    var schoolInfo = new SchoolInitInfo();
    final String FLD_SP_ID = "sp_id";
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_STCO = "stco";
    final String FLD_ELEVATION = "elevation";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Trying to find school information in: " + RtiToPersephonePopulationFileConverter.ORIGINAL_RTI_FILE_DIR + " ...");
    
    SchoolFilenameFilter origFilt = new SchoolFilenameFilter();
    File populationDir = Paths.get(RtiToPersephonePopulationFileConverter.ORIGINAL_RTI_FILE_DIR, populationDirName).toFile();
    //Find the original file
    String [] schoolFilenameArr = populationDir.list(origFilt);
    if(schoolFilenameArr.length != 1)
    {
      RtiToPersephonePopulationFileConverter.LOGGER.error("There should be exactly one School file per directory");
      System.exit(2);
    }
    
    Path filePath = Paths.get(RtiToPersephonePopulationFileConverter.ORIGINAL_RTI_FILE_DIR, populationDirName, schoolFilenameArr[0]);
    
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      boolean isFound = false;
      int countLat = 0;
      int countLon = 0;
      int countElev = 0;
      double totLat = 0.0;
      double totLon = 0.0;
      double totElev = 0.0;
      
      for(CSVRecord record : records) 
      {
        final int schoolId = Integer.parseInt(record.get(FLD_SP_ID));
        final String stco = record.get(FLD_STCO);
        final double lat = Double.parseDouble(record.get(FLD_LAT));
        final double lon = Double.parseDouble(record.get(FLD_LON));
        final double elev = Double.parseDouble(record.get(FLD_ELEVATION));
        if(schoolId == findSchoolSpId)
        {
          isFound = true;
          schoolInfo.setLatitude(lat);
          schoolInfo.setLongitude(lon);
          schoolInfo.setStateCode(Integer.parseInt(stco.substring(0, 2)));
          schoolInfo.setCountyCode(Integer.parseInt(stco.substring(2, 5)));
          // Make sure to set elevation after lat/lon to catch weird 0.0 elevations
          schoolInfo.setElevation(elev);
          
          if(!(schoolInfo.getLatitude() == -9999.0 || schoolInfo.getLongitude() == -9999.0 || schoolInfo.getElevation() == -9999.0))
          {
            return schoolInfo;
            
          }
        }
        
        if(stco.equals(populationDirName))
        {
          if(lat != 0.0)
          {
            totLat += lat;
            ++countLat;
          }
          
          if(lon != 0.0)
          {
            totLon += lon;
            ++countLon;
          }
          
          if(lat != 0.0 && lon != 0.0)
          {
            totElev += elev;
            ++countElev;
          }
        }
      }
      
      if(isFound)
      {
        schoolInfo.setLatitude(countLat > 0 ? totLat / (double)countLat : 0.0);
        schoolInfo.setLongitude(countLon > 0 ? totLon / (double)countLon : 0.0);
        schoolInfo.setElevation(countElev > 0 ? totElev / (double)countElev : 0.0);
      }
      else
      {
        RtiToPersephonePopulationFileConverter.LOGGER.error("Unable to find the matching school, sp_id = " + findSchoolSpId + 
            " in file " + filePath.toString());
        schoolInfo.setLatitude(0.0);
        schoolInfo.setLongitude(0.0);
        schoolInfo.setElevation(0.0);
      }
      

    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    
    return schoolInfo;
  }
  
  
  /**
   * 
   * @param populationDirName
   * @return the mean elevation
   */
  private static double findMeanElevationInWorkplaceFile(Path workplaceFilePath)
  {
    //sp_id latitude  longitude elevation
    final String FLD_LAT = "latitude";
    final String FLD_LON = "longitude";
    final String FLD_ELEVATION = "elevation";
    
    RtiToPersephonePopulationFileConverter.LOGGER.debug("Trying to find workplace elevation information in: " + workplaceFilePath.toString() + " ...");
    
    try(
        final Reader in = new FileReader(workplaceFilePath.toFile());
       )
    {
      
      Iterable<CSVRecord> records = CSVFormat.TDF.withHeader().parse(in);
      int countElev = 0;
      double totElev = 0.0;
      
      for(CSVRecord record : records) 
      {
        final double lat = Double.parseDouble(record.get(FLD_LAT));
        final double lon = Double.parseDouble(record.get(FLD_LON));
        final double elev = Double.parseDouble(record.get(FLD_ELEVATION));
        
        if(lat != 0.0 && lon != 0.0)
        {
          totElev += elev;
          ++countElev;
        }
      }
      
      return (countElev > 0 ? totElev / (double)countElev : 0.0);
    }
    catch(IOException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
      System.exit(2);
    }
    return 0.0;
  }
  
  private static void jaxbObjectToXML(SyntheticEnvironment synthEnv, Path pathToPopDir) 
  {
    try
    {
      File xmlFile = new File(pathToPopDir.toString()
          .replaceAll("FRED_POPULATIONS", "Persephone_Synthetic_Environment")
          .replaceAll("RTI_2010_ver1_Elevation", "/country/USA/Persephone.v1.0") + Constants.FS + Constants.SYNTHENV_FILENAME);
      
      //Create JAXB Context
      JAXBContext jaxbContext = JAXBContext.newInstance(SyntheticEnvironment.class);
           
      //Create Marshaller
      Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
      //Required formatting??
      jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
      
      //Writes XML file to file-system
      jaxbMarshaller.marshal(synthEnv, xmlFile); 
    } 
    catch(JAXBException e) 
    {
      e.printStackTrace();
      System.exit(2);
    }
  }
  
  @SuppressWarnings("javadoc")
  public enum CreationStage { LOAD_MATCH_DB, CREATE_FILES, RESUBMIT_FILES };

}
