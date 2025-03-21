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

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author David Galloway
 *
 */
public class SimulationEvent extends Event implements Comparable<SimulationEvent> {
  
  private static final Logger LOGGER = LogManager.getLogger(SimulationEvent.class.getName());
  
  private final SimulationEventTypeEnum type;
  
  /**
   * 
   * @param type 
   * @param simDay the simulation day that this event should occur
   * @param simHour the hour of the simulation day that this event should occur
   */
  public SimulationEvent(SimulationEventTypeEnum type, int simDay, int simHour)
  {
    super(simDay, simHour);
    this.type = type;
    this.isRepeating = false;
  }
  
  /**
   * 
   * @return The SimulationEventTypeEnum of this Event
   */
  public SimulationEventTypeEnum getType()
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
    SimulationEvent other = (SimulationEvent)obj;
    
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
  public int compareTo(SimulationEvent o)
  {
    return new CompareToBuilder()
      .append(this.simDay, o.simDay)
      .append(this.simHour, o.simHour)
      .append(this.type, o.type)
      .toComparison();
  }
  
  /**
   * An enumeration of all Simulation Event Types in the application
   *  
   * @author David Galloway
   */
  @SuppressWarnings("javadoc")
  public enum SimulationEventTypeEnum
  {
    SIMULATION_INITIALIZE("INIT"),;
    
    private final String label;
    
    private SimulationEventTypeEnum(String label)
    {
      this.label = "SIM-EVNT->" + label;
    }
    
    @Override
    public String toString()
    {
      return this.label;
    }
  }
  
}
