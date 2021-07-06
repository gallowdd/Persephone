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

import edu.pitt.gallowdd.persephone.container.IdConnectable;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.location.School;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class Classroom extends GenericSublocation {
  
  private static final String ID_PREPEND = LocationTypeEnum.CLASSROOM.toString();
  
  /**
   * Constructor for creating new Classroom where the ID is not known
   * 
   * @param containingSchool the School that holds this Classroom
   * @throws IllegalArgumentException if containingSchool is null (i.e. a Classroom can't exist on its own. It must be contained in a School)
   * @throws IdException if the id is not valid
   */
  protected Classroom(School containingSchool) throws IllegalArgumentException, IdException
  {
    super(Id.create(Classroom.ID_PREPEND).getIdString(), containingSchool.getId());
  }
  
  /**
   * Constructor for creating new Classroom where the ID is not known
   * 
   * @param containingSchool the School that holds this Classroom
   * @param mixingContainer the specific Mixing Container to be used for this Classroom
   * @throws IllegalArgumentException if containingSchool is null (i.e. a Classroom can't exist on its own. It must be contained in a School)
   * @throws IdException if the id is not valid
   */
  protected Classroom(School containingSchool, IdConnectable mixingContainer) throws IllegalArgumentException, IdException
  {
    super(Id.create(Classroom.ID_PREPEND).getIdString(), containingSchool.getId(), mixingContainer);
  }
  
  /**
   * Constructor for creating new Classroom where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   * 
   * @param id if the id is known
   * @param containingSchool the School that holds this Classroom
   * @throws IllegalArgumentException if containingSchool is null (i.e. a Classroom can't exist on its own. It must be contained in a School)
   * @throws IdException if the id is not valid
   */
  protected Classroom(String id, School containingSchool) throws IllegalArgumentException, IdException
  {
    super(id, containingSchool.getId());
  }
  
  /**
   * Constructor for creating new Classroom where the ID is known
   *  i.e. the ID will be read from a file or some outside source
   * 
   * @param id if the id is known
   * @param containingSchool the School that holds this Classroom
   * @param mixingContainer the specific Mixing Container to be used for this Classroom
   * @throws IllegalArgumentException if containingSchool is null (i.e. a Classroom can't exist on its own. It must be contained in a School)
   * @throws IdException if the id is not valid
   */
  protected Classroom(String id, School containingSchool, IdConnectable mixingContainer) throws IllegalArgumentException, IdException
  {
    super(id, containingSchool.getId(), mixingContainer);
  }
}
