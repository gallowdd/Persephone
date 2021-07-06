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

package edu.pitt.gallowdd.persephone.util;

import java.time.LocalDate;

/**
 * Class with variables that will need to be accessed across the simulation
 * These variables are all public access
 */
public class Globals {
  
  /**
   * The simulationDate is a LocalDate that is set by the runtime parameters
   *  -- Note: this must be initialized AFTER the runtime parameters are read
   */
  static public LocalDate simulationDate = null;
  
  /**
   * The simDay is a value from 0 to Params.getDays() - 1
   */
  static public int simDay;
  
  /**
   * The simHour is a value from 0 to 23 representing the hour of the day (0 = midnight to 23 = 11:00 PM)
   */ 
  static public int simHour;
  
  /**
   * 
   */
  public static void initialize()
  {
    Globals.simulationDate = Params.getStartDate();
    Globals.simDay = 0;
    Globals.simHour = 0;
  }
}
