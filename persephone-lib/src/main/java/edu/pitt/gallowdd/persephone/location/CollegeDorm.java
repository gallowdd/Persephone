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

import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;
import edu.pitt.gallowdd.persephone.event.LocationEvent;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author David Galloway
 *
 */
public class CollegeDorm extends GenericLocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.COLLEGE_DORM.toString();
  
  private String stateFipsCode = null;
  private String countyFipsCode = null;
  
  /**
   * Constructor for creating new CollegeDorm where the ID is not known
   * 
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm's longitude
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(double latitude, double longitude) throws IdException
  {
    super(Id.create(CollegeDorm.ID_PREPEND).getIdString(), latitude, longitude);
  }
  
  /**
   * Constructor for creating new CollegeDorm where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the CollegeDorm's id as a String
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm' longitude
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(String idString, double latitude, double longitude) throws IdException
  {
    super(idString, latitude, longitude);
  }
  
  /**
   * Constructor for creating new CollegeDorm where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the CollegeDorm's id as a String
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm's longitude
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(String idString, double latitude, double longitude, GenericIdMixingContainer mixingContainer) throws IdException
  {
    super(idString, latitude, longitude, mixingContainer);
  }
  
  /**
   * Constructor for creating new CollegeDorm where the ID is not known
   * 
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm's longitude
   * @param elevation the CollegeDorm's elevation
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(double latitude, double longitude, double elevation) throws IdException
  {
    super(Id.create(CollegeDorm.ID_PREPEND).getIdString(), latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new CollegeDorm where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the CollegeDorm's id as a String
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm's longitude
   * @param elevation the CollegeDorm's elevation
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(String idString, double latitude, double longitude, double elevation) throws IdException
  {
    super(idString, latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new CollegeDorm where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the CollegeDorm's id as a String
   * @param latitude the CollegeDorm's latitude
   * @param longitude the CollegeDorm's longitude
   * @param elevation the CollegeDorm's elevation
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected CollegeDorm(String idString, double latitude, double longitude, double elevation, GenericIdMixingContainer mixingContainer) throws IdException
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
  
  public void createSublocations()
  {
    
  }
  
  @Override
  public LocationTypeEnum getLocationType()
  {
    return LocationTypeEnum.COLLEGE_DORM;
  }

  @Override
  public void onSubscribe(@NonNull Disposable d)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onNext(@NonNull LocationEvent t)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onError(@NonNull Throwable e)
  {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void onComplete()
  {
    // TODO Auto-generated method stub
    
  }
}
