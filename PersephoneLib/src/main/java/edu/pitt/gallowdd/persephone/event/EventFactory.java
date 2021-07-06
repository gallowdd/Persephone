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
 * 
 * @author David Galloway
 *
 */
public class EventFactory {
  
  /**
   * 
   * @param type the Event type to create
   * @return an Event of the specified type
   */
  public static Event createEvent(EventTypeEnum type)
  {
    var retVal = switch(type) {
      case AGENT_ACTION -> new ;
      case FEBRUARY, SEPTEMBER, OCTOBER, NOVEMBER, DECEMBER -> 1;
      case MARCH, MAY, APRIL, AUGUST -> 2;
      default -> 0; 
    };
    
    
    
    return retVal;
  }

}
