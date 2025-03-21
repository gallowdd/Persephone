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
package edu.pitt.gallowdd.persephone.controller.administration;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;

import edu.pitt.gallowdd.persephone.util.EnumNotFoundException;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * This class represents a State Administrative Unit
 * 
 * @author David Galloway
 **/
public class State extends AdministrativeUnit{

  /**
   * @param id
   * @throws IdException 
   */
  public State(String id) throws IdException
  {
    super(id);
  }
  
  
  /**
   * All of the state codes and state names in the United States
   * @author David Galloway
   *
   */
  @SuppressWarnings("javadoc")
  public enum StateCode 
  {
    @SerializedName("1")
    ALABAMA(1, "AL", "Alabama"),
    @SerializedName("2")
    ALASKA(2, "AK", "Alaska"),
    @SerializedName("4")
    ARIZONA(4, "AZ", "Arizona"),
    @SerializedName("5")
    ARKANSAS(5, "AR", "Arkansas"),
    @SerializedName("6")
    CALIFORNIA(6, "CA", "California"),
    @SerializedName("8")
    COLORADO(8, "CO", "Colorado"),
    @SerializedName("9")
    CONNECTICUT(9, "CT", "Connecticut"),
    @SerializedName("10")
    DELAWARE(10, "DE", "Delaware"),
    @SerializedName("11")
    DISTRICT_OF_COLUMBIA(11, "DC", "District of Columbia"),
    @SerializedName("12")
    FLORIDA(12, "FL", "Florida"),
    @SerializedName("13")
    GEORGIA(13, "GA", "Georgia"),
    @SerializedName("15")
    HAWAII(15, "HI", "Hawaii"),
    @SerializedName("16")
    IDAHO(16, "ID", "Idaho"),
    @SerializedName("17")
    ILLINOIS(17, "IL", "Illinois"),
    @SerializedName("18")
    INDIANA(18, "IN", "Indiana"),
    @SerializedName("19")
    IOWA(19, "IA", "Iowa"),
    @SerializedName("20")
    KANSAS(20, "KS", "Kansas"),
    @SerializedName("21")
    KENTUCKY(21, "KY", "Kentucky"),
    @SerializedName("22")
    LOUISIANA(22, "LA", "Louisiana"),
    @SerializedName("23")
    MAINE(23, "ME", "Maine"),
    @SerializedName("24")
    MARYLAND(24, "MD", "Maryland"),
    @SerializedName("25")
    MASSACHUSETTS(25, "MA", "Massachusetts"),
    @SerializedName("26")
    MICHIGAN(26, "MI", "Michigan"),
    @SerializedName("27")
    MINNESOTA(27, "MN", "Minnesota"),
    @SerializedName("28")
    MISSISSIPPI(28, "MS", "Mississippi"),
    @SerializedName("29")
    MISSOURI(29, "MO", "Missouri"),
    @SerializedName("30")
    MONTANA(30, "MT", "Montana"),
    @SerializedName("31")
    NEBRASKA(31, "NE", "Nebraska"),
    @SerializedName("32")
    NEVADA(32, "NV", "Nevada"),
    @SerializedName("33")
    NEW_HAMPSHIRE(33, "NH", "New Hampshire"),
    @SerializedName("34")
    NEW_JERSEY(34, "NJ", "New Jersey"),
    @SerializedName("35")
    NEW_MEXICO(35, "NM", "New Mexico"),
    @SerializedName("36")
    NEW_YORK(36, "NY", "New York"),
    @SerializedName("37")
    NORTH_CAROLINA(37, "NC", "North Carolina"),
    @SerializedName("38")
    NORTH_DAKOTA(38, "ND", "North Dakota"),
    @SerializedName("39")
    OHIO(39, "OH", "Ohio"),
    @SerializedName("40")
    OKLAHOMA(40, "OK", "Oklahoma"),
    @SerializedName("41")
    OREGON(41, "OR", "Oregon"),
    @SerializedName("42")
    PENNSYLVANIA(42, "PA", "Pennsylvania"),
    @SerializedName("44")
    RHODE_ISLAND(44, "RI", "Rhode Island"),
    @SerializedName("45")
    SOUTH_CAROLINA(45, "SC", "South Carolina"),
    @SerializedName("46")
    SOUTH_DAKOTA(46, "SD", "South Dakota"),
    @SerializedName("47")
    TENNESSEE(47, "TN", "Tennessee"),
    @SerializedName("48")
    TEXAS(48, "TX", "Texas"),
    @SerializedName("49")
    UTAH(49, "UT", "Utah"),
    @SerializedName("50")
    VERMONT(50, "VT", "Vermont"),
    @SerializedName("51")
    VIRGINIA(51, "VA", "Virginia"),
    @SerializedName("53")
    WASHINGTON(53, "WA", "Washington"),
    @SerializedName("54")
    WEST_VIRGINIA(54, "WV", "West Virginia"),
    @SerializedName("55")
    WISCONSIN(55, "WI", "Wisconsin"),
    @SerializedName("56")
    WYOMING(56, "WY", "Wyoming"),
    @SerializedName("60")
    AMERICAN_SAMOA(60, "AS", "American Samoa"),
    @SerializedName("66")
    GUAM(66, "GU", "Guam"),
    @SerializedName("69")
    NORTHERN_MARIANA_ISLANDS(69, "MP", "Northern Mariana Islands"),
    @SerializedName("72")
    PUERTO_RICO(72, "PR", "Puerto Rico"),
    @SerializedName("74")
    US_MINOR_OUTLYING_ISLANDS(74, "UM", "U.S. Minor Outlying Islands"),
    @SerializedName("78")
    US_VIRGIN_ISLANDS(78, "VI", "U.S. Virgin Islands");
    
    private int value;
    private String abbv;
    private String displayName;
    private static Map<Integer, StateCode> map = new HashMap<>();
    
    private StateCode(int value, String abbv, String displayName)
    {
      this.value = value;
      this.abbv = abbv;
      this.displayName = displayName;
    }
    
    /**
     * Get the numeric value assigned to a State. This two digit number represents the first two digits of a FIPS code
     *
     * @return the State's value
     */
    public int getValue()
    {
      return this.value;
    }
    
    /**
     * 
     * @return the State's abbreviation
     */
    public String getAbbv()
    {
      return this.abbv;
    }
    
    /**
     * 
     * @return the State's display ame
     */
    public String getDisplayName()
    {
      return this.displayName;
    }
    
    static 
    {
      /*
       * Set up the StateCode map that maps an integer value to the StateCode that it represents
       */
      for(StateCode state : StateCode.values())
      {
        StateCode.map.put(state.value, state);
      }
    }
    
    /**
     * 
     * @param stateNumCode
     * @return The State code that has an integer value of stateNumCode
     * @throws EnumNotFoundException if the stateNumCode does not exist in the mapping
     */
    public static StateCode valueOf(int stateNumCode) throws EnumNotFoundException
    {
      StateCode retVal = StateCode.map.get(stateNumCode);
      if(retVal != null)
      {
        return retVal;
      }
      else
      {
        throw new EnumNotFoundException(stateNumCode, StateCode.class);
      }
    }
  }
  
}
