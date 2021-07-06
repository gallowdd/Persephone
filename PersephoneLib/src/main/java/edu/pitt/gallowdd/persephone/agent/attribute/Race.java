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
package edu.pitt.gallowdd.persephone.agent.attribute;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import edu.pitt.gallowdd.persephone.util.EnumNotFoundException;

/**
 * An enumeration of all Race Types in the application
 *  (Along with the common string representation used in the csv files)
 *  
 * @author David Galloway
 */
@SuppressWarnings("javadoc")
public enum Race
{
  
  @SerializedName("-1")
  UNSET(-1),
  
  @SerializedName("1")
  WHITE(1),
  
  @SerializedName("2")
  BLACK(2),
  
  @SerializedName("3")
  AMERICAN_INDIAN(3),
  
  @SerializedName("4")
  ALASKA_NATIVE(4),
  
  @SerializedName("5")
  NATIVE_TRIBE_SPECIFIED(5),
  
  @SerializedName("6")
  ASIAN(6),
  
  @SerializedName("7")
  PACIFIC_ISLANDER(7),
  
  @SerializedName("8")
  OTHER(8),
  
  @SerializedName("9")
  TWO_OR_MORE(9);
  
  private int value;
  private static Map<Integer, Race> map = new HashMap<>();
  
  private Race(int value)
  {
    this.value = value;
  }
  
  /**
   * @return the integer value of this enumerated value
   */
  public int getValue()
  {
    return this.value;
  }
  
  static 
  {
    for(Race race : Race.values())
    {
      Race.map.put(race.value, race);
    }
  }
  
  /**
   * Return an Enumeration that matches an integer code
   * 
   * @param raceCode
   * @return the enumerated value matching the integer code
   * @throws EnumNotFoundException if the integer value is not in the map
   */
  public static Race valueOf(int raceCode) throws EnumNotFoundException
  {
    Race retVal = Race.map.get(raceCode);
    if(retVal != null)
    {
      return retVal;
    }
    else
    {
      throw new EnumNotFoundException(raceCode, Race.class);
    }
  }
  
}
