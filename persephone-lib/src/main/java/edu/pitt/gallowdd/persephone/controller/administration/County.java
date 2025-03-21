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

package edu.pitt.gallowdd.persephone.controller.administration;

import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * This class represents a County Administrative Unit
 * 
 * @author David Galloway
 **/
public class County extends AdministrativeUnit {

  /**
   * @param id
   * @throws IdException 
   */
  public County(String id) throws IdException
  {
    super(id);
  }
  
  
//  /* (non-Javadoc)
//   * @see java.lang.Comparable#compareTo(java.lang.Object)
//   */
//  @Override
//  public int compareTo(Controller<T> o)
//  {
//    return new CompareToBuilder()
//      .append(this.getId(), o.getId())
//      .toComparison();
//  }
//
//  /* (non-Javadoc)
//   * @see java.util.concurrent.Callable#call()
//   */
//  @Override
//  public ControllerOutput call() throws Exception
//  {
//    // TODO Auto-generated method stub
//    return null;
//  }
  
}
