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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class LocationFactory {
  
  static final Logger LOGGER = LogManager.getLogger(LocationFactory.class.getName());
  
  /**
   * 
   * @param type
   * @param id
   * @param latitude
   * @param longitude
   * @param elevation
   * @return a new Location of the specified LocationType
   */
  public static GenericLocation createLocation(LocationTypeEnum type, String id, double latitude, double longitude, double elevation)
  {
    GenericLocation retVal = null;
    try
    {
      
      switch(type)
      {
        case CLASSROOM:
          break;
        case HOUSEHOLD:
          retVal = new Household(id, latitude, longitude, elevation);
          break;
        case OFFICE:
          break;
        case SCHOOL:
          retVal = new School(id, latitude, longitude, elevation);
          break;
        case WORKPLACE:
          retVal = new Workplace(id, latitude, longitude, elevation);
          break;
        case GRID:
          break;
        case COLLEGE_DORM:
          break;
        case COLLEGE_DORM_ROOM:
          break;
        case MILITARY_BARRACKS:
          break;
        case NURSING_FACILITY:
          break;
        case PRISON:
          break;
        case PRISON_CELL:
          break;
        case NULL_TYPE:
          retVal = GenericLocation.NULL_LOCATION;
          break;
        default:
          break;
      }
    }
    catch(IdException e)
    {
      LocationFactory.LOGGER.fatal(e);
      System.exit(2);
    }
    
    return retVal;
  }
  
  /**
   * 
   * @param type
   * @param id
   * @param latitude
   * @param longitude
   * @return a new Location of the specified LocationType
   */
  public static GenericLocation createLocation(LocationTypeEnum type, String id, double latitude, double longitude)
  {
    GenericLocation retVal = null;
    try
    {
      switch(type)
      {
        case CLASSROOM:
          break;
        case HOUSEHOLD:
          retVal = new Household(id, latitude, longitude);
          break;
        case OFFICE:
          break;
        case SCHOOL:
          retVal = new School(id, latitude, longitude);
          break;
        case WORKPLACE:
          retVal = new Workplace(id, latitude, longitude);
          break;
        case GRID:
          break;
        case COLLEGE_DORM:
          break;
        case COLLEGE_DORM_ROOM:
          break;
        case MILITARY_BARRACKS:
          break;
        case NURSING_FACILITY:
          break;
        case PRISON:
          break;
        case PRISON_CELL:
          break;
        case NULL_TYPE:
          retVal = GenericLocation.NULL_LOCATION;
          break;
        default:
          break;
      }
    }
    catch(IdException e)
    {
      LocationFactory.LOGGER.fatal(e);
      System.exit(2);
    }
    
    return retVal;
  }
  
  /**
   * @param type
   * @param id
   * @param latitude
   * @param longitude
   * @param mixingContainer
   * @return a new Location of the specified LocationType
   */
  public static GenericLocation createLocation(LocationTypeEnum type, String id, double latitude, double longitude, GenericIdMixingContainer mixingContainer)
  {
    GenericLocation retVal = null;
    try
    {
      switch(type)
      {
        case CLASSROOM:
          break;
        case HOUSEHOLD:
          retVal = new Household(id, latitude, longitude, mixingContainer);
          break;
        case OFFICE:
          break;
        case SCHOOL:
          retVal = new School(id, latitude, longitude, mixingContainer);
          break;
        case WORKPLACE:
          retVal = new Workplace(id, latitude, longitude, mixingContainer);
          break;
        case GRID:
          break;
        case COLLEGE_DORM:
          break;
        case COLLEGE_DORM_ROOM:
          break;
        case MILITARY_BARRACKS:
          break;
        case NURSING_FACILITY:
          break;
        case PRISON:
          break;
        case PRISON_CELL:
          break;
        case NULL_TYPE:
          retVal = GenericLocation.NULL_LOCATION;
          break;
        default:
          break;
      }
    }
    catch(IdException e)
    {
      LocationFactory.LOGGER.fatal(e);
      System.exit(2);
    }
    return retVal;
  }
  
  /**
   * 
   * @param type
   * @param id
   * @param latitude
   * @param longitude
   * @param elevation
   * @param mixingContainer
   * @return a new Location of the specified LocationType
   */
  public static GenericLocation createLocation(LocationTypeEnum type, String id, double latitude, double longitude, double elevation, GenericIdMixingContainer mixingContainer)
  {
    GenericLocation retVal = null;
    try
    {
      switch(type)
      {
        case CLASSROOM:
          break;
        case HOUSEHOLD:
          retVal = new Household(id, latitude, longitude, elevation, mixingContainer);
          break;
        case OFFICE:
          break;
        case SCHOOL:
          retVal = new School(id, latitude, longitude, elevation, mixingContainer);
          break;
        case WORKPLACE:
          retVal = new Workplace(id, latitude, longitude, elevation, mixingContainer);
          break;
        case GRID:
          break;
        case COLLEGE_DORM:
          break;
        case COLLEGE_DORM_ROOM:
          break;
        case MILITARY_BARRACKS:
          break;
        case NURSING_FACILITY:
          break;
        case PRISON:
          break;
        case PRISON_CELL:
          break;
        case NULL_TYPE:
          retVal = GenericLocation.NULL_LOCATION;
          break;
        default:
          break;
      }
    }
    catch(IdException e)
    {
      LocationFactory.LOGGER.fatal(e);
      System.exit(2);
    }
    return retVal;
  }
  
  /**
   * 
   * @param type
   * @param id
   * @param containingLocation
   * @return a new Location of the specified LocationType
   */
  public static GenericLocation createLocation(LocationTypeEnum type, String id, GenericLocation containingLocation)
  {
    GenericLocation retVal = null;
    
    switch(type)
    {
      case CLASSROOM:
        break;
      case HOUSEHOLD:
        break;
      case OFFICE:
        break;
      case SCHOOL:
        break;
      case WORKPLACE:
        break;
      case GRID:
        break;
      case COLLEGE_DORM:
        break;
      case COLLEGE_DORM_ROOM:
        break;
      case MILITARY_BARRACKS:
        break;
      case NURSING_FACILITY:
        break;
      case PRISON:
        break;
      case PRISON_CELL:
        break;
      case NULL_TYPE:
        retVal = GenericLocation.NULL_LOCATION;
        break;
      default:
        break;
    }
    
    return retVal;
  }
  
}
