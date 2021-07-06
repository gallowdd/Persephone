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

import edu.pitt.gallowdd.persephone.container.DirectedWeightedGraphIdContainer;
import edu.pitt.gallowdd.persephone.util.Id;

/**
 * 
 * @author David Galloway
 *
 */
public class InfluenceNetwork extends DirectedWeightedNetwork {
  
  /**
   * 
   * @param id
   */
  public InfluenceNetwork(Id id)
  {
    super(id, new DirectedWeightedGraphIdContainer());
  }

//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.network.GenericNetwork#applyActionToOthers(com.epistemix.persephone.util.Id)
//   */
//  @Override
//  public void applyActionToOthers(Id agentId)
//  {
//    this.getDirectedWeightedGraphIdContainer().getConnected(agentId, 10);
//    
//  }
//
//  /* (non-Javadoc)
//   * @see com.epistemix.persephone.network.GenericNetwork#receiveActionFromOthers(com.epistemix.persephone.util.Id)
//   */
//  @Override
//  public void receiveActionFromOthers(Id agentId)
//  {
//    // TODO Auto-generated method stub
//    
//  }
  
  
  
}
