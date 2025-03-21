/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2021 David Galloway / University of Pittsburgh
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
 * An enumeration of all Sex Types in the application
 *  (Along with the common string representation used in the csv files)
 *  
 * @author David Galloway
 */
@SuppressWarnings("javadoc")
public enum Sex
{
  
  @SerializedName("-1")
  UNSET(-1)
  {
    @Override
    public String toString()
    {
      return "U";
    }
  },
  
  @SerializedName("1")
  MALE(1)
  {
    @Override
    public String toString()
    {
      return "M";
    }
  },
  
  @SerializedName("2")
  FEMALE(2)
  {
    @Override
    public String toString()
    {
      return "F";
    }
  };
  
  private int value;
  private static Map<Integer, Sex> map = new HashMap<>();
  
  private Sex(int value)
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
    for(Sex sex : Sex.values())
    {
      Sex.map.put(sex.value, sex);
    }
  }
  
  /**
   * Return an Enumeration that matches an integer code
   * 
   * @param sexCode
   * @return the enumerated value matching the integer code
   * @throws EnumNotFoundException if the integer value is not in the map
   */
  public static Sex valueOf(int sexCode) throws EnumNotFoundException
  {
    Sex retVal = Sex.map.get(sexCode);
    if(retVal != null)
    {
      return retVal;
    }
    else
    {
      throw new EnumNotFoundException(sexCode, Sex.class);
    }
  }
  
  public abstract String toString();
  
}
