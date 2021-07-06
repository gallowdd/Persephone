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

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import edu.pitt.gallowdd.persephone.util.Id;

/**
 * Abstract Base Class for all Messages
 * 
 * @author David Galloway
 */
public abstract class Message {

  private MessageType type = null;
  private Id senderId = null;
  private Message wrappedMessage = null;
  
  /**
   * 
   * @param type
   */
  protected Message(MessageType type)
  {
    this.type = type;
  }
  
  /**
   * 
   * @param type
   * @param senderId
   */
  protected Message(MessageType type, Id senderId)
  {
    this.type = type;
    this.senderId = senderId;
  }
  
  /**
   * 
   * @param type
   * @param senderId
   * @param wrappedMessage
   */
  protected Message(MessageType type, Id senderId, Message wrappedMessage)
  {
    this.type = type;
    this.senderId = senderId;
    this.wrappedMessage = wrappedMessage;
  }
  
  /**
   * @return the type
   */
  public MessageType getType()
  {
    return this.type;
  }
  
  /**
   * @return the senderId
   */
  public Id getSenderId()
  {
    return this.senderId;
  }
  
  /**
   * @param senderId the senderId to set
   */
  public void setSenderId(Id senderId)
  {
    this.senderId = senderId;
  }
  
  /**
   * @return the wrappedMessage
   */
  public Message getWrappedMessage()
  {
    return this.wrappedMessage;
  }
  
  /**
   * @param wrappedMessage the wrappedMessage to set
   */
  public void setWrappedMessage(Message wrappedMessage)
  {
    this.wrappedMessage = wrappedMessage;
  }
  
  /**
   * 
   * @return the JSON string representation of this message
   */
  public String toJSONString()
  {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("type", this.type)
        .append("senderId", this.senderId.toJsonString())
        .append("wrappedMessage", this.wrappedMessage)
        .toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return new ToStringBuilder(this)
        .append("type", this.type)
        .append("senderId", this.senderId.toString())
        .append("wrappedMessage", this.wrappedMessage)
        .toString();
  }
  
  @SuppressWarnings("javadoc")
  public enum MessageType
  {
    UNKNOWN,
    CNTRLLR_INIT_IN,
    CNTRLLR_INIT_OUT,
    CNTRLLR_INIT_NRMLZ_IN,
    CNTRLLR_INIT_NRMLZ_OUT,
    CNTRLLR_TIMESTEP_IN,
    CNTRLLR_TIMESTEP_OUT,
    ORIG_CHNG_CNTRLLR,
    FRWD_CHNG_CNTRLLR
  }
}
