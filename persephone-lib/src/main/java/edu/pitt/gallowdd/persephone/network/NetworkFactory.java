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
 * @author David Galloway
 *
 */
public class NetworkFactory extends Object {
  
  /**
   * 
   * @param type
   * @param idString
   * @param edgeCount 
   * @param nodeCount 
   * 
   * @return a new Network of the specified NetworkType
   */
  public static GenericNetwork createNetwork(NetworkTypeEnum type, String idString, int edgeCount, int nodeCount)
  {
    GenericNetwork retVal = null;
    switch(type)
    {
      case DIRECTED_NETWORK:
        break;
      case DIRECTED_WEIGHTED_NETWORK:
        break;
      case UNDIRECTED_NETWORK:
        break;
      case UNDIRECTED_WEIGHTED_NETWORK:
        break;
      case NULL_TYPE:
        retVal = GenericNetwork.NULL_NETWORK;
        break;
      default:
        break;
    }
    
    return retVal;
  }
  
  /**
   * 
   * @param type
   * 
   * @return a new Network of the specified NetworkType
   */
  public static GenericNetwork createNetwork(NetworkTypeEnum type)
  {
    GenericNetwork retVal = null;
    switch(type)
    {
      case DIRECTED_NETWORK:
        break;
      case DIRECTED_WEIGHTED_NETWORK:
        break;
      case UNDIRECTED_NETWORK:
        break;
      case UNDIRECTED_WEIGHTED_NETWORK:
        break;
      case NULL_TYPE:
        retVal = GenericNetwork.NULL_NETWORK;
        break;
      default:
        break;
    }
    
    return retVal;
  }
}
