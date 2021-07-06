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
public class MessageFactory {
  
  /**
   * @param type the MessageType requested
   * @param senderId 
   * @return A specific Message of the MessageType requested
   */
  public static Message getMessageInstance(Message.MessageType type, Id senderId)
  {
    switch(type)
    {
      case CNTRLLR_INIT_IN:
        return new ControllerInitInMessage(senderId);
      case CNTRLLR_INIT_OUT:
        return new ControllerInitOutMessage(senderId);
      case CNTRLLR_INIT_NRMLZ_IN:
        return new ControllerNormalizeInMessage(senderId);
      case CNTRLLR_INIT_NRMLZ_OUT:
//        return new ControllerNormalizeOutMessage();
        break;
      case CNTRLLR_TIMESTEP_IN:
        break;
      case CNTRLLR_TIMESTEP_OUT:
        break;
      case FRWD_CHNG_CNTRLLR:
        break;
      case ORIG_CHNG_CNTRLLR:
        break;
      case UNKNOWN:
        break;
      default:
        break;
    }
    return null;
  }
}
