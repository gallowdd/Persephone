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
 * An enumeration of all CensusRelationship Types in the application
 *  (Along with the common string representation used in the csv files)
 *  
 * @author David Galloway
 */
@SuppressWarnings("javadoc")
public enum CensusRelationship
{
  
  @SerializedName("0")
  REFERENCE_PERSON(0),
  
  @SerializedName("1")
  HUSBAND_WIFE(1),
  
  @SerializedName("2")
  SON_DAUGHTER(2),
  
  @SerializedName("3")
  BROTHER_SISTER(3),
  
  @SerializedName("4")
  FATHER_MOTHER(4),
  
  @SerializedName("5")
  GRANDCHILD(5),
  
  @SerializedName("6")
  IN_LAW(6),
  
  @SerializedName("7")
  OTHER(7),
  
  @SerializedName("8")
  ROOMER_BOARDER(8),
  
  @SerializedName("9")
  HOUSEMATE_ROOMMATE(9),
  
  @SerializedName("10")
  UNMARRIED_PARTNER(10),
  
  @SerializedName("11")
  FOSTER_CHILD(11),
  
  @SerializedName("12")
  OTHER_NONRELATIVE(12),
  
  @SerializedName("13")
  INST_GROUP_QUARTER(13),
  
  @SerializedName("14")
  NONINST_GROUP_QUARTER(14),
  
  @SerializedName("-1")
  UNDEFINED(-1);
  
  
  private int value;
  private static Map<Integer, CensusRelationship> map = new HashMap<>();
  
  private CensusRelationship(int value)
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
    for(CensusRelationship relationship : CensusRelationship.values())
    {
      CensusRelationship.map.put(relationship.value, relationship);
    }
  }
  
  /**
   * Return an Enumeration that matches an integer code
   * 
   * @param relationshipCode
   * @return the enumerated value matching the integer code
   * @throws EnumNotFoundException if the integer value is not in the map
   */
  public static CensusRelationship valueOf(int relationshipCode) throws EnumNotFoundException
  {
    CensusRelationship retVal = CensusRelationship.map.get(relationshipCode);
    if(retVal != null)
    {
      return retVal;
    }
    else
    {
      throw new EnumNotFoundException(relationshipCode, CensusRelationship.class);
    }
  }
}
