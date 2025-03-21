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

package edu.pitt.gallowdd.persephone.event;

import edu.pitt.gallowdd.persephone.util.Id;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author David Galloway
 *
 */
public class AgentEvent extends Event implements Comparable<AgentEvent> {
  
  private static final Logger LOGGER = LogManager.getLogger(AgentEvent.class.getName());
  
  protected final Id agentId;
  private final AgentEventTypeEnum type;
  
  /**
   * 
   * @param agentId
   * @param type 
   * @param simDay the simulation day that this event should occur
   * @param simHour the hour of the simulation day that this event should occur
   */
  public AgentEvent(Id agentId, AgentEventTypeEnum type, int simDay, int simHour)
  {
    super(simDay, simHour);
    this.agentId = agentId;
    this.type = type;
    this.isRepeating = false;
  }
  
  /**
   * 
   * @return The ID of the Agent impacted by this Event
   */
  public Id getAgentId()
  {
    return this.agentId;
  }
  
  /**
   * 
   * @return The AgentEventType of this Event
   */
  public AgentEventTypeEnum getType()
  {
    return this.type;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return new HashCodeBuilder()
      .append(this.simDay)
      .append(this.simHour)
      .append(this.type)
      .append(this.isRepeating)
      .toHashCode();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(java.lang.Object)
   */
  @Override
  public boolean equals(Object obj)
  {
    if(this == obj) 
    {
      return true;
    }
    if(obj == null || this.getClass() != obj.getClass()) 
    {
      return false;
    }
    AgentEvent other = (AgentEvent)obj;
    
    return new EqualsBuilder()
      .append(this.simDay, other.simDay)
      .append(this.simHour, other.simHour)
      .append(this.type, other.type)
      .append(this.isRepeating, other.isRepeating)
      .isEquals();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(AgentEvent o)
  {
    return new CompareToBuilder()
      .append(this.simDay, o.simDay)
      .append(this.simHour, o.simHour)
      .append(this.type, o.type)
      .toComparison();
  }
  
  /**
   * An enumeration of all Agent Event Types in the application
   *  
   * @author David Galloway
   */
  @SuppressWarnings("javadoc")
  public enum AgentEventTypeEnum
  {

    /*
     * Agent Events
     */
    AGENT_DEATH_EVENT("DEATH"),
    AGENT_GIVE_BIRTH_EVENT("GIVE-BIRTH"),
    AGENT_STATE_TRANSITION_EVENT("STATE-TRANSITION"),;
    
    private final String label;
    
    private AgentEventTypeEnum(String label)
    {
      this.label = "AGT-EVNT->" + label;
    }
    
    @Override
    public String toString()
    {
      return this.label;
    }
  }
  
}
