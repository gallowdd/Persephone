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

package edu.pitt.gallowdd.persephone.event;

/**
 * An enumeration of all Event Types in the application
 *  
 * @author David Galloway
 */
public enum EventTypeEnum
{
  
  /**
   * Events that pertain to the simulation itself
   */
  SIMULATION_EVENT
  {
    @Override
    public String toString()
    {
      return "SIM_EVNT";
    }
  },
  
  /**
   * Events that pertain to agents
   * 
   * E.g.
   * <ul>
   *   <li>Agent Death -- Need to remove all other actions for particular agent from event queues</li>
   *   <li>Agent Gives Birth -- Need to create new agent(s)</li>
   *   <li>Agent Will Transition from current State in Condition to new State</li>
   *   <li>Etc.</li>
   * </ul>
   */
  AGENT_EVENT,
  
  /**
   * Events that pertain to locations
   * 
   * E.g. 
   * <ul>
   *   <li>Close</li>
   *   <li>Open</li>
   *   <li>Etc</li>
   * </ul>
   */
  LOCATION_EVENT
  {
    @Override
    public String toString()
    {
      return "PER";
    }
  },;
  
}
