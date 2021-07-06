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

package edu.pitt.gallowdd.persephone.agent.activity;

import com.google.gson.annotations.SerializedName;

/**
 * An enumeration of all Acitvity Types in the application
 *  
 * @author David Galloway
 */
public enum ScheduleType
{
  
  /**
   * Mon - Fri, 08:00 - 16:59 / Holidays Off
   */
  @SerializedName("BANKER")
  BANKER,
  
  /**
   * 2-4 days selected from Mon - Fri, 08:00 - 16:59 / Holidays off
   */
  @SerializedName("BANKER_PT")
  BANKER_PT,
  
  /**
   * Three shifts covered 24 hours a day. Each shift has 0.33 probability of being selected.
   * 5 Days selected from Sun - Sat. 08:00 - 15:59, 16:00 - 23:59, 00:00 - 07:59
   */
  @SerializedName("SHIFT_24HR")
  SHIFT_24HR,
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected. 
   * 5 days selected from Sun - Sat. 08:00 - 15:59, 16:00 - 23:59
   **/
  @SerializedName("SHIFT_16HR_1ST_TURN_2ND_TURN")
  SHIFT_16HR_1ST_TURN_2ND_TURN,
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected. 
   * 5 days selected from Sun - Sat. 16:00 - 23:59, 00:00 - 07:59
   */
  @SerializedName("SHIFT_16HR_2ND_TURN_3RD_TURN")
  SHIFT_16HR_2ND_TURN_3RD_TURN,
  
  /**
   * 5 Days selected from Sun - Sat, 08:00 - 15:59 
   */
  @SerializedName("STEADY_1ST_TURN")
  STEADY_1ST_TURN,
  
  /**
   * 2-4 Days selected from Sun - Sat, 08:00 - 15:59
   */
  @SerializedName("STEADY_1ST_TURN_PT")
  STEADY_1ST_TURN_PT,
  
  /**
   * 5 Days selected from Sun - Sat, 16:00 - 23:59
   */
  @SerializedName("STEADY_2ND_TURN")
  STEADY_2ND_TURN,
  
  /**
   * 2-4 Days selected from Sun - Sat, 16:00 - 23:59
   */
  @SerializedName("STEADY_2ND_TURN_PT")
  STEADY_2ND_TURN_PT,
  
  /**
   * 5 Days selected from Sun - Sat, 00:00 - 07:59
   */
  @SerializedName("STEADY_3RD_TURN")
  STEADY_3RD_TURN,
  
  /**
   * 2-4 Days selected from Sun - Sat, 00:00 - 07:59
   */
  @SerializedName("STEADY_3RD_TURN_PT")
  STEADY_3RD_TURN_PT,
  
  /**
   * Three shifts covered 24 hours a day. Each shift has 0.33 probability of being selected. 
   * 1 or 2 Days selected from Sat & Sun 08:00 - 15:59, 16:00 - 23:59, 00:00 - 07:59
   */
  @SerializedName("WEEKEND_SHIFT_24HR")
  WEEKEND_SHIFT_24HR,
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected.
   * 1 or 2 Days elected from Sat & Sun. 08:00 - 15:59, 16:00 - 23:59
   */
  @SerializedName("WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN")
  WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN,
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected.
   * 1 or 2 Days elected from Sat & Sun. 16:00 - 23:59, 08:00 - 15:59
   */
  @SerializedName("WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN")
  WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN,
  
  /**
   * Mon - Fri, 07:00 - 15:59 / Holidays Off / Summer Break / Winter Break / Spring Break
   */
  @SerializedName("PRIMARY_SCHOOL")
  PRIMARY_SCHOOL,
  
  /**
   * Mon - Fri, 07:00 - 15:59 / Holidays Off / Summer Break / Winter Break / Spring Break
   */
  @SerializedName("SECONDARY_SCHOOL")
  SECONDARY_SCHOOL;
}
