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
package edu.pitt.gallowdd.persephone.location;

import edu.pitt.gallowdd.persephone.container.IdConnectable;
import edu.pitt.gallowdd.persephone.event.MixEvent;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class Household extends GenericLocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.HOUSEHOLD.toString();
  private String stateFipsCode = null;
  private String countyFipsCode = null;
  private String tractFipsCode = null;
  private double householdIncome = Constants.DBL_UNSET;
  
  /**
   * Constructor for creating new Household where the ID is not known
   * 
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @throws IdException if the id is invalid
   */
  protected Household(double latitude, double longitude) throws IdException
  {
    super(Id.create(Household.ID_PREPEND).getIdString(), latitude, longitude);
  }
  
  /**
   * Constructor for creating new Household where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Household's id as a String
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @throws IdException if the id is invalid
   */
  protected Household(String idString, double latitude, double longitude) throws IdException
  {
    super(idString, latitude, longitude);
  }
  
  /**
   * Constructor for creating new Household where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Household's id as a String
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected Household(String idString, double latitude, double longitude, IdConnectable mixingContainer) throws IdException
  {
    super(idString, latitude, longitude, mixingContainer);
  }
  
  /**
   * Constructor for creating new Household where the ID is not known
   * 
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @param elevation the Household's elevation
   * @throws IdException if the id is invalid
   */
  protected Household(double latitude, double longitude, double elevation) throws IdException
  {
    super(Id.create(Household.ID_PREPEND).getIdString(), latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new Household where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Household's id as a String
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @param elevation the Household's elevation
   * @throws IdException if the id is invalid
   */
  protected Household(String idString, double latitude, double longitude, double elevation) throws IdException
  {
    super(idString, latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new Household where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Household's id as a String
   * @param latitude the Household's latitude
   * @param longitude the Household's longitude
   * @param elevation the Household's elevation
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected Household(String idString, double latitude, double longitude, double elevation, IdConnectable mixingContainer) throws IdException
  {
    super(idString, latitude, longitude, elevation, mixingContainer);
  }
  
  /**
   * @return the stateFipsCode
   */
  public String getStateFipsCode()
  {
    return this.stateFipsCode;
  }
  
  /**
   * @param stateFipsCode the stateFipsCode to set
   */
  public void setStateFipsCode(String stateFipsCode)
  {
    this.stateFipsCode = stateFipsCode;
  }
  
  /**
   * @return the countyFipsCode
   */
  public String getCountyFipsCode()
  {
    return this.countyFipsCode;
  }
  
  /**
   * @param countyFipsCode the countyFipsCode to set
   */
  public void setCountyFipsCode(String countyFipsCode)
  {
    this.countyFipsCode = countyFipsCode;
  }
  
  /**
   * @return the tractFipsCode
   */
  public String getTractFipsCode()
  {
    return this.tractFipsCode;
  }
  
  /**
   * @param tractFipsCode the tractFipsCode to set
   */
  public void setTractFipsCode(String tractFipsCode)
  {
    this.tractFipsCode = tractFipsCode;
  }
  
  /**
   * @return the householdIncome
   */
  public double getHouseholdIncome()
  {
    return this.householdIncome;
  }
  
  /**
   * @param householdIncome the householdIncome to set
   */
  public void setHouseholdIncome(double householdIncome)
  {
    this.householdIncome = householdIncome;
  }
  
  @Override
  public LocationTypeEnum getLocationType()
  {
    return LocationTypeEnum.HOUSEHOLD;
  }

  @Override
  public void handleMixEvent(MixEvent mixEvent)
  {
    // TODO Auto-generated method stub
    
  }
}
