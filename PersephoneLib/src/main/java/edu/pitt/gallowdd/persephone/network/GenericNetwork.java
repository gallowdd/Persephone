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

package edu.pitt.gallowdd.persephone.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * The Base Class for all Networks (basically, locations without a specific lat/lon) in the simulation.
 * 
 * @author David Galloway
 **/
public abstract class GenericNetwork {
  
  private static final Logger LOGGER = LogManager.getLogger(GenericNetwork.class.getName());
  
  /**
   * The NullNetwork that will be used for all cases where a Null is needed
   */
  public static final GenericNetwork NULL_NETWORK = new GenericNetwork() {};
  
  private final Id id;
  private GenericIdMixingContainer mixingContainer;
  
  /**
   * Constructor used to create a NullNetwork
   */
  private GenericNetwork()
  {
    super();
    this.id = Id.NULL_ID;
  }
  
  /**
   * Constructor used when we have an Id object already
   * 
   * @param id the id to use to create this generic network
   */
  protected GenericNetwork(Id id)
  {
    super();
    this.id = id;
    GenericNetwork.LOGGER.trace("new instance of GenericNetwork");
  }
  
  /**
   * 
   * @param id
   * @param mixingContainer
   */
  protected GenericNetwork(Id id, GenericIdMixingContainer mixingContainer)
  {
    super();
    this.id = id;
    this.mixingContainer = mixingContainer;
    GenericNetwork.LOGGER.trace("new instance of GenericNetwork");
  }
  
  /**
   * @param idString
   * @throws IdException if the idString is invalid
   */
  protected GenericNetwork(String idString) throws IdException
  {
    super();
    this.id = new Id(idString);
    GenericNetwork.LOGGER.trace("new instance of GenericNetwork");
  }
  
  /**
   * @param idString
   * @param mixingContainer
   * 
   * @throws IdException if the idString is invalid
   */
  protected GenericNetwork(String idString, GenericIdMixingContainer mixingContainer) throws IdException
  {
    super();
    this.id = new Id(idString);
    this.mixingContainer = mixingContainer;
    GenericNetwork.LOGGER.trace("new instance of GenericNetwork");
  }
  
  /**
   * @return this network's id
   */
  public Id getId() 
  {
    return this.id;
  }
  
  /**
   * @return the mixingContainer
   */
  public GenericIdMixingContainer getMixingContainer()
  {
    return this.mixingContainer;
  }
  
  /**
   * @param mixingContainer the mixingContainer to set
   */
  public void setMixingContainer(GenericIdMixingContainer mixingContainer)
  {
    this.mixingContainer = mixingContainer;
  }
}
