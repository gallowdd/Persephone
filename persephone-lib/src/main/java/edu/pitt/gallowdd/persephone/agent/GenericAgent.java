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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.event.AgentEvent;
import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

/**
 * @author David Galloway
 *
 */
public abstract class GenericAgent implements Comparable<GenericAgent>, Observer<AgentEvent> {
  
  private static final Logger LOGGER = LogManager.getLogger(GenericAgent.class.getName());
  
  /**
   * The Null Agent that will be used for all cases where a Null is needed
   */
  public static final GenericAgent NULL_AGENT = new GenericAgent(Id.NULL_ID) {
    
    @Override
    public AgentTypeEnum getAgentType()
    {
      return AgentTypeEnum.NULL_TYPE;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d)
    {
      GenericAgent.LOGGER.debug("NULL_AGENT can't observe Agent Location Event Publisher, hence there is no onSubscribe");
    }
    
    @Override
    public void onNext(@NonNull AgentEvent t)
    {
      GenericAgent.LOGGER.debug("NULL_AGENT can't observe a Agent Event Publisher, hence there is no onNext");
    }

    @Override
    public void onComplete()
    {
      GenericAgent.LOGGER.debug("NULL_AGENT can't observe a Agent Event Publisher, hence there is no onComplete");
    }

    @Override
    public void onError(@NonNull Throwable e)
    {
      GenericAgent.LOGGER.debug("NULL_AGENT can't observe a Agent Event Publisher, hence there is no onError");
    }
  };
  
  private final Id id;
  private final Map<LocationTypeEnum, Id> assignedLocationMap = new ConcurrentHashMap<>();
  private final Map<String, String> dynamicAttributeMap = new ConcurrentHashMap<>();
  
  /**
   * @param id The agent's id
   */
  public GenericAgent(Id id)
  {
    super();
    this.id = id;
  }
  
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
  protected Id getAssignedLocation(LocationTypeEnum locationType)
  {
    return this.assignedLocationMap.get(locationType);
  }
  
  /**
   * 
   * @param locationType
   * @param locationId
   */
  protected void addAssignedLocation(LocationTypeEnum locationType, Id locationId)
  {
    this.assignedLocationMap.put(locationType, locationId);
  }
  
  /**
   * 
   * @param locationType
   * @return the previous LocationId assigned to the locationType for this agent (or null if not found)
   */
  protected Id removeAssignedLocation(LocationTypeEnum locationType)
  {
    return this.assignedLocationMap.remove(locationType);
  }
  
  /**
   * @return the AgentType enum of this Agent
   */
  abstract public AgentTypeEnum getAgentType();
  
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
