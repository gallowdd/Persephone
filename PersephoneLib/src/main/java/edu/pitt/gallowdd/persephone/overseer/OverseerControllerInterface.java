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

package edu.pitt.gallowdd.persephone.overseer;

import java.time.LocalDateTime;

import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.location.GenericLocation;
import edu.pitt.gallowdd.persephone.network.GenericNetwork;

/**
 * 
 * @author David Galloway
 *
 */
public interface OverseerControllerInterface {
  
  /**
   * Determines whether or not this Overseer contains the agentId specified
   * 
   * @param agentId
   * @return {@code true} if the internal map contains the agent ID, {@code false} otherwise
   */
  public boolean containsAgent(String agentId);
  
  /**
   * 
   * @param agentId
   * @return the Agent stored in this controller or null if not found
   */
  public GenericAgent getAgent(String agentId);
  
  /**
   * Determines whether or not this Overseer contains the locationId specified
   * 
   * @param locationId
   * @return {@code true} if the internal map contains the location ID, {@code false} otherwise
   */
  public boolean containsLocation(String locationId);
  
  /**
   * Check each LocationListManager to see if it contains the locationId. If one does, then get the GenericLocation and return it.
   * 
   * @param locationId
   * @return the location stored in this Overseer or null if not found
   */
  public GenericLocation getLocation(String locationId);
  
  /**
   * Determines whether or not this Overseer contains the networkId specified
   * 
   * @param networkId
   * @return {@code true} if the internal map contains the network ID, {@code false} otherwise
   */
  public boolean containsNetwork(String networkId);
  
  /**
   * 
   * @param networkId
   * @return the network stored in this controller or null if not found
   */
  public GenericNetwork getNetwork(String networkId);
  
  /**
   * To stay synchronized, the Overseer keeps track of the time that its Controllers will use
   * @return the simDateTime the current DateTime of the simulation
   */
  public LocalDateTime getSimDateTime();
  
}
