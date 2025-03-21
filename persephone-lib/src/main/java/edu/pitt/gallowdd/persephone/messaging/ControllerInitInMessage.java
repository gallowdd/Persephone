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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import edu.pitt.gallowdd.persephone.messaging.record.SynthEnvRec;
import edu.pitt.gallowdd.persephone.util.Id;

/**
 * 
 * @author David Galloway
 *
 */
public class ControllerInitInMessage extends Message {
  
  private List<SynthEnvRec> syntheticEnvironments = null;
  private LocalDateTime simDateTime = null;
  
  /**
   * Default Constructor creates a ControllerInitMessage and sets the Type Correctly
   */
  protected ControllerInitInMessage()
  {
    super(Message.MessageType.CNTRLLR_INIT_IN);
  }
  
  /**
   * 
   * @param senderId
   */
  protected ControllerInitInMessage(Id senderId)
  {
    super(Message.MessageType.CNTRLLR_INIT_IN, senderId);
  }
  
  /**
   * @return the List of syntheticEnvironment Plain Old Java Objects
   */
  public List<SynthEnvRec> getSyntheticEnvironments()
  {
    return this.syntheticEnvironments;
  }
  
  /**
   * @param syntheticEnvironments the syntheticEnvironmentIdentifier Plain Old Java Objects to set
   */
  public void setSyntheticEnvironmentIdentifiers(ArrayList<SynthEnvRec> syntheticEnvironments)
  {
    this.syntheticEnvironments = syntheticEnvironments;
  }
  
  /**
   * @param syntheticEnvironment the syntheticEnvironment Plain Old Java Object to add to the List
   * @return true (as specified by Collection.add)
   */
  public boolean addSyntheticEnvironment(SynthEnvRec syntheticEnvironment)
  {
    if(this.syntheticEnvironments == null)
    {
      this.syntheticEnvironments = new ArrayList<>();
    }
    return this.syntheticEnvironments.add(syntheticEnvironment);
  }
  
  /**
   * @return the simDateTime
   */
  public LocalDateTime getSimDateTime()
  {
    return this.simDateTime;
  }
  
  /**
   * @param simDateTime the simDateTime to set
   */
  public void setSimDateTime(LocalDateTime simDateTime)
  {
    this.simDateTime = simDateTime;
  }
}
