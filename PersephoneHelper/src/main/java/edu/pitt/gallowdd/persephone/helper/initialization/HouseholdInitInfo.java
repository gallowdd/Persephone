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
public class HouseholdInitInfo extends InitializationInfo {

  private int stateCode;
  private int countyCode;
  private int censusTract;
  private double income;
  private double latitude;
  private double longitude;
  private double elevation;
  
  /**
   * Default Constructor
   */
  public HouseholdInitInfo() 
  {
    super();
  }    
  
  /**
   * @return the stateCode
   */
  public int getStateCode()
  {
    return this.stateCode;
  }

  /**
   * @param stateCode the stateCode to set
   */
  public void setStateCode(int stateCode)
  {
    this.stateCode = stateCode;
  }

  /**
   * @return the countyCode
   */
  public int getCountyCode()
  {
    return this.countyCode;
  }

  /**
   * @param countyCode the countyCode to set
   */
  public void setCountyCode(int countyCode)
  {
    this.countyCode = countyCode;
  }

  /**
   * @return the censusTract
   */
  public int getCensusTract()
  {
    return this.censusTract;
  }

  /**
   * @param censusTract the censusTract to set
   */
  public void setCensusTract(int censusTract)
  {
    this.censusTract = censusTract;
  }

  /**
   * @return the income
   */
  public double getIncome()
  {
    return this.income;
  }

  /**
   * @param income the income to set
   */
  public void setIncome(double income)
  {
    this.income = income;
  }

  /**
   * @return the latitude
   */
  public double getLatitude()
  {
    return this.latitude;
  }

  /**
   * @param latitude the latitude to set
   */
  public void setLatitude(double latitude)
  {
    this.latitude = latitude;
  }

  /**
   * @return the longitude
   */
  public double getLongitude()
  {
    return this.longitude;
  }

  /**
   * @param longitude the longitude to set
   */
  public void setLongitude(double longitude)
  {
    this.longitude = longitude;
  }
  
  /**
   * @return the elevation
   */
  public double getElevation()
  {
    return this.elevation;
  }
  
  /**
   * @param elevation the elevation to set
   */
  public void setElevation(double elevation)
  {
    this.elevation = elevation;
  }
  
  @Override
  public String toString() 
  {
    return new ToStringBuilder(this)
      .append("id", this.getId())
      .append("stateCode", String.valueOf(this.stateCode))
      .append("countyCode", String.valueOf(this.countyCode))
      .append("censusTract", String.valueOf(this.censusTract))
      .append("income", String.valueOf(this.income))
      .append("latitude", String.valueOf(this.latitude))
      .append("longitude", String.valueOf(this.longitude))
      .append("elevation", String.valueOf(this.elevation))
      .toString();
  }    
}