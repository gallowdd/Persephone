/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2022  David Galloway / University of Pittsburgh
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

import java.util.List;
import java.util.Optional;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * 
 * @author David Galloway
 *
 */
public abstract class GenericIdMixingContainer {
  
  private final MixingRoleTypeEnum[] mixingRoleTypesAccepted;
  
  /**
   * Default Constructor
   * 
   * @param roleTypes an argument list of MixingRoleTypeEnum that will be used to populate the 
   *   internal array of roleTypesAcepted
   */
  public GenericIdMixingContainer(MixingRoleTypeEnum ... roleTypes)
  {
    this.mixingRoleTypesAccepted = roleTypes;
  }
  
  /**
   * Add the Id to the this collection
   *
   * @param id the id to add
   * @throws MixingContainerTypeMatchException 
   */
  public abstract void add(Id id) throws MixingContainerTypeMatchException;
  
  /**
   * Remove the Id created from the idString
   * 
   * @param idString the String to use for Id creation
   * @return {@code true} if the id was removed, {@code false} otherwise
   * @throws IdException if the idString is invalid
   */
  public abstract boolean remove(String idString) throws IdException;
  
  /**
   * Remove the Id created from the idString
   * 
   * @param id the id to remove
   * @return true if the id was removed, false otherwise
   */
  public abstract boolean remove(Id id);
  
  /**
   * Removes all of the elements from this Container. It will be empty after this call.
   */
  public abstract void clear();
  
  /**
   * Get a random element of type {@code Id} from this connected collection
   * 
   * @return a random element of type {@code Id} in this connected collection or {@code Optional.empty()} if none 
   * are in this connected collection
   */
  public abstract Optional<Id> getRandom();
  
  /**
   * Given an id, return an element of type {@code Id} connected to it in this collection
   * 
   * Note: this will return {@code Optional.empty()} if there are no connected elements of type {@code Id} or will return a single
   *   random element of type {@code Id} connected to the created {@code Id}
   * 
   * @param id the id to search for
   * 
   * @return  An Id connected to the Id searched for, or {@code Optional.empty()} if not found
   */
  public abstract Optional<Id> getConnected(Id id);
  
  /**
   * Given an idString, create an ID and return an element of type {@code Id} connected to it in this collection
   * 
   * Note: this will return {@code Optional.empty()} if there are no connected elements of type {@code Id} and will return a single
   *   random element of type {@code Id} connected to the created {@code Id}
   * 
   * @param idString the String to use for Id creation
   * 
   * @return An Id connected to the Id created by idString that we searched
   *   for, or {@code Optional.empty()} if not found
   */
  public abstract Optional<Id> getConnected(String idString);
  
  /**
   * Given an id, return a List of howMany connected {@code Id}s
   * 
   * Note: this will return an Optional List of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id (i.e. if there aren't enough connected to fill the List, 
   *   then you will get one that is size < howMany)
   * 
   * @param id the id to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return An Optional List of at most howMany elements of type {@code Id} connected to the id searched for, or {@code Optional.empty()} if not found
   */
  public abstract Optional<List<Id>> getConnected(Id id, int howMany);
  
  /**
   * Given an idString, return a List of howMany connected Ids
   * 
   * Note: this will return an Optional List of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id created by idString (i.e. if there aren't enough connected
   *   to fill the List, then you will get one that is size < howMany)
   * 
   * @param idString the ID String to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return An Optional List of at most howMany elements of type {@code Id} connected to the id searched for, or {@code Optional.empty()} if not found
   */
  public abstract Optional<List<Id>> getConnected(String idString, int howMany);
  
  /**
   * @return an array of the MixingTypes that this container will allow to be added
   */
  public MixingRoleTypeEnum[] getMixingRoleTypesAccepted()
  {
    return this.mixingRoleTypesAccepted;
  }
  
  /**
   * Check to see if the mixing role type to check is one that is accepted by this MixingContainer
   * 
   * @param mixingRoleTypeCheck the mixing role type to check
   * @return true if the mixing role type is accepted by this container, false otherwise 
   */
  public boolean isMixingRoleTypeAccepted(MixingRoleTypeEnum mixingRoleTypeCheck)
  {
    final int length = this.mixingRoleTypesAccepted.length;
    for(int i = 0; i < length; ++i)
    {
      if(mixingRoleTypeCheck == this.mixingRoleTypesAccepted[i])
      {
        return true;
      }
    }
    
    return false;
  }
}
