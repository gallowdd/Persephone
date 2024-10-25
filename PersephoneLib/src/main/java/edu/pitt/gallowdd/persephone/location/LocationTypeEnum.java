/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2022  David Galloway / University of Pittsburgh
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

/**
 * An enumeration of all Location Types in the application
 *  (Along with the common string representation used in the csv files)
 *  
 * @author David Galloway
 */
public enum LocationTypeEnum
{
  
  @SuppressWarnings("javadoc")
  CLASSROOM
  {
    @Override
    public String toString()
    {
      return "CLSSRM";
    }
  },
  @SuppressWarnings("javadoc")
  HOUSEHOLD
  {
    @Override
    public String toString()
    {
      return "HH";
    }
  },
  @SuppressWarnings("javadoc")
  GRID
  {
    @Override
    public String toString()
    {
      return "GRD";
    }
  },
  @SuppressWarnings("javadoc")
  OFFICE
  {
    @Override
    public String toString()
    {
      return "OFFC";
    }
  },
  @SuppressWarnings("javadoc")
  SCHOOL
  {
    @Override
    public String toString()
    {
      return "SCL";
    }
  },
  @SuppressWarnings("javadoc")
  WORKPLACE
  {
    @Override
    public String toString()
    {
      return "WP";
    }
  },
  @SuppressWarnings("javadoc")
  NURSING_FACILITY
  {
    @Override
    public String toString()
    {
      return "NRS";
    }
  },
  @SuppressWarnings("javadoc")
  MILITARY_BARRACKS
  {
    @Override
    public String toString()
    {
      return "MIL";
    }
  },
  @SuppressWarnings("javadoc")
  COLLEGE_DORM
  {
    @Override
    public String toString()
    {
      return "CLG";
    }
  },
  @SuppressWarnings("javadoc")
  COLLEGE_DORM_ROOM
  {
    @Override
    public String toString()
    {
      return "DRMRM";
    }
  },
  @SuppressWarnings("javadoc")
  PRISON
  {
    @Override
    public String toString()
    {
      return "PRS";
    }
  },
  @SuppressWarnings("javadoc")
  PRISON_CELL
  {
    @Override
    public String toString()
    {
      return "CELL";
    }
  },
  @SuppressWarnings("javadoc")
  NULL_TYPE
  {
    @Override
    public String toString()
    {
      return "NULL";
    }
  };
  
  public abstract String toString();
  
}
