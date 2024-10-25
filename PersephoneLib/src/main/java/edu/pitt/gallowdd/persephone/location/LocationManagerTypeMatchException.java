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

import edu.pitt.gallowdd.persephone.PersephoneException;

/**
 * The exception class that will be thrown if we add a Location type to a LocationManager that doesn't match the type expected.
 * I.e. Location Managers only handle the LocationType that they are assigned during the Constructor
 *
 * @author David Galloway
 */
public class LocationManagerTypeMatchException extends PersephoneException {

  private static final long serialVersionUID = 1L;

  /**
   * Default Constructor
   * @param mgrId the ID of the Location Manager
   * @param locationTypeStr they String representation of the LocationType
   */
  public LocationManagerTypeMatchException(String mgrId, String locationTypeStr)
  {
    super(ErrorCode.ERR_LOC_MGR_TYPE_MATCH.message(mgrId, locationTypeStr));
  }
}
