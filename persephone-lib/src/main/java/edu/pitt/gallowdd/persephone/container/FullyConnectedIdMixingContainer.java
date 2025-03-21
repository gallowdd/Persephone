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
import java.util.Optional;
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
public class FullyConnectedIdMixingContainer extends GenericIdMixingContainer {
  
  private static final Logger LOGGER = LogManager.getLogger(FullyConnectedIdMixingContainer.class.getName());
  
  private final Set<Id> idSet = new HashSet<>();
  
  /**
   * Default Constructor
   * 
   * Creates a FullyConnectedIdMixingContainer that accepts RoleType of ANY
   */
  public FullyConnectedIdMixingContainer()
  {
    super(MixingRoleTypeEnum.ANY);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#add(Id)
   */
  @Override
  public void add(Id id)
  {
    this.idSet.add(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    return this.idSet.remove(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(String)
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
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#clear()
   */
  @Override
  public void clear()
  {
    this.idSet.clear();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getRandom()
   */
  @Override
  public Optional<Id> getRandom()
  {
    if(this.idSet.size() == 0)
    {
      return Optional.empty();
    }
    List<Id> asList = new ArrayList<>(this.idSet);
    Collections.shuffle(asList, Utils.getRandomNumberGenerator());
    return Optional.of(asList.get(0));
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id)
   */
  public Optional<Id> getConnected(Id id)
  {
    // For fully connected mixing group, there is an equal probability of any two Ids being connected, so
    // we just need to randomly pick an Id that is not id
    int size = this.idSet.size();
    if(this.idSet.contains(id) && size > 1)
    {
      while(true)
      {
        Optional<Id> testVal = this.getRandom();
        
        if(testVal.isPresent() && !testVal.get().equals(id))
        {
          return testVal;
        }
      }
    }
    
    return Optional.empty();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String)
   */
  @Override
  public Optional<Id> getConnected(String idString)
  {
    try
    {
      Id id = new Id(idString);
      return this.getConnected(id);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return Optional.empty();
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id, int)
   */
  @Override
  public Optional<List<Id>> getConnected(Id id, int howMany)
  {
    //Optional<List<Id>> retVal = Optional.empty();
    if(howMany <= 0)
    {
      FullyConnectedIdMixingContainer.LOGGER.warn("howMany must be > 0");
      return Optional.empty();
    }
    else if(!this.idSet.contains(id))
    {
      return Optional.empty();
    }
    
    // For fully connected mixing group, there is an equal probability of any two Id Strings being connected, so
    // we just need to randomly pick howMany Id Strings that are not inElement
    final int size = this.idSet.size();
    if(howMany >= size)
    {
      List<Id> tempList = new ArrayList<>(size - 1);
      FullyConnectedIdMixingContainer.LOGGER.warn("Asked for a connected list that is equal to or larger than the number of agents stored");
      
      for(Id checkId : this.idSet)
      {
        if(!checkId.equals(id))
        {
          tempList.add(id);
        }
      }
      return Optional.of(tempList);
    }
    else
    {
      List<Id> tempList = new ArrayList<Id>(howMany);
      
      List<Id> asList = new ArrayList<>(this.idSet);
      Collections.shuffle(asList, Utils.getRandomNumberGenerator());
      
      int addedCount = 0;
      
      for(Id checkId : asList)
      {
        if(!checkId.equals(id))
        {
          ++addedCount;
          tempList.add(id);
        }
        
        if(addedCount == howMany)
        {
          break;
        }
      }
      
      return Optional.of(tempList);
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String, int)
   */
  @Override
  public Optional<List<Id>> getConnected(String idString, int howMany)
  {
    try
    {
      Id id = new Id(idString);
      return this.getConnected(id, howMany);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return Optional.empty();
    }
  }
}
