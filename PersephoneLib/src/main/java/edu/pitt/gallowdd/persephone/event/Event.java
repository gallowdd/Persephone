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

import edu.pitt.gallowdd.persephone.agent.GenericAgent;
import edu.pitt.gallowdd.persephone.controller.Controller;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.Params;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author David Galloway
 *
 */
public abstract class Event implements Comparable<Event> {
  
  private static final Logger LOGGER = LogManager.getLogger(Event.class.getName());
  
  private final int simDay;
  private final int simHour;
  private final boolean isRepeating;
  private final EventTypeEnum type;
  
  /**
   * @param simDay  the simulation day that this event should occur
   * @param simHour  the hour of the simulation day that this event should occur
   */
  public Event(EventTypeEnum type, int simDay, int simHour)
  {
    super();
    if(simDay >= Params.getDays())
    {
      Event.LOGGER.fatal("simDay must be between 0 and less the the requested number of simDays; tried to it to " + simDay);
      throw new RuntimeException("Invalid parameter value for Event Constructor");
    }
    else if(simHour < 0 || simHour > 23) 
    {
      Event.LOGGER.fatal("simHour must be between 0 and 23 inclusive; tried to set it to " + simHour);
      throw new RuntimeException("Invalid parameter value for Event Constructor");
    }
    else
    {
      this.simDay = simDay;
      this.simHour = simHour;
    }
    
    this.type = type;
    this.isRepeating = false;
  }
  
  /**
   * 
   * @return The EventType of the Event
   */
  public EventTypeEnum getType()
  {
    return this.type;
  }
  
  /**
   * 
   * @return the simDay of the event
   */
  public int getSimDay()
  {
    return this.simDay;
  }
  
  /**
   * 
   * @return the simHour of the event
   */
  public int getSimHour()
  {
    return this.simHour;
  }
  
  
  
  
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return new HashCodeBuilder()
      .append(this.id)
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
    Event other = (Event)obj;
    
    return new EqualsBuilder()
      .append(this.id, other.id)
      .isEquals();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Event o)
  {
    return new CompareToBuilder()
      .append(this.id, o.id)
      .toComparison();
  }
}
