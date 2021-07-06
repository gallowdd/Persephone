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

import java.util.List;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * The interface which all Containers of Ids will implement
 * 
 * @author David Galloway
 */
public interface IdConnectable extends Connectable<Id> {
  
  /**
   * Add the Id to the this collection
   * 
   * @param id the id to add
   */
  public void add(Id id);
  
  /**
   * Remove the Id created from the idString
   * 
   * @param idString the String to use for Id creation
   * @return {@code true} if the id was removed, {@code false} otherwise
   * @throws IdException if the idString is invalid
   */
  public boolean remove(String idString) throws IdException;
  
  /**
   * Remove the Id created from the idString
   * 
   * @param id the id to remove
   * @return {@code true} if the id was removed, {@code false} otherwise
   */
  public boolean remove(Id id);
  
  /**
   * Removes all of the elements from this Container. It will be empty after this call.
   */
  public void clear();

  /**
   * Get a random element of type {@code Id} from this connected collection
   * @return a random element of type {@code Id} in this connected collection or {@code null} if none 
   * are in this connected collection
   */
  @Override
  public Id getRandom();
  
  /**
   * Given an idString, create an {@code Id} and return an element of type {@code Id} connected to it in this collection
   * 
   * Note: this will return {@code null} if there are no connected elements of type {@code Id} and will return a single
   *   random element of type {@code Id} connected to the created {@code Id}
   * 
   * @param idString the String to use for Id creation
   * 
   * @return An elements of type {@code T} connected to the id created by idString that we searched
   *   for, or {@code null} if not found
   */
  public Id getConnected(String idString);
  
  /**
   * Given an id, return an element of type {@code Id} connected to it in this collection
   * 
   * Note: this will return {@code null} if there are no connected elements of type {@code Id} or will return a single
   *   random element of type {@code Id} connected to the created {@code Id}
   * 
   * @param idString the String to use for Id creation
   * 
   * @return An elements of type {@code T} connected to the id searched for, or {@code null} if not found
   */
  @Override
  public Id getConnected(Id id);
  
  /**
   * Given an idString, return a List of howMany connected Ids
   * 
   * Note: this will return null if there are no connected elements of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id created by idString (i.e. if there aren't enough connected
   *   to fill the List, then you will get one that is size < howMany)
   * 
   * @param idString the ID String to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return A List of at most howMany elements of type {@code Id} connected to the id created by idString that we searched
   *   for, or {@code null} if not found
   */
  public List<Id> getConnected(String idString, int howMany);
  
  /**
   * Given an id, return a List of howMany connected {@code Id}s
   * 
   * Note: this will return null if there are no connected elements of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id (i.e. if there aren't enough connected to fill the List, 
   *   then you will get one that is size < howMany)
   * 
   * @param id the id to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return A List of at most howMany elements of type {@code Id} connected to the id searched for, or {@code null} if 
   *   not found
   */
  @Override
  public List<Id> getConnected(Id id, int howMany);
}
