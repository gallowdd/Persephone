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

package edu.pitt.gallowdd.persephone.location.initializer;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.xml.common.CountryCodeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.LocationDatatypeXmlEnum;
import edu.pitt.gallowdd.persephone.xml.common.PopVersionXmlEnum;
import edu.pitt.gallowdd.persephone.xml.runtime.SimulationXmlType.SyntheticEnvironmentDescriptor;
import edu.pitt.gallowdd.persephone.util.Params;

/**
 * @author David Galloway
 *
 */
public class LocationListManagerInitializer {
  
  private static final Logger LOGGER = LogManager.getLogger(LocationListManagerInitializer.class.getName());
  
  /**
   * @param locations 
   * @param xmlSyntheticEnvironment the synthetic environment information from the parameter file
   * @param simDate the current date in the simulation
   */
  public static void initialize(List<GenericLocation> locations, SyntheticEnvironmentDescriptor xmlSyntheticEnvironment)
  {
    CountryCodeXmlEnum country = xmlSyntheticEnvironment.getCountry();
    PopVersionXmlEnum version = xmlSyntheticEnvironment.getVersion();
    String identifier = xmlSyntheticEnvironment.getIdentifier();
    
    // Verify that agentTypeName is defined in the parameter file
    for(int i = 0; i < Params.getLocations().size(); ++i)
    {
      
      Path filePath = Paths.get(Params.getPopulationDirectory(), "country", country.value(), version.value(), identifier, 
          Params.getLocations().get(i).getFilename().value());
      
      LocationDatatypeXmlEnum javaClass = Params.getLocations().get(i).getDataType();
      
      try(
          final Reader in = new FileReader(filePath.toFile());
         )
      {
        if(locations == null)
        {
          locations = new ArrayList<>();
        }
        Iterable<CSVRecord> records = CSVFormat.RFC4180.builder().setHeader().setSkipHeaderRecord(true).build().parse(in);
        int locationCount = 0;
        switch(javaClass)
        {
          case HOUSEHOLD:
            for(CSVRecord record : records) 
            {
              GenericLocation location = LocationInitializer.initialize(record, Params.getLocations().get(i));
              locations.add(location);
              ++locationCount;
            }
            LocationListManagerInitializer.LOGGER.debug("Added " + locationCount + " households");
            break;
          case SCHOOL:
            for(CSVRecord record : records) 
            {
              GenericLocation location = LocationInitializer.initialize(record, Params.getLocations().get(i));
              locations.add(location);
              ++locationCount;
            }
            LocationListManagerInitializer.LOGGER.debug("Added " + locationCount + " schools");
            break;
          case WORKPLACE:
            for(CSVRecord record : records) 
            {
              GenericLocation location = LocationInitializer.initialize(record, Params.getLocations().get(i));
              locations.add(location);
              ++locationCount;
            }
            LocationListManagerInitializer.LOGGER.debug("Added " + locationCount + " workplaces");
            break;
          default:
            break;
          
        }
      }
      catch(IOException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
      catch(RuntimeException e)
      {
        e.printStackTrace();
        System.exit(2);
      }
    }
  }
  
}
