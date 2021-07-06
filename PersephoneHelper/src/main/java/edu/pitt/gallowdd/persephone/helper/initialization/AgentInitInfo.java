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
package edu.pitt.gallowdd.persephone.helper.initialization;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author David Galloway
 *
 */
public class AgentInitInfo extends InitializationInfo {
  
  private String householdId;
  private int age;
  private String sexCode;
  private int raceCode;
  private int hhRelationshipCode;
  private String schoolId;
  private String workplaceId;
  
  /**
   * Default Constructor
   */
  public AgentInitInfo() 
  {
    super();
  }

  /**
   * @return the householdId
   */
  public String getHouseholdId()
  {
    return this.householdId;
  }

  /**
   * @param householdId the householdId to set
   */
  public void setHouseholdId(String householdId)
  {
    this.householdId = householdId;
  }

  /**
   * @return the age
   */
  public int getAge()
  {
    return this.age;
  }

  /**
   * @param age the age to set
   */
  public void setAge(int age)
  {
    this.age = age;
  }

  /**
   * @return the sexCode
   */
  public String getSexCode()
  {
    return this.sexCode;
  }

  /**
   * @param sexCode the sexCode to set
   */
  public void setSexCode(String sexCode)
  {
    if(sexCode.trim().equals("M") || sexCode.trim().equals("F"))
    {
      this.sexCode = sexCode.trim();
    }
    else
    {
      System.out.println("Sex code [" + sexCode.trim() + "] not recognized ... abort");
      System.exit(1);
    }
    
  }

  /**
   * @return the raceCode
   */
  public int getRaceCode()
  {
    return this.raceCode;
  }

  /**
   * @param raceCode the raceCode to set
   */
  public void setRaceCode(int raceCode)
  {
    this.raceCode = raceCode;
  }
  
  /**
   * @return the hhRelationshipCode
   */
  public int getHhRelationshipCode()
  {
    return hhRelationshipCode;
  }
  
  /**
   * @param hhRelationshipCode the hhRelationshipCode to set
   */
  public void setHhRelationshipCode(int hhRelationshipCode)
  {
    this.hhRelationshipCode = hhRelationshipCode;
  }
  
  /**
   * @return the schoolId
   */
  public String getSchoolId()
  {
    return this.schoolId;
  }

  /**
   * @param schoolId the schoolId to set
   */
  public void setSchoolId(String schoolId)
  {
    this.schoolId = schoolId;
  }

  /**
   * @return the workplaceId
   */
  public String getWorkplaceId()
  {
    return this.workplaceId;
  }

  /**
   * @param workplaceId the workplaceId to set
   */
  public void setWorkplaceId(String workplaceId)
  {
    this.workplaceId = workplaceId;
  }

//  @Override
//  public String toJSONString()
//  {
//    String json = Constants.GSON.toJson(this);
//    return json;
//  }

  @Override
  public String toString() 
  {
    return new ToStringBuilder(this).
      append("id", this.getId()).
      append("householdId", this.householdId).
      append("age", String.valueOf(this.age)).
      append("sexCode", String.valueOf(this.sexCode)).
      append("raceCode", String.valueOf(this.raceCode)).
      append("hhRelationshipCode", String.valueOf(this.hhRelationshipCode)).
      append("schoolId", this.schoolId).
      append("workplaceId", this.workplaceId).
      toString();
  }


}