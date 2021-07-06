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

package edu.pitt.gallowdd.persephone.location.physical;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class Grid extends GenericLocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.GRID.toString();
  private final double minLatitude;
  private final double maxLatitude;
  private final double minLongitude;
  private final double maxLongitude;
  private final double minElevation;
  private final double maxElevation;
  private final double patchSizeKilometer;
  
  /**
   * @param minLatitude
   * @param maxLatitude
   * @param minLongitude
   * @param maxLongitude
   * @param minElevation
   * @param maxElevation
   * @param patchSizeKilometer
   * @throws PhysicalLocationException 
   * @throws IdException 
   */
  public Grid(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude, double minElevation, double maxElevation,
      double patchSizeKilometer) throws PhysicalLocationException, IdException
  {
    super(Id.create(Grid.ID_PREPEND).getIdString(), (minLatitude + maxLatitude) / 2.0, (minLongitude + maxLongitude) / 2.0, (minElevation + maxElevation) / 2.0);
    
    if(minLatitude < -90.0 || minLatitude >= maxLatitude || minLongitude < -90.0 || minLongitude >= maxLongitude || patchSizeKilometer <= 0.0)
    {
      StringBuffer sb1 = new StringBuffer("");
      sb1.append("minLatitude = " + minLatitude + "' ");
      sb1.append("maxLatitude = " + maxLatitude + "' ");
      sb1.append("minLongitude = " + minLongitude + "' ");
      sb1.append("maxLongitude = " + maxLongitude + ", ");
      sb1.append("patchSizeKilometer = " + patchSizeKilometer);
      throw new PhysicalLocationException(sb1.toString());
    }
    
    if(minElevation > maxElevation)
    {
      StringBuffer sb1 = new StringBuffer("");
      sb1.append("minElevation = " + minElevation + "' ");
      sb1.append("maxElevation = " + maxElevation);
      throw new PhysicalLocationException(sb1.toString());
    }
    
    this.minLatitude = minLatitude;
    this.maxLatitude = maxLatitude;
    this.minLongitude = minLongitude;
    this.maxLongitude = maxLongitude;
    this.minElevation = minElevation;
    this.maxElevation = maxElevation;
    this.patchSizeKilometer = patchSizeKilometer;
    
    this.setMixingContainer(new GridPatchWeightedIdContainer(minLatitude, maxLatitude, minLongitude, maxLongitude, patchSizeKilometer, 0.75));
  }
  
  /**
   * @return the minLatitude
   */
  public double getMinLatitude()
  {
    return this.minLatitude;
  }
  
  /**
   * @return the maxLatitude
   */
  public double getMaxLatitude()
  {
    return this.maxLatitude;
  }
  
  /**
   * @return the minLongitude
   */
  public double getMinLongitude()
  {
    return this.minLongitude;
  }
  
  /**
   * @return the maxLongitude
   */
  public double getMaxLongitude()
  {
    return this.maxLongitude;
  }
  
  /**
   * @return the minElevation
   */
  public double getMinElevation()
  {
    return this.minElevation;
  }
  
  /**
   * @return the maxElevation
   */
  public double getMaxElevation()
  {
    return this.maxElevation;
  }
  
  /**
   * @return the patchSizeKilometer
   */
  public double getPatchSizeKilometer()
  {
    return this.patchSizeKilometer;
  }
  
  @Override
  public LocationTypeEnum getLocationType()
  {
    return LocationTypeEnum.GRID;
  }
  
  /* (non-Javadoc)
   * @see com.gallowdd.persephone.location.GenericLocation#toJSONString()
   */
  @Override
  public String toJsonString()
  {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", this.getId().toJsonString())
        .append("minLatitude", this.minLatitude)
        .append("maxLatitude", this.maxLatitude)
        .append("latitude", this.latitude)
        .append("minLongitude", this.minLongitude)
        .append("maxLongitude", this.maxLongitude)
        .append("minElevation", this.minElevation)
        .append("maxElevation", this.maxElevation)
        .append("latitude", this.latitude)
        .append("longitude", this.longitude)
        .append("elvation", this.elevation)
        .append("patchSizeKilometer", this.patchSizeKilometer)
        .toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return new ToStringBuilder(this, ToStringStyle.DEFAULT_STYLE)
        .append("id", this.getId())
        .append("minLatitude", this.minLatitude)
        .append("maxLatitude", this.maxLatitude)
        .append("latitude", this.latitude)
        .append("minLongitude", this.minLongitude)
        .append("maxLongitude", this.maxLongitude)
        .append("minElevation", this.minElevation)
        .append("maxElevation", this.maxElevation)
        .append("latitude", this.latitude)
        .append("longitude", this.longitude)
        .append("elevation", this.elevation)
        .append("patchSizeKilometer", this.patchSizeKilometer)
        .toString();
  }

}
