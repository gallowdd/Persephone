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

package edu.pitt.gallowdd.persephone.location.sublocation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.container.FullyConnectedIdContainer;
import edu.pitt.gallowdd.persephone.container.IdConnectable;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * The Base Class for all Sublocations in the simulation. "Sublocations" are locations that don't have a latitude/longitude/altitude, 
 * because they are contained within another Location
 * 
 * @author David Galloway
 **/
public abstract class GenericSublocation {
  
  private static final Logger LOGGER = LogManager.getLogger(GenericSublocation.class.getName());
  
  private final Id id;
  private Id containingLocationId;
  private final IdConnectable mixingContainer;
  
  /**
   * The base class of any sublocation that does not move.
   * 
   * @param idString The sublocation's idString
   * @param containingLocationIdString the containing location's idString
   * @throws IdException if the idString or the containingLocationIdString is not valid
   */
  protected GenericSublocation(String idString, String containingLocationIdString) throws IdException
  {
    super();
    
    if(idString == null || containingLocationIdString == null)
    {
      throw new IdException("null");
    }
    
    this.id = new Id(idString);
    this.containingLocationId = new Id(containingLocationIdString);
    this.mixingContainer = new FullyConnectedIdContainer();
  }
  
  /**
   * The base class of any sublocation that does not move.
   * 
   * @param idString The sublocation's idString
   * @param containingLocationId the containing location's id
   * @throws IdException if the idString is not valid
   */
  protected GenericSublocation(String idString, Id containingLocationId) throws IdException
  {
    super();
    
    if(idString == null || containingLocationId == null)
    {
      throw new IdException("null");
    }
    
    this.id = new Id(idString);
    this.containingLocationId = new Id(containingLocationId);
    this.mixingContainer = new FullyConnectedIdContainer();
  }
  
  /** 
   * @param idString The location's idString
   * @param containingLocationIdString the containing location's idString
   * @param mixingContainer a container for Id strings
   * @throws IdException if the idString or the containingLocationIdString is not valid
   */
  protected GenericSublocation(String idString, String containingLocationIdString, IdConnectable mixingContainer) throws IdException 
  {
    if(idString == null || containingLocationIdString == null)
    {
      throw new IdException("null");
    }
    
    this.id = new Id(idString);
    this.containingLocationId = new Id(containingLocationIdString);
    
    this.mixingContainer = mixingContainer;
    GenericSublocation.LOGGER.trace("New instance of GenericLocation");
  }
  
  /** 
   * @param idString The location's idString
   * @param containingLocationId the containing location's id
   * @param mixingContainer a container for Id strings
   * @throws IdException if the idString is not valid
   */
  protected GenericSublocation(String idString, Id containingLocationId, IdConnectable mixingContainer) throws IdException 
  {
    if(idString == null || containingLocationId == null)
    {
      throw new IdException("null");
    }
    
    this.id = new Id(idString);
    this.containingLocationId = new Id(containingLocationId);
    
    this.mixingContainer = mixingContainer;
    GenericSublocation.LOGGER.trace("New instance of GenericLocation");
  }
  
  /**
   * @return this location's id
   */
  public Id getId() 
  {
    return this.id;
  }
  
  /**
   * @return the containingLocationId
   */
  public Id getContainingLocation()
  {
    return this.containingLocationId;
  }
  
  /**
   * @return the mixingContainer
   */
  public IdConnectable getMixingContainer()
  {
    return this.mixingContainer;
  }
}
