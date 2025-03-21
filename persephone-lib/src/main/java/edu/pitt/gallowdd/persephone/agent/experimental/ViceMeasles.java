package edu.pitt.gallowdd.persephone.agent.experimental;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;



/**
 * 
 * @author ddg5
 *
 */
public class ViceMeasles {
  
  /**
   * @param args 
   * 
   */
  public static void main(String[] args)
  {
    Map<String, Double> schoolToVaccRateInitMap = new HashMap<>();
    Map<String, Integer> schoolToCountInitMap = new HashMap<>();
    
    // Cpunty Data
    Map<String, Double> schoolToVaccRateMap = new HashMap<>();
    Map<String, Integer> schoolToCountMap = new HashMap<>();
    Map<String, Double> studentToVaccRateMap = new HashMap<>();
    // Read the school vaccination rate file
    ViceMeasles.storeSchoolInfo(schoolToVaccRateInitMap, schoolToCountInitMap);
    
    schoolToVaccRateInitMap.entrySet().forEach(entry->{
      System.out.println(entry.getKey() + " " + entry.getValue());
    });
    
    schoolToCountInitMap.entrySet().forEach(entry->{
      System.out.println(entry.getKey() + " " + entry.getValue());
    });
    
    //sp_id age sex race  school_id
    //Path filePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "people.csv");
    
    Path filePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "people.csv");
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        if(record.isConsistent())
        {
          String personId = record.get(0);
          String schoolId = record.get(4);
          if(!schoolId.trim().toUpperCase().equals("X"))
          {
            if(schoolToCountInitMap.containsKey(schoolId))
            {
              int newVal = schoolToCountInitMap.get(schoolId).intValue() + 1;
              schoolToCountInitMap.put(schoolId, newVal);
            }
            else
            {
              System.out.println("MISSING KEY[" + schoolId + "]");
              schoolToCountInitMap.put(schoolId, 1);
              schoolToVaccRateInitMap.put(schoolId, 0.95);
            }
            
            studentToVaccRateMap.put(personId, schoolToVaccRateInitMap.get(schoolId));
          }
        }
        else
        {
          System.out.println("INCONSISTENT RECORD " + record.toString());
          System.exit(2);
        }
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
    
    schoolToCountInitMap.entrySet().forEach(entry->{
      if(entry.getValue() > 0)
      {
        schoolToCountMap.put(entry.getKey(), entry.getValue());
        //System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + schoolToVacRateMap.get(entry.getKey()));
      }
    });
    
    System.out.println("------------------------");
    schoolToVaccRateInitMap.entrySet().forEach(entry->{
      if(schoolToCountMap.containsKey(entry.getKey()) && schoolToCountMap.get(entry.getKey()).intValue() > 0)
      {
        schoolToVaccRateMap.put(entry.getKey(), entry.getValue());
//        System.out.println(entry.getKey() + "\t" + entry.getValue());
      }
    });
    
    schoolToCountMap.entrySet().forEach(entry->{
      System.out.println(entry.getKey() + "\t" + entry.getValue() + "\t" + schoolToVaccRateMap.get(entry.getKey()));
      
    });
    
    System.out.println("------------------------");
    
    //Path outCsvFilePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "student_vacc_rate.csv");
    Path outCsvFilePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "student_vacc_rate_reduce_5_pct.csv");
    try(
        final Writer outStudent = new FileWriter(outCsvFilePath.toFile());
        final CSVPrinter csvPrinterStudentVaccRate = new CSVPrinter(outStudent, CSVFormat.DEFAULT.withHeader("student_id", "school_vacc_rate"));
       )
    {
      studentToVaccRateMap.entrySet().forEach(entry->{
        //System.out.println(entry.getKey() + "\t" + entry.getValue());
        try
        {
          csvPrinterStudentVaccRate.printRecord(entry.getKey(), entry.getValue());
        }
        catch(IOException e)
        {
          // TODO Auto-generated catch block
          e.printStackTrace();
          System.exit(2);
        }
      });
    }
    catch(IOException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
  }
  
  /**
   * 
   * @param schooltoVacRateMap
   * @param schoolToCountMap
   */
  private static void storeSchoolInfo(Map<String, Double> schoolToVacRateMap, Map<String, Integer> schoolToCountMap)
  {
    //school_id,vacc_rate
    Path filePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "school_vacc_rate_reduce_5_pct.csv");
   // Path filePath = Paths.get("/Volumes/LACIE_MAC/TexasStuff/texas_vice/PopulationAnalysis", "school_vacc_rate.csv");
    try(
        final Reader in = new FileReader(filePath.toFile());
       )
    {
      Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(in);
      
      for(CSVRecord record : records) 
      {
        if(record.isConsistent())
        {
          String id = record.get(0);
          String vaccRateStr = record.get(1);
          double vaccRate = Double.parseDouble(vaccRateStr);
          schoolToVacRateMap.put(id, vaccRate);
          schoolToCountMap.put(id, 0);
        }
        else
        {
          System.out.println("INCONSISTENT RECORD " + record.toString());
          System.exit(2);
        }
        
      }
    }
    catch(IOException e)
    {
      e.printStackTrace();
      System.exit(2);
    }
  }
}
