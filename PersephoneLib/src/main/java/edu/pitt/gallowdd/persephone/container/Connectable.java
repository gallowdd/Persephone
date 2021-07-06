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

/**
 * The interface to allow for selecting elements from a collection of type {@code T} connected to other elements of
 * type {@code T} in some way
 * 
 * @param <T> The type of element that should be connected
 * 
 * @author David Galloway
 */
public interface Connectable<T> {
  
  /**
   * Add the element of type {@code T}
   * 
   * @param inElement the element to add
   */
  public void add(T inElement);
  
  /**
   * Remove the element of type {@code T}
   * 
   * @param inElement the element to search for
   * @return {@code true} if the element was removed, {@code false} otherwise
   */
  public boolean remove(T inElement);
  
  /**
   * Removes all of the elements from this collection. It will be empty after this call.
   */
  public void clear();
  
  /**
   * Given an element of type {@code T}, return an element of type {@code T}
   * 
   * Note: this will return {@code null} if there are no connected elements of type {@code T} and will return a single
   *   element of type {@code T} connected to inElement (how that element is selected is left to the implementation)
   * 
   * @param inElement the element to search for
   * 
   * @return An elements of type {@code T} connected to the inElement searched for, or {@code null} if not found
   */
  public T getConnected(T inElement);
  
  /**
   * Given an element of type {@code T}, return a {@code List} of howMany connected elements
   * 
   * Note: this will return {@code null} if there are no connected elements of type {@code T} and will return a {@code List} of 
   * AT MOST howMany elements of type {@code T} connected to inElement (i.e. if there aren't enough connected to fill the {@code List}, 
   * then you will get one that is size < howMany)
   * 
   * @param inElement the element to search for
   * @param howMany the (MAX) size of the ArrayList returned
   * 
   * @return A List of at most howMany elements of type {@code T} connected to the inElement searched for, or {@code null} if not found
   */
  public List<T> getConnected(T inElement, int howMany);
  
  /**
   * Get a random element of type {@code T} from this connected container
   * @return a random elements of type {@code T} in this connected container or null if none are in this connected container
   */
  public T getRandom();
}
