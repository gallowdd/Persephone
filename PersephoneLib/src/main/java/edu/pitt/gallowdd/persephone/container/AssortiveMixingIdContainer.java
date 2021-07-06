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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * <p>The interface to allow for selecting agents connected to other agents in an assortative way</p>
 * <p>From <a href="https://en.wikipedia.org/wiki/Assortative_mixing">wikipedia</a>:<br>
 * In the study of complex networks, assortative mixing, or assortativity, is a bias in favor of connections between 
 * network nodes with similar characteristics. In the specific case of social networks, assortative mixing is also 
 * known as homophily.</p>
 * 
 * @author David Galloway
 */
public class AssortiveMixingIdContainer implements IdConnectable 
{
  private final Set<String> idSet = new HashSet<>();
  private final Map<String, Double> attributeWeightMap = new HashMap<>();
  @Override
  public void add(Id id)
  {
    // TODO Auto-generated method stub
    
  }
  @Override
  public boolean remove(String idString) throws IdException
  {
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public boolean remove(Id id)
  {
    // TODO Auto-generated method stub
    return false;
  }
  @Override
  public void clear()
  {
    // TODO Auto-generated method stub
    
  }
  @Override
  public Id getRandom()
  {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public Id getConnected(String idString)
  {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public Id getConnected(Id id)
  {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public List<Id> getConnected(String idString, int howMany)
  {
    // TODO Auto-generated method stub
    return null;
  }
  @Override
  public List<Id> getConnected(Id id, int howMany)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  
  
  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConectable#add(String)
//   */
//  @Override
//  public void add(String id)
//  {
//    this.idSet.add(id);
//  }
//  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConectable#remove(String)
//   */
//  @Override
//  public boolean remove(String id)
//  {
//    return this.idSet.remove(id);
//  }
//  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConectable#clear()
//   */
//  @Override
//  public void clear()
//  {
//    this.idSet.clear();
//  }
//  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConectable#getRandom()
//   */
//  @Override
//  public String getRandom()
//  {
//    if(this.idSet.size() == 0)
//    {
//      
//    }
//    List<String> asList = new ArrayList<>(this.idSet);
//    Collections.shuffle(asList, Utils.getRandomNumberGenerator());
//    return asList.get(0);
//  }
//  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConectable#getConnected(String)
//   */
//  @Override
//  public String getConnected(String inElement)
//  {
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//  }
//  
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.container.IdStringConnectable#getConnected(String, int)
//   */
//  @Override
//  public ArrayList<String> getConnected(String inElement, int howMany)
//  {
//    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//  }
  
}