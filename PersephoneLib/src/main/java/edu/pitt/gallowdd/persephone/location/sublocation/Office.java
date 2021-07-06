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

package edu.pitt.gallowdd.persephone.location.sublocation;

import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.location.Workplace;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class Office extends GenericSublocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.OFFICE.toString();
  
  /**
   * Constructor for creating new Office where the ID is not known
   * 
   * @param containingWorkplace the Workplace that holds this Office
   * @throws IllegalArgumentException if containingWorkplace is null
   *     (i.e. an Office can't exist on its own. It must be contained in a Workplace)
   * @throws IdException if the id is not valid
   */
  protected Office(Workplace containingWorkplace) throws IllegalArgumentException, IdException
  {
    super(Id.create(Office.ID_PREPEND).getIdString(), containingWorkplace.getId());
  }
  
  /**
   * Constructor for creating new Office where the ID String is known
   *  i.e. the Id String will be read from a file or some outside source
   * 
   * @param idString the Office's idString
   * @param containingWorkplace the Workplace that holds this Office
   * @throws IllegalArgumentException if containingWorkplace is null 
   *     (i.e. an Office can't exist on its own. It must be contained in a Workplace)
   * @throws IdException if the id is not valid
   */
  protected Office(String idString, Workplace containingWorkplace) throws IllegalArgumentException, IdException
  {
    super(idString, containingWorkplace.getId());
  }
}
