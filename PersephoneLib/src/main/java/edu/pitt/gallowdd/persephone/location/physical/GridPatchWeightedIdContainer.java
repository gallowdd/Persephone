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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

import edu.pitt.gallowdd.persephone.container.IdConnectable;
import edu.pitt.gallowdd.persephone.location.LatitudeLongitude;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * 
 * 
 * @author David Galloway
 */
public class GridPatchWeightedIdContainer implements IdConnectable {
  
  private static final Logger LOGGER = LogManager.getLogger(GridPatchWeightedIdContainer.class.getName());
  
  private final RangeMap<Double, RangeMap<Double, Set<Id>>> latLonRangeMap = 
      new ImmutableRangeMap.Builder<Double, RangeMap<Double, Set<Id>>>().build();
  
  private final Map<Id, LatitudeLongitude> idLatLonMap = new HashMap<>();
  
  private final double minLatitude;
  private final double maxLatitude;
  private final double minLongitude;
  private final double maxLongitude;
  private final double patchSizeKilometer;
  private final double inPatchProbability;
  
  /**
   * @param minLatitude the min latitude of this Grid
   * @param maxLatitude the max latitude of this Grid
   * @param minLongitude the min longitude of this Grid
   * @param maxLongitude the max longitude of this Grid
   * @param patchSizeKilometer the size of a geographic patch in kilometers
   * @param inPatchProbability the probability that two connected agents are from the same geographic patch
   * 
   * @throws PhysicalLocationException 
   */
  public GridPatchWeightedIdContainer(double minLatitude, double maxLatitude, double minLongitude, double maxLongitude, double patchSizeKilometer, double inPatchProbability) throws PhysicalLocationException
  {
    super();
    //super((minLatitude + maxLatitude) / 2.0, (minLongitude + maxLongitude) / 2.0, (minAltitude + maxAltitude) / 2.0);
    
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
    
    this.minLatitude = minLatitude;
    this.maxLatitude = maxLatitude;
    this.minLongitude = minLongitude;
    this.maxLongitude = maxLongitude;
    this.patchSizeKilometer = patchSizeKilometer;
    this.inPatchProbability = (inPatchProbability >= 0.0 && inPatchProbability <= 1.0 ? inPatchProbability : 1.0);
    
    double latStep = 1.0;
    double lonStep = 1.0;
    
    // Use the middle latitude as the latitude for the haversine distance
    double midLatitude = (this.minLatitude + this.maxLatitude) / 2.0;
    
    // Need the distance so I can determine how many patches I will create
    double longitudeDistKm = LatitudeLongitude.haversine(midLatitude, this.minLongitude, midLatitude, this.maxLongitude);
    // For Haversine, the distance between latitudes doesn't depend on the longitude, so just use 0.0 for the longitudes
    double latitudeDistKm = LatitudeLongitude.haversine(this.minLatitude, 0.0, this.maxLatitude, 0.0);
    
    int rowCount = (int)Math.ceil(latitudeDistKm / this.patchSizeKilometer);
    int colCount = (int)Math.ceil(longitudeDistKm / this.patchSizeKilometer);
    
    if(rowCount > 1)
    {
      latStep = (this.maxLatitude - this.minLatitude) / (double)rowCount;
    }
    
    if(colCount > 1)
    {
      lonStep = (this.maxLongitude - this.minLongitude) / (double)colCount;
    }
    
    double curLat = this.minLatitude;
    
    for(int row = 0; row < rowCount; ++row)
    {
      final RangeMap<Double, Set<Id>> tmpLonRangeMap = new ImmutableRangeMap.Builder<Double, Set<Id>>().build();
      double curLon = this.minLongitude;
      for(int col = 0; col < colCount; ++col)
      {
        if(col + 1 < colCount)
        {
          tmpLonRangeMap.put(Range.closedOpen(curLon, curLon + lonStep), new HashSet<>());
        }
        else
        {
          // Last column so want to include end point
          tmpLonRangeMap.put(Range.closed(curLon, curLon + lonStep), new HashSet<>());
        }
        
        curLon+= lonStep;
      }
      if(row + 1 < rowCount)
      {
        this.latLonRangeMap.put(Range.closedOpen(curLat, curLat + latStep), tmpLonRangeMap);
      }
      else
      {
        // Last row so want to include end point
        this.latLonRangeMap.put(Range.closed(curLat, curLat + latStep), tmpLonRangeMap);
      }
      
      curLat += latStep;
    }
  }
//  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#add(String)
   */
  @Override
  public void add(Id id)
  {
    // Just add this to a random patch in the grid
    double lat = this.getRandomLatitudeInGrid();
    double lon = this.getRandomLongitudeInGrid();
    
    try
    {
      this.latLonRangeMap.get(lat).get(lon).add(id);
      this.idLatLonMap.put(id, new LatitudeLongitude(lat, lon));
    }
    catch(NullPointerException e)
    {
      // If either the lat or lon is not found in this container
      // If either the lat or lon is not found in this container
      GridPatchWeightedIdContainer.LOGGER.debug("Couldn't add id, " + id + 
          ", because the latitude and/or longitude could not be found in this grid.");
      GridPatchWeightedIdContainer.LOGGER.debug( "Grid latitude range: [" + 
          this.minLatitude + ", " + this.maxLatitude + ")");
      GridPatchWeightedIdContainer.LOGGER.debug("Grid longitude range: [" + 
          this.minLongitude + ", " + this.maxLongitude + ")");
    }
  }
  
