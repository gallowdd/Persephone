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

package edu.pitt.gallowdd.persephone.agent;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.PersephoneException.ErrorCode;
import edu.pitt.gallowdd.persephone.agent.attribute.CensusRelationship;
import edu.pitt.gallowdd.persephone.agent.attribute.Race;
import edu.pitt.gallowdd.persephone.agent.attribute.Sex;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * An agent that represents a Person.
 * 
 * @author David Galloway
 *
 */
public class Person extends GenericAgent {
  
  private static final Logger LOGGER = LogManager.getLogger(Person.class.getName());
  
  /**
   * The value to prepend to all Ids of this type
   */
  public static final String ID_PREPEND = AgentTypeEnum.PERSON.toString();
  
  private int initAge;
  private double height;
  private double weight;
  private LocalDate birthdate;
  private Sex sex;
  private Race race;
  private CensusRelationship relationship;
  private final Map<LocationTypeEnum, Id> favoriteLocationMap;
  
  /**
   * Create a new Person agent (used if creating a new agent with a random id)
   * 
   * @throws IdException if the id is invalid
   */
  public Person() throws IdException
  {
    super(Id.create(Person.ID_PREPEND).getIdString());
    this.initAge = Constants.INT_UNSET;
    this.height = Constants.INT_UNSET;
    this.weight = Constants.INT_UNSET;
    this.sex = Sex.UNSET;
    this.race = Race.UNSET;
    this.relationship = CensusRelationship.UNDEFINED;
    this.favoriteLocationMap = new HashMap<>();
  }
  
  /**
   * Create a new Person agent (used if reading from an input file that has agent id already)
   * 
   * @param idString the Person's idString
   * 
   * @throws IdException if the idString is invalid
   */
  public Person(String idString) throws IdException
  {
    super(idString);
    this.initAge = Constants.INT_UNSET;
    this.height = Constants.INT_UNSET;
    this.weight = Constants.INT_UNSET;
    this.sex = Sex.UNSET;
    this.race = Race.UNSET;
    this.relationship = CensusRelationship.UNDEFINED;
    this.favoriteLocationMap = new HashMap<>();
  }
  
  /**
   * Create a new Person agent (used if not reading from an input file)
   * 
   * @param age the Person's age in years
   * @param sex the Person's sex
   * @param race the Person's race
   * 
   * @throws IdException if the id is invalid
   */
  public Person(int age, Sex sex, Race race) throws IdException
  {
    super(Id.create(Person.ID_PREPEND).getIdString());
    this.initAge = age;
    this.birthdate = Utils.getRandomBirthdate(this.initAge);
    this.height = Constants.INT_UNSET;
    this.weight = Constants.INT_UNSET;
    this.sex = sex;
    this.race = race;
    this.relationship = CensusRelationship.UNDEFINED;
    this.favoriteLocationMap = new HashMap<>();
  }
  
  /**
   * Create a new Person agent (used if reading from an input file that has agent id already)
   * 
   * @param idString the Person's idString
   * @param age the Person's age in years
   * @param sex the Person's sex
   * @param race the Person's race
   * 
   * @throws IdException if the idString is invalid
   */
  public Person(String idString, int age, Sex sex, Race race) throws IdException
  {
    super(idString);
    this.initAge = age;
    this.birthdate = Utils.getRandomBirthdate(this.initAge);
    this.height = Constants.INT_UNSET;
    this.weight = Constants.INT_UNSET;
    this.sex = sex;
    this.race = race;
    this.relationship = CensusRelationship.UNDEFINED;
    this.favoriteLocationMap = new HashMap<>();
  }
  
  /**
   * @return the height
   */
  public double getHeight() 
  {
    return this.height;
  }
  
  /**
   * @param height the height to set
   */
  public void setHeight(double height) 
  {
    this.height = (height <= 0.0 ? Constants.DBL_UNSET : height);
  }
  
  /**
   * @return the weight
   */
  public double getWeight() 
  {
    return this.weight;
  }
  
  /**
   * @param weight the weight to set
   */
  public void setWeight(double weight) 
  {
    this.weight = weight <= 0.0 ? Constants.DBL_UNSET : weight;
  }
  
