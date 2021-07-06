package edu.pitt.gallowdd.persephone.location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * A group of LocationManagers and locations. The LocationListManager will keep a map of all locationIds to Locations. 
 * It will also add locations to LocationManagers as they are added to the list.
 * 
 * Note: There will be one LocationListManager per LocationType in a given Controller
 * 
 * @author David Galloway
 */
public class LocationListManager {
  
  private static final Logger LOGGER = LogManager.getLogger(LocationListManager.class.getName());
  
  private final Id id;
  private final EventBus eventBus;
  private final Map<Id, GenericLocation> locationIdMap = new HashMap<>();
  private final Set<LocationManager> locationMgrSet = new HashSet<>();
  private boolean isInitialized;
  
  /**
   * 
   * @throws IdException if id String is invalid
   */
  public LocationListManager() throws IdException
  {
    this.id = Id.create("LSTMGR");
    this.eventBus = new EventBus(this.id.getIdString());
    this.isInitialized = false;
  }
  
  /**
   * Determines whether or not this LocationListManager contains the locationId specified
   * 
   * @param locationId the locationId to search for
   * @return {@code true} if the internal map contains the location ID, {@code false} otherwise
   */
  public boolean contains(Id locationId)
  {
    return this.locationIdMap.containsKey(locationId);
  }
  
  /**
   * Determines whether or not this LocationListManager contains the locationId specified
   * by the locationIdString
   * 
   * @param locationIdString the String representation of the locationId
   * @return {@code true} if the internal map contains the location ID, {@code false} otherwise
   */
  public boolean contains(String locationIdString)
  {
    try
    {
      return this.locationIdMap.containsKey(new Id(locationIdString));
    }
    catch(IdException e)
    {
      // The locationIdString is invalid, so it can't be mapped to anything
      return false;
    }
  }
  
  /**
   * 
   * @param locationId
   * @return the location stored in this controller or null if not found
   */
  public GenericLocation getLocation(Id locationId)
  {
    return this.locationIdMap.get(locationId);
  }
  
  /**
   * 
   * @param locationIdString
   * @return the location stored in this controller or null if not found
   */
  public GenericLocation getLocation(String locationIdString)
  {
    try
    {
      return this.locationIdMap.get(new Id(locationIdString));
    }
    catch(IdException e)
    {
      // The locationIdString is invalid, so it can't be mapped to anything
      return null;
    }
  }
  
  /**
   * Maps location's Id to the location itself in this table.
   * 
   * @param location the location to add
   * @return the previous location associated with key, or @code{null} if there was no mapping for key
   */
  public GenericLocation addLocation(GenericLocation location)
  {
    boolean isAdded = false;
    
    for (LocationManager locationMgr : this.locationMgrSet)
    {
      try
      {
        locationMgr.addLocation(location);
        isAdded = true;
        break;
      }
      catch (LocationManagerSizeExceededException e)
      {
        // If this LocationListManager has already done the bulk registration of its
        // listeners, then make sure that
        // the locationManager has performed ITS bulk registration
        if (this.isInitialized)
        {
          if (locationMgr.registerListeners())
          {
            this.eventBus.register(locationMgr);
          }
        }
        
        // Try next one
      }
      catch (LocationManagerTypeMatchException e)
      {
        LocationListManager.LOGGER.fatal(e);
        throw new RuntimeException(e);
      }
    }

    // If all of the LocationManagers are full, create a new one and add it
    if (!isAdded)
    {

      LocationManager newLocMgr;
      try
      {
        newLocMgr = new LocationManager(location.getLocationType());
        newLocMgr.addLocation(location);
      }
      catch (LocationManagerSizeExceededException | LocationManagerTypeMatchException | IdException e)
      {
        LocationListManager.LOGGER.fatal(e);
        throw new RuntimeException(e);
      }
      this.locationMgrSet.add(newLocMgr);
    }

    return this.locationIdMap.put(location.getId(), location);
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param locationId the id of the location to remove
   * @return the removed location or @code{null} if not found
   */
  public GenericLocation removeLocation(Id locationId)
  {
    return this.locationIdMap.remove(locationId);
  }
  
  /**
   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
   * 
   * @param locationIdString the idString of the location to remove
   * @return the removed location or @code{null} if not found
   */
  public GenericLocation removeLocation(String locationIdString)
  {
    try
    {
      return this.locationIdMap.remove(new Id(locationIdString));
    }
    catch(IdException e)
    {
      // The locationIdString is invalid, so it can't be mapped to anything
      return null;
    }
  }
  
  /**
   * It is significantly faster to add all of the listeners to a list first, then use a parallelStream to bulk register.
   * This method also sets the isInitialized flag so additional listeners will be added dynamically.
   * 
   * This method should be called after all of the initial listeners are added
   * 
   * @return true if the bulk register is successful, false otherwise (this LocationManager has already performed a bulk registration)
   */
  public boolean registerListeners()
  {
    if(!this.isInitialized)
    {
      this.locationMgrSet.parallelStream().forEach(tmpListener ->
      {
        tmpListener.registerListeners();
        this.eventBus.register(tmpListener);
      });
      this.isInitialized = true;
      return true;
    }
    
    return false;
  }
}
