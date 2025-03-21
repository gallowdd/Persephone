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

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.event.LocationEvent;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import io.reactivex.rxjava3.subjects.PublishSubject;

/**
 * A LocationManager holds an ArrayList of at most {@code Constants.MANAGER_MAX_SUBSCRIBER_COUNT} and sends events out to them as necessary.
 * The LocationManager manages a particular type ({@code LocationTypeEnum}) of locations. If a user attempts to add a location not of the type managed, 
 * an exception will be raised.
 * 
 * @author David Galloway
 *
 */
public class LocationManager {
  
  private static final Logger LOGGER = LogManager.getLogger(LocationManager.class.getName());
  
  private final Id id;
  private final PublishSubject<LocationEvent> locationEventPublishSubject = PublishSubject.create();
  private final LocationTypeEnum locationTypeManaged;
  private final List<GenericLocation> locationArrLst = new ArrayList<>(Constants.MANAGER_MAX_SUBSCRIBER_COUNT);
  private boolean isInitialized;
  
  /**
   * 
   * @param locationTypeManaged
   * @throws IdException if id String is invalid
   */
  public LocationManager(LocationTypeEnum locationTypeManaged) throws IdException
  {
    this.id = Id.create(locationTypeManaged.toString() + "MGR");
    this.locationTypeManaged = locationTypeManaged;
    this.isInitialized = false;
    
  }
  
  /**
   * Add a location to this LocationManager. If the location's type is not the same as the type being managed, this will throw a
   * LocationManagerTypeMatchException. 
   * If this LocationManager doesn't have space to add another location, it will throw a LocationManagerSizeExceededException
   * 
   * @param location the GenericLocation to add to this Location Manager
   * @return {@code true} if the location is added correctly to this Location Manager's internal list (as specified by {@code Collection.add})
   * @throws LocationManagerSizeExceededException if locationArrLst is already at {@code size() == Constants.MANAGER_MAX_SUBSCRIBER_COUNT}
   * @throws LocationManagerTypeMatchException the locationType of the location does not match the locationTypeManaged of this LocationManager
   */
  public boolean addLocation(GenericLocation location) throws LocationManagerSizeExceededException, LocationManagerTypeMatchException
  {
    if(location.getLocationType() == this.locationTypeManaged)
    {
      if(this.locationArrLst.size() < Constants.MANAGER_MAX_SUBSCRIBER_COUNT)
      {
        boolean isAdded =  this.locationArrLst.add(location);
        
        // If we have already done the bulk registration of observers, then from now on we must register new ones as they are added
        if(this.isInitialized)
        {
          this.locationEventPublishSubject.subscribe(location);
        }
        return isAdded;
      }
      else
      {
        throw new LocationManagerSizeExceededException(this.id.getIdString());
      }
    }
    
    throw new LocationManagerTypeMatchException(this.id.getIdString(), this.locationTypeManaged.toString());
  }
  
  /**
   * Remove a GenericLocation from this LocationManager
   * 
   * @param location the location to remove
   * @return {@code true} if the location is removed from this LocationManager, {@code false} otherwise
   */
  public boolean removeLocation(GenericLocation location)
  {
    if(this.locationArrLst.contains(location))
    {
      return this.locationArrLst.remove(location);
    }
    
    return false;
  }
  
  /**
   * Remove a GenericLocation from this LocationManager
   * 
   * @param locationId the id of the location to remove
   * @return {@code true} if the location is removed from this LocationManager, {@code false} otherwise
   */
  public boolean removeLocation(Id locationId)
  {
    GenericLocation locationToRemove = null;
    for(int i = 0; i < this.locationArrLst.size(); ++i)
    {
      if(this.locationArrLst.get(i).getId().equals(locationId))
      {
        locationToRemove = this.locationArrLst.get(i);
        break;
      }
    }
    
    if(locationToRemove != null)
    {
      return this.locationArrLst.remove(locationToRemove);
    }
    
    return false;
  }
  
  /**
   * @param locEventPublisher
   */
   public void observe(PublishSubject<LocationEvent> locEventPublisher)
   {
     locEventPublisher.subscribe(this.locationEventPublishSubject);
   }
   
  /**
   * It is significantly faster to add all of the Locations to a list first, then use a parallelStream to have each Location's
   * observer OBSERVE this LocationManager's PublishSubject.
   * This method also sets the isInitialized flag so additional locations will be added dynamically.
   * 
   * This method should be called after all of the initial locations are added
   * 
   * @return true if the bulk register is successful, false otherwise (this LocationManager has already performed a bulk registration)
   */
  public boolean registerObservers()
  {
    if(!this.isInitialized)
    {
      this.locationArrLst.parallelStream().forEach(tmpObserver -> {
        this.locationEventPublishSubject.subscribe(tmpObserver);
      });
      this.isInitialized = true;
      return true;
    }
    
    return false;
  }
}
