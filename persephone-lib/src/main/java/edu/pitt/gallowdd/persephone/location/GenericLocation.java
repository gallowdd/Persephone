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

package edu.pitt.gallowdd.persephone.location;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.container.FullyConnectedIdMixingContainer;
import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;
import edu.pitt.gallowdd.persephone.event.LocationEvent;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * The Base Class for all Locations in the simulation.
 * 
 * @author David Galloway
 **/
public abstract class GenericLocation implements Observer<LocationEvent> {
  
  private static final Logger LOGGER = LogManager.getLogger(GenericLocation.class.getName());
  
  /**
   * The Null Location that will be used for all cases where a Null is needed
   */
  public static final GenericLocation NULL_LOCATION = new GenericLocation(Id.NULL_ID, Constants.DBL_NULL_LAT_LON_ELEV, Constants.DBL_NULL_LAT_LON_ELEV) {
    @Override
    public LocationTypeEnum getLocationType()
    {
      return LocationTypeEnum.NULL_TYPE;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d)
    {
      GenericLocation.LOGGER.debug("NULL_LOCATION can't observe a Location Event Publisher, hence there is no onSubscribe");
    }
    
    @Override
    public void onNext(@NonNull LocationEvent t)
    {
      GenericLocation.LOGGER.debug("NULL_LOCATION can't observe a Location Event Publisher, hence there is no onNext");
    }

    @Override
    public void onComplete()
    {
      GenericLocation.LOGGER.debug("NULL_LOCATION can't observe a Location Event Publisher, hence there is no onComplete");
    }

    @Override
    public void onError(@NonNull Throwable e)
    {
      GenericLocation.LOGGER.debug("NULL_LOCATION can't observe a Location Event Publisher, hence there is no onError");
    }
  };
  
  private final Id id;
  private GenericIdMixingContainer mixingContainer;
  protected double latitude;
  protected double longitude;
  protected double elevation;

  /**
   * The base class of any location that does not move.
   * 
   * @param id The id of the location
   * @param locationType the type of location 
   * @param latitude The location's latitude
   * @param longitude The location's longitude
   */
  protected GenericLocation(Id id, double latitude, double longitude)
  {
    super();
    this.id = id;
    this.latitude = latitude;
    this.longitude =longitude;
    this.elevation = Constants.DBL_NULL_LAT_LON_ELEV;
    this.mixingContainer = new FullyConnectedIdMixingContainer();
  }
  
  /**
   * The base class of any location that does not move.
   * 
   * @param idString The idString of the location
   * @param latitude The location's latitude
   * @param longitude The location's longitude
   * @throws IdException if the id is not valid
   */
  protected GenericLocation(String idString, double latitude, double longitude) throws IdException
  {
    super();
    this.id = new Id(idString);
    this.latitude = latitude;
    this.longitude =longitude;
    this.elevation = Constants.DBL_NULL_LAT_LON_ELEV;
    this.mixingContainer = new FullyConnectedIdMixingContainer();
  }
  
  /**
   * The base class of any location that does not move.
   * 
   * @param idString the idString of the location
   * @param latitude The location's latitude
   * @param longitude The location's longitude
   * @param elevation The location's elevation
   * @throws IdException if the id is not valid
   */
  protected GenericLocation(String idString, double latitude, double longitude, double elevation) throws IdException
  {
    super();
    this.id = new Id(idString);
    this.latitude = latitude;
    this.longitude =longitude;
    this.elevation = elevation;
    this.mixingContainer = new FullyConnectedIdMixingContainer();
  }
  
  /**
   * @param idString the idString of the location
   * @param latitude The location's latitude
   * @param longitude The location's longitude
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is not valid
   */
  protected GenericLocation(String idString, double latitude, double longitude, GenericIdMixingContainer mixingContainer) throws IdException 
  {
    this.id = new Id(idString);
    this.latitude = latitude;
    this.longitude = longitude;
    this.elevation = Constants.DBL_NULL_LAT_LON_ELEV;
    this.mixingContainer = mixingContainer;
  }
  
  /**
   * @param idString the idString of the location
   * @param latitude The location's latitude
   * @param longitude The location's longitude
   * @param elevation The location's elevation
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is not valid
   */
  protected GenericLocation(String idString, double latitude, double longitude, double elevation, GenericIdMixingContainer mixingContainer) throws IdException 
  {
    this.id = new Id(idString);
    this.latitude = latitude;
    this.longitude = longitude;
    this.elevation = elevation;
    this.mixingContainer = mixingContainer;
  }
  
  /**
   * @return this location's id
   */
  public Id getId() 
  {
    return this.id;
  }
  
  /**
   * @return the latitude
   */
  public double getLatitude() 
  {
    return this.latitude;
  }
  
  /**
   * @return the mixingContainer
   */
  public GenericIdMixingContainer getMixingContainer()
  {
    return this.mixingContainer;
  }
  
  /**
   * Set the mixingContainer
   * 
   * @param mixingContainer the mixingContainer to set
   */
  public void setMixingContainer(GenericIdMixingContainer mixingContainer)
  {
    this.mixingContainer = mixingContainer;
  }
  
  /**
   * @return the longitude
   */
  public double getLongitude() 
  {
    return this.longitude;
  }
  
  /**
   * @return the elevation
   */
  public double getElevation()
  {
    return this.elevation;
  }
  
  /**
   * @return the LocationType enum of this Location
   */
  abstract public LocationTypeEnum getLocationType();
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if(this == obj)
    {
      return true;
    }
    
    if(obj == null)
    {
      return false;
    }
    
    if(getClass() != obj.getClass())
    {
      return false;
    }
    
    GenericLocation rhs = (GenericLocation)obj;
    EqualsBuilder equalsBuilder = new EqualsBuilder();
    equalsBuilder.append(this.id, rhs.id);
    return equalsBuilder.isEquals();
  }
  
  /**
   * 
   * @return this object as a JSON string
   */
  public String toJsonString()
  {
    
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("id", this.id.toJsonString())
        .append("latitude", this.latitude)
        .append("longitude", this.longitude)
        .append("elevation", this.elevation)
        .toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    
    return new ToStringBuilder(this)
        .append("id", this.id)
        .append("latitude", this.latitude)
        .append("longitude", this.longitude)
        .append("elevation", this.elevation)
        .toString();
  }
}