  /**
   * 
   * @param id
   * @param latitude
   * @param longitude
   */
  public void add(Id id, double latitude, double longitude)
  {
    if(latitude < this.minLatitude || latitude > this.maxLatitude)
    {
      GridPatchWeightedIdContainer.LOGGER.debug("Couldn't add id, " + id + 
          ", because the latitude and/or longitude are out of range for this grid");
      GridPatchWeightedIdContainer.LOGGER.debug( "Grid latitude range: [" + 
          this.minLatitude + ", " + this.maxLatitude + ")");
      GridPatchWeightedIdContainer.LOGGER.debug("Grid longitude range: [" + 
          this.minLongitude + ", " + this.maxLongitude + ")");
      return;
    }
    
    try
    {
      this.latLonRangeMap.get(latitude).get(longitude).add(id);
      this.idLatLonMap.put(id, new LatitudeLongitude(latitude, longitude));
    }
    catch(NullPointerException e)
    {
      // If either the lat or lon is not found in this container
      GridPatchWeightedIdContainer.LOGGER.debug("Couldn't add id, " + id + 
          ", because the latitude and/or longitude could not be found in this grid.");
      GridPatchWeightedIdContainer.LOGGER.debug( "Grid latitude range: [" + 
          this.minLatitude + ", " + this.maxLatitude + ")");
      GridPatchWeightedIdContainer.LOGGER.debug("Grid longitude range: [" + 
          this.minLongitude + ", " + this.maxLongitude + ")");
    }
    
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    if(this.idLatLonMap.containsKey(id))
    {
      double lat = this.idLatLonMap.get(id).getLatitude();
      double lon = this.idLatLonMap.get(id).getLongitude();
      
      this.latLonRangeMap.get(lat).get(lon).remove(id);
      this.idLatLonMap.remove(id);
      return true;
    }
    
    return false;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#remove(String)
   */
  @Override
  public boolean remove(String idString)
  {
    Id id;
    try
    {
      id = new Id(idString);
    }
    catch (IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idLatLonMap
      return false;
    }
    
    if(this.idLatLonMap.containsKey(id))
    {
      double lat = this.idLatLonMap.get(id).getLatitude();
      double lon = this.idLatLonMap.get(id).getLongitude();
      
      this.latLonRangeMap.get(lat).get(lon).remove(id);
      this.idLatLonMap.remove(id);
      return true;
    }
    
    return false;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#clear()
   */
  @Override
  public void clear()
  {
    
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#getRandom()
   */
  @Override
  public Id getRandom()
  {
    if(this.idLatLonMap.size() == 0)
    {
      return null;
    }
    
    List<Id> keyList = new ArrayList<Id>(this.idLatLonMap.keySet());
    int randomIndex = Utils.getRandomInt(keyList.size());
    return keyList.get(randomIndex);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#getConnected(Id)
   */
  @Override
  public Id getConnected(Id id)
  {
    Id retVal = null;
    
    if(this.idLatLonMap.containsKey(id))
    {
      // First check to see if the connected agent is from inElement's patch
      if(Utils.getRandomNumberGenerator().nextDouble() < this.inPatchProbability)
      {
        double lat = this.idLatLonMap.get(id).getLatitude();
        double lon = this.idLatLonMap.get(id).getLongitude();
        
        Id[] tempArr = (Id[])this.latLonRangeMap.get(lat).get(lon).toArray();
        int arrSize = tempArr.length;
        if(arrSize > 1)
        {
          int randomIndex = Utils.getRandomInt(arrSize);
          // Picked the same element, so either go up or down one to get a different random one
          if(randomIndex + 1 < arrSize)
          {
            ++randomIndex;
          }
          else
          {
            --randomIndex;
          }
          retVal = tempArr[randomIndex];
        }
      }
      
      // If we either randomly decided that we should pick outside the current patch, or looking in patch yielded no connection
      if(retVal == null)
      {
        List<Id> keyList = new ArrayList<Id>(this.idLatLonMap.keySet());
        int keyListSize = keyList.size();
        if(keyListSize > 1)
        {
          int randomIndex = Utils.getRandomInt(keyListSize);
          if(keyList.get(randomIndex).equals(id))
          {
            // Picked the same element, so either go up or down one to get a different random one
            if(randomIndex + 1 < keyListSize)
            {
              ++randomIndex;
            }
            else
            {
              --randomIndex;
            }
          }
          retVal = keyList.get(randomIndex);
        }
      }
    }
    
    return retVal;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#getConnected(String)
   */
  @Override
  public Id getConnected(String idString)
  {
    Id retVal = null;
    
    Id id;
    try
    {
      id = new Id(idString);
    }
    catch (IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idLatLonMap
      return null;
    }
    
    if(this.idLatLonMap.containsKey(id))
    {
      // First check to see if the connected agent is from inElement's patch
      if(Utils.getRandomNumberGenerator().nextDouble() < this.inPatchProbability)
      {
        double lat = this.idLatLonMap.get(id).getLatitude();
        double lon = this.idLatLonMap.get(id).getLongitude();
        
        Id[] tempArr = (Id[])this.latLonRangeMap.get(lat).get(lon).toArray();
        int arrSize = tempArr.length;
        if(arrSize > 1)
        {
          int randomIndex = Utils.getRandomInt(arrSize);
          // Picked the same element, so either go up or down one to get a different random one
          if(randomIndex + 1 < arrSize)
          {
            ++randomIndex;
          }
          else
          {
            --randomIndex;
          }
          retVal = tempArr[randomIndex];
        }
      }
      
      // If we either randomly decided that we should pick outside the current patch, or looking in patch yielded no connection
      if(retVal == null)
      {
        List<Id> keyList = new ArrayList<Id>(this.idLatLonMap.keySet());
        int keyListSize = keyList.size();
        if(keyListSize > 1)
        {
          int randomIndex = Utils.getRandomInt(keyListSize);
          if(keyList.get(randomIndex).equals(id))
          {
            // Picked the same element, so either go up or down one to get a different random one
            if(randomIndex + 1 < keyListSize)
            {
              ++randomIndex;
            }
            else
            {
              --randomIndex;
            }
          }
          retVal = keyList.get(randomIndex);
        }
      }
    }
    
    return retVal;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#getConnected(String, int)
   */
  @Override
  public List<Id> getConnected(Id id, int howMany)
  {
    if(howMany <= 0)
    {
      GridPatchWeightedIdContainer.LOGGER.warn("howMany must be > 0");
      return null;
    }
    
    if(!this.idLatLonMap.containsKey(id))
    {
      return null;
    }
    
    List<Id> retList;
    final int idLatLonMapSize = this.idLatLonMap.size();
    if(howMany >= idLatLonMapSize)
    {
      // We are asking for more connections than are in the entire Grid, so just add every Id that is not idString
      retList = new ArrayList<Id>(idLatLonMapSize - 1);
      GridPatchWeightedIdContainer.LOGGER.warn("Asked for a connected list that is equal to or larger than the number of agents stored");
      
      for(Id idKey : this.idLatLonMap.keySet())
      {
        if(!idKey.equals(id))
        {
          retList.add(idKey);
        }
      }
    }
    else
    {
      // Create an array list of howMany elements
      retList = new ArrayList<Id>(howMany);
      
      final List<Id> tmpListSamePatch = new ArrayList<>();
      final List<Id> tmpListGrid = new ArrayList<>();
      
      // Loop over the entire list of agents to make two lists: one of ids that share the same patch as idString, the other of ids that are in
      // this grid, but not the same patch
      for(Id idKey : this.idLatLonMap.keySet())
      { 
        LatitudeLongitude latLon = this.idLatLonMap.get(idKey);
        if(!idKey.equals(id))
        {
          if(this.latLonRangeMap.get(latLon.getLatitude()).get(latLon.getLongitude()).contains(idKey))
          {
            tmpListSamePatch.add(idKey);
          }
          else
          {
            tmpListGrid.add(idKey);
          }
        }
      }
      
      // Now that I have the two lists, just fill the return List
      
      // Fill it with howMany Ids that are not idString
      for(int i = 0; i < howMany; ++i)
      {
        // First check to see if the connected agent is from inElement's patch
        if(Utils.getRandomNumberGenerator().nextDouble() < this.inPatchProbability)
        {
          int randomIndex = Utils.getRandomInt(tmpListSamePatch.size());
          retList.add(tmpListSamePatch.remove(randomIndex));
        }
        else
        {
          int randomIndex = Utils.getRandomInt(tmpListGrid.size());
          retList.add(tmpListGrid.remove(randomIndex));
        }
      }
    }
    
    return retList;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConnectable#getConnected(String, int)
   */
  @Override
  public List<Id> getConnected(String idString, int howMany)
  {
    Id id;
    try
    {
      id = new Id(idString);
    }
    catch (IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idLatLonMap
      return null;
    }
    
    if(howMany <= 0)
    {
      GridPatchWeightedIdContainer.LOGGER.warn("howMany must be > 0");
      return null;
    }
    
    if(!this.idLatLonMap.containsKey(id))
    {
      return null;
    }
    
    List<Id> retList;
    final int idLatLonMapSize = this.idLatLonMap.size();
    if(howMany >= idLatLonMapSize)
    {
      // We are asking for more connections than are in the entire Grid, so just add every Id that is not idString
      retList = new ArrayList<Id>(idLatLonMapSize - 1);
      GridPatchWeightedIdContainer.LOGGER.warn("Asked for a connected list that is equal to or larger than the number of agents stored");
      
      for(Id idKey : this.idLatLonMap.keySet())
      {
        if(!idKey.equals(id))
        {
          retList.add(idKey);
        }
      }
    }
    else
    {
      // Create an array list of howMany elements
      retList = new ArrayList<Id>(howMany);
      
      final List<Id> tmpListSamePatch = new ArrayList<>();
      final List<Id> tmpListGrid = new ArrayList<>();
      
      // Loop over the entire list of agents to make two lists: one of ids that share the same patch as idString, the other of ids that are in
      // this grid, but not the same patch
      for(Id idKey : this.idLatLonMap.keySet())
      { 
        LatitudeLongitude latLon = this.idLatLonMap.get(idKey);
        if(!idKey.equals(id))
        {
          if(this.latLonRangeMap.get(latLon.getLatitude()).get(latLon.getLongitude()).contains(idKey))
          {
            tmpListSamePatch.add(idKey);
          }
          else
          {
            tmpListGrid.add(idKey);
          }
        }
      }
      
      // Now that I have the two lists, just fill the return List
      
      // Fill it with howMany Ids that are not idString
      for(int i = 0; i < howMany; ++i)
      {
        // First check to see if the connected agent is from inElement's patch
        if(Utils.getRandomNumberGenerator().nextDouble() < this.inPatchProbability)
        {
          int randomIndex = Utils.getRandomInt(tmpListSamePatch.size());
          retList.add(tmpListSamePatch.remove(randomIndex));
        }
        else
        {
          int randomIndex = Utils.getRandomInt(tmpListGrid.size());
          retList.add(tmpListGrid.remove(randomIndex));
        }
      }
    }
    
    return retList;
  }
  
  /**
   * 
   * @return a random double value between minLatitude (inclusive) and maxLatitude (exclusive)
   */
  private double getRandomLatitudeInGrid()
  {
    double range = this.maxLatitude - this.minLatitude;
    return this.minLatitude + range * Utils.getRandomNumberGenerator().nextDouble();
  }
  
  /**
   * 
   * @return a random double value between minLongitude (inclusive) and maxLongitude (exclusive)
   */
  private double getRandomLongitudeInGrid()
  {
    double range = this.maxLongitude - this.minLongitude;
    return this.minLongitude + range * Utils.getRandomNumberGenerator().nextDouble();
  }
}
