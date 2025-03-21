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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import java.util.Random;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.rng.UniformRandomProvider;
import org.apache.commons.rng.core.source64.L128X1024Mix;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.google.common.primitives.Ints;

import edu.pitt.gallowdd.persephone.PersephoneException.ErrorCode;

/**
 * The Static Utility Functions used by the simulation
 * 
 * @author David Galloway
 **/
public final class Utils {

  private static final Logger LOGGER = LogManager.getLogger(Utils.class.getName());
  private static L128X1024Mix RAND_SAMPLER;
  // The Random Number Generator that will be used to shuffle lists
  private static Random EXTERNAL_RAND_GEN;
  
  static
  {
    try
    {
      long[] seedArr =  { Params.getSeed() };
      Utils.RAND_SAMPLER = new L128X1024Mix(seedArr);
      Utils.EXTERNAL_RAND_GEN = new Random(Params.getSeed());
    }
    catch(Exception e)
    {
      throw new ExceptionInInitializerError(e);
    }
  }
  
  // No instances of this class
  private Utils() {}
  
  /**
   * Given an age, will assign a random birth date from 
   * (currentDate - (age + 1) years + 1 day) to (currentDate - age years)
   * I.e. the birth date could be exactly 'age' years ago today or any day in the 
   * year prior.
   * @param age the current age
   * @return the random birth date generated
   **/
  public static LocalDate getRandomBirthdate(int age) 
  {
    LocalDate currentDate = LocalDate.now();
    
    return Utils.getRandomBirthdate(age, currentDate);
  }
  
  /**
   * Given an int age and the current date, will assign a random birthdate from 
   * (currentDate - (age + 1) years + 1 day) to (currentDate - age years)
   * I.e. the birthdate could be exactly age years ago today or any day in the 
   * year prior.
   * @param age the current age
   * @param currentDate the current date of the simulation
   * @return the random birth date generated
   **/
  public static LocalDate getRandomBirthdate(int age, LocalDate currentDate) 
  {
    LocalDate endDate = currentDate.minusYears(age);
    LocalDate beginDate = currentDate.plusDays(1L).minusYears(age + 1);
    
    long amount = ChronoUnit.DAYS.between(beginDate, endDate);
    int intAmount = Ints.checkedCast(amount);
    LocalDate retVal = null;
    try
    {
      int daysToAdd = Utils.getRandomInt(intAmount + 1);
      retVal = beginDate.plusDays(daysToAdd);
    }
    catch(IllegalArgumentException e)
    {
      Utils.LOGGER.error(ErrorCode.ERR_LNG_DWNCNVRT_INT.message(String.valueOf(amount)), e);
      e.printStackTrace();
      System.exit(1);
    }
    return retVal;
  }
  
  /**
   * Returns the next pseudorandom, uniformly distributed double value between 0.0 and 1.0 
   * drawn from our random number generator's sequence.
   * @return a pseudorandom, uniformly distributed int value between 0.0 (inclusive) and1.0 (exclusive).
   **/
  public static double getRandom()
  {
    return Utils.RAND_SAMPLER.nextDouble();
  }
  
  /**
   * Returns a pseudorandom, uniformly distributed int value between 0 (inclusive) and the specified value (exclusive), 
   * drawn from our random number generator's sequence.
   * @param n - the bound on the random number to be returned. Must be positive.
   * @return a pseudorandom, uniformly distributed int value between 0 (inclusive) and n (exclusive).
   * @throws IllegalArgumentException - if n is not positive.
   **/
  public static int getRandomInt(int n) throws IllegalArgumentException 
  {
    return Utils.RAND_SAMPLER.nextInt(n);
  }
  
  /**
   * 
   * @return the static RandomNumberSampler used by the simulation
   */
  public static UniformRandomProvider getRandomNumberSampler()
  {
    return Utils.RAND_SAMPLER;
  }
  
  /**
   * 
   * @return the static RandomNumberGenerator used by the simulation
   */
  public static Random getRandomNumberGenerator()
  {
    return Utils.EXTERNAL_RAND_GEN;
  }
  
  /**
   * Try to find a file given the resources that the Classloader will search.
   * 
   * @param filename the filename to search for amongst the resources
   * @return the String that is the full path to the file
   * @throws FileNotFoundException
   */
  public static String getResource(String filename) throws FileNotFoundException
  {
    URL resource = Params.class.getClassLoader().getResource(filename);
    try
    {
      Objects.requireNonNull(resource);
    }
    catch(NullPointerException e)
    {
      throw new FileNotFoundException("File not found: " + filename);
    }
    return resource.getFile();
  }
  
  /**
   * 
   * @param weights an array of doubles that are all weights
   * @return the integer that is the index of the weight that was chosen at random
   */
  public static int getRandomIntFromArrayOfWeights(double[] weights)
  {
    double total = 0.0;
    double [] probArr = new double[weights.length];
    
    for(int i = 0; i < weights.length; ++i)
    {
      total += Math.abs(weights[i]);
      probArr[i] = Math.abs(weights[i]);
    }
    
    if(total > 0.0)
    {
      for(int i = 0; i < weights.length; ++i)
      {
        probArr[i] = probArr[i] / total;
      }
      double randVal = Utils.getRandom();
      double currentUpperBound = 0.0;
      for(int i = 0; i < weights.length; ++i)
      {
        currentUpperBound += probArr[i];
        if(randVal < currentUpperBound)
        {
          return i;
        }
      }
      return weights.length - 1;
    }
    else
    {
      return -1;
    }
  }
  
  /**
   * 
   * @param xmlFileName the XML File to validate
   * @param xmlSchemaFileName the XML Schema File to validate against
   * @return true if the XML File is valid, false if it is not
   */
  public static boolean validateXmlFile(String xmlFileName, String xmlSchemaFileName)
  {
    SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    try
    {
      File xmlFile = new File(xmlFileName);
      File xmlSchemaFile = new File(xmlSchemaFileName);
      if(xmlFile == null || !xmlFile.exists())
      {
        xmlFile = new File(Utils.getResource(xmlFileName));
      }
      
      if(xmlSchemaFile == null || !xmlSchemaFile.exists())
      {
        xmlSchemaFile = new File(Utils.getResource(xmlSchemaFileName));
      }
      
      Schema schema = schemaFactory.newSchema(xmlSchemaFile);
      Validator validator = schema.newValidator();
      validator.validate(new StreamSource(xmlFile));
      return true;
    }
    catch(SAXException | IOException e)
    {
      Utils.LOGGER.error(e);
      Utils.LOGGER.error(ErrorCode.ERR_XML_VALIDATION.message(xmlFileName, xmlSchemaFileName), e);
      return false;
    }
  }
}