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
public class WorkplaceInitInfo extends InitializationInfo {
  private int workerCount;
  private double latitude;
  private double longitude;
  private double elevation;
  
  /**
   * Default Constructor
   */
  public WorkplaceInitInfo() 
  {
    super();
  }
  
  /**
   * @return the workerCount
   */
  public int getWorkerCount()
  {
    return this.workerCount;
  }
  
  /**
   * @param workerCount the workerCount to set
   */
  public void setWorkerCount(int workerCount)
  {
    this.workerCount = workerCount;
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
    if(latitude == 0.0)
    {
      this.latitude = -9999.0;
    }
    else
    {
      this.latitude = latitude;
    }
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
    if(longitude == 0.0)
    {
      this.longitude = -9999.0;
    }
    else
    {
      this.longitude = longitude;
    }
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
    if(this.latitude == -9999.0 && this.longitude == -9999.0 && elevation == 0.0)
    {
      this.elevation = -9999.0;
    }
    else
    {
      this.elevation = elevation;
    }
  }
  
  @Override
  public String toString() 
  {
    return new ToStringBuilder(this)
      .append("id", this.getId())
      .append("workerCount", String.valueOf(this.workerCount))
      .append("latitude", String.valueOf(this.latitude))
      .append("longitude", String.valueOf(this.longitude))
      .append("elevation", String.valueOf(this.elevation))
      .toString();
  }    
}