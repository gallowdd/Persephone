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
  
  /*
   * Simulation Events
   */
  ADVANCE_TIME_EVENT(EventBaseTypeEnum.SIMULATION_EVENT, "ADVANCE-TIME"),
  
  /*
   * Agent Events
   */
  AGENT_DEATH_EVENT(EventBaseTypeEnum.AGENT_EVENT, "DEATH"),
  AGENT_GIVE_BIRTH_EVENT(EventBaseTypeEnum.AGENT_EVENT, "GIVE-BIRTH"),
  AGENT_STATE_TRANSITION_EVENT(EventBaseTypeEnum.AGENT_EVENT, "STATE-TRANSITION"),
  
  /*
   *Location Events
   */
  LOCATION_START_MIXING_EVENT(EventBaseTypeEnum.LOCATION_EVENT, "MIX-START"),
  LOCATION_CLOSE_EVENT(EventBaseTypeEnum.LOCATION_EVENT, "CLOSE"),
  LOCATION_OPEN_EVENT(EventBaseTypeEnum.LOCATION_EVENT, "OPEN"),;
  
  private final EventBaseTypeEnum baseType;
  private final String label;
  
  private EventTypeEnum(EventBaseTypeEnum baseType, String label)
  {
    this.baseType = baseType;
    this.label = label;
  }
  
  @Override
  public String toString()
  {
    return this.baseType.toString() + "->" + this.label;
  }
  
}
