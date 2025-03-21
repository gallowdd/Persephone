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
 * After Places and Networks are added to Controllers, we may have duplicates in different Controllers. Normalization messages are sent to each Controller,
 * so it can delete duplicate Location and Network information. (I.e. only one Controller can have any specific location or specific network.)
 * 
 * @author David Galloway
 *
 */
public class ControllerNormalizeInMessage extends Message {
  
  private ControllerNormalizeInType initOutType = ControllerNormalizeInType.UNSET;
  private Id toDeleteLocationId = null;
  private Id toDeleteNetworkId = null;
  
  /**
   * Default Constructor creates a ControllerNormalizeInMessage and sets the Type Correctly
   */
  protected ControllerNormalizeInMessage()
  {
    super(Message.MessageType.CNTRLLR_INIT_NRMLZ_IN);
  }
  
  /**
   * 
   * @param senderId
   */
  protected ControllerNormalizeInMessage(Id senderId)
  {
    super(Message.MessageType.CNTRLLR_INIT_NRMLZ_IN, senderId);
  }
  
  /**
   * @return the location ID to be deleted
   */
  public Id getToDeleteLocationId()
  {
    return this.toDeleteLocationId;
  }
  
  /**
   * @param locationId the location ID to set
   */
  public void setToDeleteLocationId(Id locationId)
  {
    this.toDeleteLocationId = locationId;
  }
  
  /**
   * @return the network ID to be deleted
   */
  public Id getToDeleteNetworkId()
  {
    return this.toDeleteNetworkId;
  }
  
  /**
   * @param networkId the network ID to set
   */
  public void setToDeleteNetworkId(Id networkId)
  {
    this.toDeleteNetworkId = networkId;
  }
  
  /**
   * @return the initOutType
   */
  public ControllerNormalizeInType getNomalizeInType()
  {
    return this.initOutType;
  }
  
  /**
   * @param initOutType the initOut Type to set
   */
  public void setNormalizeInType(ControllerNormalizeInType initOutType)
  {
    this.initOutType = initOutType;
  }
  
  @SuppressWarnings("javadoc")
  public enum ControllerNormalizeInType {
    PLACE_REMOVE,
    NETWORK_REMOVE,
    UNSET
  }
}
