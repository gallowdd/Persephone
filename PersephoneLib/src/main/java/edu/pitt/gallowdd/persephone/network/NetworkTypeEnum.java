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

package edu.pitt.gallowdd.persephone.network;

/**
 * An enumeration of all Network Types in the application
 *  (Along with the common string representation used in the csv files)
 *  
 * @author David Galloway
 */
public enum NetworkTypeEnum
{
  
  @SuppressWarnings("javadoc")
  DIRECTED_WEIGHTED_NETWORK
  {
    @Override
    public String toString()
    {
      return "NTWKDW";
    }
  },
  @SuppressWarnings("javadoc")
  UNDIRECTED_WEIGHTED_NETWORK
  {
    @Override
    public String toString()
    {
      return "NTWKUW";
    }
  },
  @SuppressWarnings("javadoc")
  DIRECTED_NETWORK
  {
    @Override
    public String toString()
    {
      return "NTWKD";
    }
  },
  @SuppressWarnings("javadoc")
  UNDIRECTED_NETWORK
  {
    @Override
    public String toString()
    {
      return "NTWKU";
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
