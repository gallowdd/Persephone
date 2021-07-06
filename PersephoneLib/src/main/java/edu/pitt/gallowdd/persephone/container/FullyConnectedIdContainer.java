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

package edu.pitt.gallowdd.persephone.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * A container that allows for selecting IDs connected to other IDs in a "fully-connected" way. Fully-connected means that every 
 * ID is connected to every other ID with equal weight. I.e. there is an equal probability of any ID being connected to 
 * any other ID.
 * 
 * @author David Galloway
 */
public class FullyConnectedIdContainer implements IdConnectable {
  
  private static final Logger LOGGER = LogManager.getLogger(FullyConnectedIdContainer.class.getName());
  
  private final Set<Id> idSet = new HashSet<>();
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#add(id)
   */
  @Override
  public void add(Id id)
  {
    this.idSet.add(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    return this.idSet.remove(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#remove(String)
   */
  @Override
  public boolean remove(String idString)
  {
    try
    {
      return this.idSet.remove(new Id(idString));
    } 
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return false;
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#clear()
   */
  @Override
  public void clear()
  {
    this.idSet.clear();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectablee#getRandom()
   */
  @Override
  public Id getRandom()
  {
    if(this.idSet.size() == 0)
    {
      
    }
    List<Id> asList = new ArrayList<>(this.idSet);
    Collections.shuffle(asList, Utils.getRandomNumberGenerator());
    return asList.get(0);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#getConnected(Id)
   */
  public Id getConnected(Id id)
  {
    // For fully connected mixing group, there is an equal probability of any two Ids being connected, so
    // we just need to randomly pick an Id that is not id
    Id retVal = null;
    int size = this.idSet.size();
    if(this.idSet.contains(id) && size > 1)
    {
      while(true)
      {
        Id testVal = this.getRandom();
        
        if(!testVal.equals(id))
        {
          retVal = testVal;
          break;
        }
      }
    }
    
    return retVal;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#getConnected(String)
   */
  @Override
  public Id getConnected(String idString)
  {
    try
    {
      Id id = new Id(idString);
      return this.getConnected(id);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return null;
    }
  }
  
  @Override
  public List<Id> getConnected(Id id, int howMany)
  {
    List<Id> retVal = null;
    if(howMany <= 0)
    {
      FullyConnectedIdContainer.LOGGER.warn("howMany must be > 0");
      return null;
    }
    else if(!this.idSet.contains(id))
    {
      return null;
    }
    
    // For fully connected mixing group, there is an equal probability of any two Id Strings being connected, so
    // we just need to randomly pick howMany Id Strings that are not inElement
    final int size = this.idSet.size();
    if(howMany >= size)
    {
      retVal = new ArrayList<>(size - 1);
      FullyConnectedIdContainer.LOGGER.warn("Asked for a connected list that is equal to or larger than the number of agents stored");
      
      for(Id checkId : this.idSet)
      {
        if(!checkId.equals(id))
        {
          retVal.add(id);
        }
      }
      return retVal;
    }
    else
    {
      retVal = new ArrayList<Id>(howMany);
      
      List<Id> asList = new ArrayList<>(this.idSet);
      Collections.shuffle(asList, Utils.getRandomNumberGenerator());
      
      int addedCount = 0;
      
      for(Id checkId : this.idSet)
      {
        if(!checkId.equals(id))
        {
          ++addedCount;
          retVal.add(id);
        }
        
        if(addedCount == howMany)
        {
          break;
        }
      }
      
      return retVal;
    }
  }
  
  /* (non-Javadoc)
   * @see com.epistemix.persephone.container.IdStringConnectable#getConnected(String, int)
   */
  @Override
  public List<Id> getConnected(String idString, int howMany)
  {
    try
    {
      Id id = new Id(idString);
      return this.getConnected(id, howMany);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return null;
    }
  }
}
