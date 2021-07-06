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

package edu.pitt.gallowdd.persephone.agent;

import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class AgentFactory extends Object {
  
  /**
   * 
   * @param type
   * @param id
   * @return an Agent of the Specified Type
   * @throws IdException 
   */
  public static GenericAgent createAgent(AgentTypeEnum type, String id) throws IdException
  {
    GenericAgent retVal;
    switch(type)
    {
      case PERSON:
        retVal =  new Person(id);
        break;
      default:
        retVal = null;
        break;
    }
    
    return retVal;
  }
  
  /**
   * 
   * @param type
   * @return an Agent of the Specified Type
   * @throws IdException 
   */
  public static GenericAgent createAgent(AgentTypeEnum type) throws IdException
  {
    GenericAgent retVal;
    switch(type)
    {
      case PERSON:
        retVal = new Person();
        break;
      default:
        retVal = null;
        break;
    }
    
    return retVal;
  }
  
}
