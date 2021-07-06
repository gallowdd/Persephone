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
 * An enumeration of all Activity Types in the application
 *  
 * @author David Galloway
 */

public enum ActivityType
{
  
  
  /**
   * DEFAULT
   */
  @SerializedName("HOME")
  HOME(0),
  
  @SerializedName("WORKPLACE")
  WORKPLACE(2),
  
  @SerializedName("OFFICE")
  OFFICE(3),
  
  @SerializedName("SCHOOL")
  SCHOOL(3),
  
  @SerializedName("CLASSROOM")
  CLASSROOM(4),

  @SerializedName("HOME_NEIGHBORHOOD")
  HOME_NEIGHBORHOOD(1),
  
  @SerializedName("WORK_NEIGHBORHOOD")
  WORK_NEIGHBORHOOD(1);
  
  private int rank = 0;
  
  private ActivityType(int rank)
  {
    this.rank = rank;
  }
  
  /**
   * 
   * @return the rank integer
   */
  public int getRank()
  {
    return this.rank;
  }
}