  /**
   * @return the sex
   */
  public Sex getSex()
  {
    return this.sex;
  }
  
  /**
   * @param sex the sex to set
   */
  public void setSex(Sex sex)
  {
    this.sex = sex;
  }
  
  /**
   * @return the race
   */
  public Race getRace()
  {
    return this.race;
  }
  
  /**
   * @param race the race to set
   */
  public void setRace(Race race)
  {
    this.race = race;
  }
  
  /**
   * @return the relationship
   */
  public CensusRelationship getRelationship()
  {
    return this.relationship;
  }
  
  /**
   * @param relationship the relationship to set
   */
  public void setRelationship(CensusRelationship relationship)
  {
    this.relationship = relationship;
  }
  
  /**
   * Adds a locationId to the agent's internal favorite places list.
   * Note:
   *  1) an agent can only have one Id for any particular LocationType, so assigning 
   *     to the an already set LocationType will replace the old one
   *  2) since this is an AGENT'S internal list, there is no verification that the ID 
   *     is of a location that is accessible (e.g. an agent can have a favorite place 
   *     that does not exist or is not of the same type as the LocationType would suggest 
   *     ... "my favorite school my household")
   *     
   * @param type the location type to map
   * @param locationId the ID of the location
   */
  public void assignLocationToAgent(LocationTypeEnum type, Id locationId)
  {
    this.favoriteLocationMap.put(type, locationId);
  }
  
  /**
   * @param initAge the initAge to set
   */
  public void setInitAge(int initAge) 
  {
    this.initAge = initAge;
  }
  
  /**
   * @return the initAge
   */
  public int getInitAge() 
  {
    return this.initAge;
  }
  
  /**
   * 
   * @param isNewBorn is this Person a new baby or not
   * @param simDate the current date of the simulation
   */
  public void setBirthdateFromInitAge(boolean isNewBorn, LocalDate simDate)
  {
    if(isNewBorn)
    {
      if(this.initAge != 0)
      {
        Person.LOGGER.fatal("Tried to create a Newborn agent with initAge greater than 0");
      }
      else
      {
        int month = simDate.getMonth().getValue();
        int dayOfMonth = simDate.getDayOfMonth();
        int year = simDate.getYear();
        this.birthdate = LocalDate.of(year, month, dayOfMonth);
      }
    }
    else
    {
      this.birthdate = Utils.getRandomBirthdate(this.initAge, simDate);
    }
  }
  
  /**
   * @return the birth date
   */
  public LocalDate getBirthdate() 
  {
    return this.birthdate;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.agent.GenericAgent#getAgentType()
   */
  @Override
  public AgentTypeEnum getAgentType()
  {
    return AgentTypeEnum.PERSON;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return new ToStringBuilder(this)
        .append("id", this.getId())
        .append("initAge", this.initAge)
        .append("height", String.format("%.1f", this.height))
        .append("weight", String.format("%.1f", this.weight))
        .append("birthdate", this.birthdate)
        .append("sex", this.sex)
        .append("race", this.race)
        .append("relationship", this.relationship)
        .toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() throws CloneNotSupportedException
  {
    super.clone();
    try
    {
      Person p = new Person(this.getId().getIdString());
      p.initAge = this.initAge;
      p.birthdate = this.birthdate;
      p.height = this.height;
      p.weight = this.weight;
      p.sex = this.sex;
      p.race = this.race;
      p.relationship = this.relationship;
      return p;
    }
    catch(IdException e)
    {
      // Fail fast because should not happen (i.e. the original Person would have failed construction)
      Person.LOGGER.error(ErrorCode.ERR_PARAMS.message(), e);
      System.exit(1);
    }
    return null;
  }
  
  /**
   * Returns the string representation of this object, but in JSON format
   * 
   * @return the object's string representation in JSON format
   */
  public String toJsonString()
  {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", this.getId().toJsonString())
        .append("initAge", this.initAge)
        .append("height", String.format("%.1f", this.height))
        .append("weight", String.format("%.1f", this.weight))
        .append("birthdate", this.birthdate)
        .append("sex", this.sex)
        .append("race", this.race)
        .append("relationship", this.relationship)
        .toString();
  }

}
