/*
 * Copyright (c) 2018, University of Pittsburgh
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the 
 * following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following
 *    disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the 
 *    following disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote 
 *    products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE 
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package edu.pitt.gallowdd.persephone.network;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.container.DirectedWeightedGraphIdContainer;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public abstract class DirectedWeightedNetwork extends GenericNetwork {
  
  private static final Logger LOGGER = LogManager.getLogger(DirectedWeightedNetwork.class.getName());
  
  /**
   * 
   * @param id
   */
  public DirectedWeightedNetwork(Id id)
  {
    super(id, new DirectedWeightedGraphIdContainer());
  }
  
  /**
   * 
   * @param id
   * @param mixingContainer
   */
  public DirectedWeightedNetwork(Id id, DirectedWeightedGraphIdContainer mixingContainer)
  {
    super(id, mixingContainer);
  }
  
  /**
   * 
   * @param idString
   * @throws IdException if the idString is invalid
   */
  public DirectedWeightedNetwork(String idString) throws IdException
  {
    super(idString, new DirectedWeightedGraphIdContainer());
  }
  
  /**
   * 
   * @param idString
   * @param mixingContainer
   * @throws IdException if idString is invalid
   */
  public DirectedWeightedNetwork(String idString, DirectedWeightedGraphIdContainer mixingContainer) throws IdException
  {
    super(idString, mixingContainer);
  }
  
  /**
   * If this network contains a @code{DirectedWeightedGraphIdContainer} mixing container, then it is returned
   * Otherwise returns @code{null}
   * @return the mixingContainer if it is an instance of @code{DirectedWeightedGraphIdContainer}, @code{null} otherwise
   */
  public DirectedWeightedGraphIdContainer getDirectedWeightedGraphIdContainer()
  {
    if(this.getMixingContainer() instanceof DirectedWeightedGraphIdContainer)
    {
      return (DirectedWeightedGraphIdContainer)super.getMixingContainer();
    }
    
    DirectedWeightedNetwork.LOGGER.debug("The Mixing Container used for this DirectedWeightedNetwork is not an instance of DirectedWeightedGraphIdContainer");
    return null;
  }
}
