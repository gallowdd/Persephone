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

package edu.pitt.gallowdd.persephone.container;

/**
 * An enumeration of all Mixing Role Types in the application
 *  
 * @author David Galloway
 */
public enum MixingRoleTypeEnum
{
  
  /**
   * Someone who visits a location and leaves after a short time
   */
  VISITOR,
  
  /**
   * Someone who lives at a location
   */
  RESIDENT,
  
  /**
   * Someone who lives at a location, but maintains a residence somewhere else
   */
  TEMPORARY_RESIDENT,
  
  /**
   * Someone who works at a location, but who also interfaces with visitors
   */
  EMPLOYEE_PUBLIC_FACING,
  
  /**
   * Someone who works at a location
   */
  EMPLOYEE,
  
  /**
   * Someone who is Student at a location
   */
  STUDENT,
  
  /**
   * Someone who teaches at a location
   */
  TEACHER,
  
  /**
   * Someone who interacts with patients (e.g. Doctor, Nurse, Nurse's Aide, Etc) at a location
   */
  HEALTH_CARE_WORKER,
  
  /**
   * Catch-all for any agent at a location
   */
  ANY,
  
}
