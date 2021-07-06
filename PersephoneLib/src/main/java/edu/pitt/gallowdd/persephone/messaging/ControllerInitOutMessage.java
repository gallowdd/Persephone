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

package edu.pitt.gallowdd.persephone.messaging;

import edu.pitt.gallowdd.persephone.util.Id;

/**
 * 
 * @author David Galloway
 *
 */
public class ControllerInitOutMessage extends Message {
  
  private ControllerInitOutType initOutType = ControllerInitOutType.UNSET;
  private Id agentId = null;  
  private Id locationId = null;
  private Id networkId = null;
  
  /**
   * Default Constructor creates a ControllerInitMessage and sets the Type Correctly
   */
  protected ControllerInitOutMessage()
  {
    super(Message.MessageType.CNTRLLR_INIT_OUT);
  }
  
  /**
   * 
   * @param senderId
   */
  protected ControllerInitOutMessage(Id senderId)
  {
    super(Message.MessageType.CNTRLLR_INIT_OUT, senderId);
  }
  
  /**
   * @return the initOutType
   */
  public ControllerInitOutType getInitOutType()
  {
    return this.initOutType;
  }
  
  /**
   * @param initOutType the type to set
   */
  public void setInitOutType(ControllerInitOutType initOutType)
  {
    this.initOutType = initOutType;
  }
  
  /**
   * @return the agentId
   */
  public Id getAgentId()
  {
    return this.agentId;
  }
  
  /**
   * @param agentId the agentId to set
   */
  public void setAgentId(Id agentId)
  {
    this.agentId = agentId;
  }
  
  /**
   * @return the locationId
   */
  public Id getLocationId()
  {
    return this.locationId;
  }
  
  /**
   * @param locationId the locationId to set
   */
  public void setLocationId(Id locationId)
  {
    this.locationId = locationId;
  }
  
  /**
   * @return the networkId
   */
  public Id getNetworkId()
  {
    return this.networkId;
  }
  
  /**
   * @param networkId the networkId to set
   */
  public void setNetworkId(Id networkId)
  {
    this.networkId = networkId;
  }
  
  @SuppressWarnings("javadoc")
  public enum ControllerInitOutType {
    AGENT_ADD,
    PLACE_ADD,
    NETWORK_ADD,
    UNSET
  }
}
