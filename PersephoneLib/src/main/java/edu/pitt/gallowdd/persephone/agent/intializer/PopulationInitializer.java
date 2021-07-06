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

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.agent.Person;
import edu.pitt.gallowdd.persephone.parameters.AgentJavaClassXmlEnum;
import edu.pitt.gallowdd.persephone.parameters.SimulationXmlType.SyntheticEnvironment;
import edu.pitt.gallowdd.persephone.util.Params;

/**
 * @author David Galloway
 *
 */
public class PopulationInitializer {
  
  private static final Logger LOGGER = LogManager.getLogger(PopulationInitializer.class.getName());
  
  /**
   * Opens a csv file for each agentType in the Params
   * 
   * E.g.
   * 
   *  <model>
   *    <agent name="people">
   *      <java_class>edu.pitt.gallowdd.persephone.agent.Person</java_class>
   *      ...
   *    <agent name="gq_people">
   *      <java_class>edu.pitt.gallowdd.persephone.agent.Person</java_class>
   *      ...
   *    </agent>
   *    ...
   *  </model>
   * 
   * @param population 
   * @param xmlSyntheticEnvironment the synthetic ecosystem information from the parameter file
   * @param simDate the current date in the simulation
   */
  public static void initialize(List<GenericAgent> population, SyntheticEnvironment xmlSyntheticEnvironment, LocalDate simDate)
  {
    String country = xmlSyntheticEnvironment.getCountry();
    String version = xmlSyntheticEnvironment.getVersion();
    String identifier = xmlSyntheticEnvironment.getIdentifier();
    
    // Verify that agentTypeName is defined in the parameter file
    for(int i = 0; i < Params.getAgentTypes().size(); ++i)
    {
      
      Path filePath = Paths.get(Params.getPopulationDirectory(), "country", country, version, identifier, 
          Params.getAgentTypes().get(i).getName() + ".csv");
      
      AgentJavaClassXmlEnum javaClass = Params.getAgentTypes().get(i).getJavaClass();
      
      try(
          final Reader in = new FileReader(filePath.toFile());
         )
      {
        switch(javaClass)
        {
          case EDU_PITT_GALLOWDD_PERSEPHONE_AGENT_PERSON:
            if(population == null)
            {
              population = new ArrayList<>();
            }
            else if(population.size() > 0)
            {
              if(!(population.get(0) instanceof Person))
              {
                // ABORT
                PopulationInitializer.LOGGER.fatal("Trying to add Person to a Population that has other agent types");
                System.exit(1);
              }
            }
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader().parse(in);
            int personCount = 0;
            for(CSVRecord record : records) 
            {
              GenericAgent agent = AgentInitializer.initialize(record, Params.getAgentTypes().get(i), simDate);
              population.add(agent);
              ++personCount;
            }
            PopulationInitializer.LOGGER.debug("Created a Person Population of " + personCount + " agents");
            break;
          case EDU_PITT_GALLOWDD_PERSEPHONE_AGENT_TEST:
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
