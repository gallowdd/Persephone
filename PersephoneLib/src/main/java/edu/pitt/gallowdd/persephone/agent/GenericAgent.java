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

package edu.pitt.gallowdd.persephone.agent;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public abstract class GenericAgent implements Comparable<GenericAgent> {
  
  private final Id id;
  private final Map<String, Id> assignedLocationMap = new ConcurrentHashMap<>();
  private final Map<String, String> dynamicAttributeMap = new ConcurrentHashMap<>();
  
  /**
   * @param idString The agent's id in String form
   * @throws IdException if the idString is invalid
   */
  public GenericAgent(String idString) throws IdException
  {
    super();
    this.id = new Id(idString);
  }
  
  /**
   * @return the id
   */
  public Id getId() 
  {
    return this.id;
  }
  
  /**
   * 
   * @param locationType
   * @return the Id of the locationType assigned to this agent (or null if not found)
   */
  protected Id getAssignedLocation(String locationType)
  {
    return this.assignedLocationMap.get(locationType);
  }
  
  /**
   * 
   * @param locationType
   * @param locationId
   */
  protected void addAssignedLocation(String locationType, Id locationId)
  {
    this.assignedLocationMap.put(locationType, locationId);
  }
  
  /**
   * 
   * @param locationType
   * @return the previous LocationId assigned to the locationType for this agent (or null if not found)
   */
  protected Id removeAssignedLocation(String locationType)
  {
    return this.assignedLocationMap.remove(locationType);
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() throws CloneNotSupportedException
  {
    throw new CloneNotSupportedException("Can't clone the abstract type, GenericAgent");
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
    GenericAgent other = (GenericAgent)obj;
    
    return new EqualsBuilder()
      .append(this.id, other.id)
      .isEquals();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(GenericAgent o)
  {
    return new CompareToBuilder()
      .append(this.id, o.id)
      .toComparison();
  }
}
