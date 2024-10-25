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
import edu.pitt.gallowdd.persephone.event.MixEvent;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class Workplace extends GenericLocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.WORKPLACE.toString();
  
  /**
   * Constructor for creating new Workplace where the ID is not known
   * 
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @throws IdException if the id is invalid
   */
  protected Workplace(double latitude, double longitude) throws IdException
  {
    super(Id.create(Workplace.ID_PREPEND).getIdString(), latitude, longitude);
  }
  
  /**
   * Constructor for creating new Workplace where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Workplace's id as a String
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @throws IdException if the id is invalid
   */
  protected Workplace(String idString, double latitude, double longitude) throws IdException
  {
    super(idString, latitude, longitude);
  }
  
  /**
   * Constructor for creating new Workplace where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Workplace's id as a String
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected Workplace(String idString, double latitude, double longitude, GenericIdMixingContainer mixingContainer) throws IdException
  {
    super(idString, latitude, longitude, mixingContainer);
  }
  
  /**
   * Constructor for creating new Workplace where the ID is not known
   * 
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @param elevation the Workplace's elevation
   * @throws IdException if the id is invalid
   */
  protected Workplace(double latitude, double longitude, double elevation) throws IdException
  {
    super(Id.create(Workplace.ID_PREPEND).getIdString(), latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new Workplace where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Workplace's id as a String
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @param elevation the Workplace's elevation
   * @throws IdException if the id is invalid
   */
  protected Workplace(String idString, double latitude, double longitude, double elevation) throws IdException
  {
    super(idString, latitude, longitude, elevation);
  }
  
  /**
   * Constructor for creating new Workplace where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   *  
   * @param idString the Workplace's id as a String
   * @param latitude the Workplace's latitude
   * @param longitude the Workplace's longitude
   * @param elevation the Workplace's elevation
   * @param mixingContainer a container for Ids
   * @throws IdException if the id is invalid
   */
  protected Workplace(String idString, double latitude, double longitude, double elevation, GenericIdMixingContainer mixingContainer) throws IdException
  {
    super(idString, latitude, longitude, elevation, mixingContainer);
  }
  
  @Override
  public LocationTypeEnum getLocationType()
  {
    return LocationTypeEnum.WORKPLACE;
  }

  @Override
  public void handleMixEvent(MixEvent mixEvent)
  {
    // TODO Auto-generated method stub
    
  }
}