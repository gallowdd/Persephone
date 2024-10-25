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

import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;
import edu.pitt.gallowdd.persephone.location.CollegeDorm;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class CollegeDormRoom extends GenericSublocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.COLLEGE_DORM_ROOM.toString();
  
  /**
   * Constructor for creating new CollegeDormRoom where the ID is not known
   * 
   * @param containingDorm the CollegeDorm that holds this CollegeDormRoom
   * @throws IllegalArgumentException if containingDorm is null (i.e. a CollegeDormRoom can't exist on its own. It must be contained in a CollegeDorm)
   * @throws IdException if the id is not valid
   */
  protected CollegeDormRoom(CollegeDorm containingDorm) throws IllegalArgumentException, IdException
  {
    super(Id.create(CollegeDormRoom.ID_PREPEND).getIdString(), containingDorm.getId());
  }
  
  /**
   * Constructor for creating new CollegeDormRoom where the ID is not known
   * 
   * @param containingDorm the CollegeDorm that holds this CollegeDormRoom
   * @param mixingContainer the specific Mixing Container to be used for this Classroom
   * @throws IllegalArgumentException if containingDorm is null (i.e. a CollegeDormRoom can't exist on its own. It must be contained in a CollegeDorm)
   * @throws IdException if the id is not valid
   */
  protected CollegeDormRoom(CollegeDorm containingDorm, GenericIdMixingContainer mixingContainer) throws IllegalArgumentException, IdException
  {
    super(Id.create(CollegeDormRoom.ID_PREPEND).getIdString(), containingDorm.getId(), mixingContainer);
  }
  
  /**
   * Constructor for creating new CollegeDormRoom where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   * 
   * @param id if the id is known
   * @param containingDorm the CollegeDorm that holds this CollegeDormRoom
   * @throws IllegalArgumentException if containingDorm is null (i.e. a CollegeDormRoom can't exist on its own. It must be contained in a CollegeDorm)
   * @throws IdException if the id is not valid
   */
  protected CollegeDormRoom(String id, CollegeDorm containingDorm) throws IllegalArgumentException, IdException
  {
    super(id, containingDorm.getId());
  }
  
  /**
   * Constructor for creating new CollegeDormRoom where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   * 
   * @param id if the id is known
   * @param containingDorm the CollegeDorm that holds this CollegeDormRoom
   * @param mixingContainer the specific Mixing Container to be used for this CollegeDormRoom
   * @throws IllegalArgumentException if containingSchool is null (i.e. a CollegeDormRoom can't exist on its own. It must be contained in a CollegeDorm)
   * @throws IdException if the id is not valid
   */
  protected CollegeDormRoom(String id, CollegeDorm containingDorm, GenericIdMixingContainer mixingContainer) throws IllegalArgumentException, IdException
  {
    super(id, containingDorm.getId(), mixingContainer);
  }
}
