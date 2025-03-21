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

package edu.pitt.gallowdd.persephone.util;

import edu.pitt.gallowdd.persephone.PersephoneException;

/**
 * @author David Galloway
 *
 */
public class EnumNotFoundException extends PersephoneException {
  
  private static final long serialVersionUID = 1;
  
  /**
   * 
   * @param value the value that was looked up
   * @param inClass the Class that tried to interpret the message
   */
  public EnumNotFoundException(int value, Class<?> inClass)
  {
    super(ErrorCode.ERR_ENUM_NOT_FND.message(String.valueOf(value), inClass.getName()));
  }
  
  /**
   * 
   * @param value the value that was looked up
   * @param inClass the Class that tried to interpret the message
   */
  public EnumNotFoundException(String value, Class<?> inClass)
  {
    super(ErrorCode.ERR_ENUM_NOT_FND.message(value, inClass.getName()));
  }
}