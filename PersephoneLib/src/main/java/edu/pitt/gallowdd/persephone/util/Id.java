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

package edu.pitt.gallowdd.persephone.util;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * This class allows creation of Standard Ids for the simulation
 * 
 * @author David Galloway
 **/
public final class Id implements Comparable<Id>
{
  /**
   * The default Null Id that uses the ID_UNSET String
   */
  public static final Id NULL_ID = new Id();
  
  //\A[A-Z][A-Z][A-Z]?[A-Z]?[A-Z]?[A-Z]?_[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}|XXX_0{8}-0{4}-0{4}-0{4}-0{12}\z
  private static final String UUID_PATTERN_STR = "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}";
  private static final String ID_UNSET = "XXX_00000000-0000-0000-0000-000000000000";
  
  // Must be at least Two Capital Letters (up to Six Capital letters) followed by an underscore then the UUID
  //  OR it can be the ID_UNSET match (The OR part allows unset UUID to be validated)
  private static final String ID_PATTERN_STR = "\\A" + "[A-Z][A-Z]?[A-Z]?[A-Z]?[A-Z]?[A-Z]_(?:MGR_)?" + Id.UUID_PATTERN_STR + "|XXX_0{8}-0{4}-0{4}-0{4}-0{12}" + "\\z"; 
  private static Pattern ID_PATTERN = Pattern.compile(Id.ID_PATTERN_STR);

  private String idString = null;
  
//  private static final NullID = new Id();
  
  /**
   * Default Constructor creates an Id with the value of UNSET
   * i.e. XXX_00000000-0000-0000-0000-000000000000
   */
  private Id()
  {
    this.idString = Id.ID_UNSET;
  }
  
  /**
   * Constructor used when we already have an idString for an Id
   * 
   * @param id the id to set
   */
  public Id(Id id)
  {
    this.idString = id.getIdString();
  }
  
  /**
   * Constructor used when we already have an idString for an Id
   * 
   * @param idString the idString to set
   * @throws IdException if the idString is not valid for an Id
   */
  public Id(String idString) throws IdException
  {
    if(Id.isValidIdString(idString))
    {
      this.idString = idString;
    }
    else
    {
      throw new IdException(idString);
    }
  }
  
  /**
   * Get the idString
   * 
   * @return the idString
   */
  public String getIdString()
  {
    return this.idString;
  }
  
  /**
   * Set the idString
   * 
   * @param idString the idString to set
   * @throws IdException if the idString is not valid for an Id
   */
  public void setIdString(String idString) throws IdException
  {
    if(Id.isValidIdString(idString))
    {
      this.idString = idString;
    }
    else
    {
      throw new IdException(idString);
    }
  }
  
  /**
   * Determine if an Id is Unset
   * 
   * @return @code{true} if the id is unset, @code{false} otherwise
   */
  public boolean isUnset()
  {
    return this.idString.equals(Id.ID_UNSET);
  }
  
  /**
   * @param prepend The 2 to Six Capital letters to prepend to the UUID
   * @return a new id that is value ID_UNSET
   * @throws IdException 
   */
  public static Id create(String prepend) throws IdException
  {
    final String idString = prepend + "_" + UUID.randomUUID().toString();
    if(Id.isValidIdString(idString))
    {
      return new Id(idString);
    }
    throw new IdException(idString);
  }
  
  /**
   * Validate the pattern of an idString
   * 
   * @param value the id to validate
   * @return {@code true} if the ID is a valid one, {@code false} if not
   */
  private static boolean isValidIdString(String value)
  {
    final Matcher matcher = Id.ID_PATTERN.matcher(value);
    return matcher.matches();
  }

  /* (non-Javadoc)
   * @see java.lang.Object#clone()
   */
  @Override
  public Object clone() throws CloneNotSupportedException
  {
    try
    {
      return new Id(this.idString);
    }
    catch(IdException e)
    {
      // Should not happen since this Id has already been successfully created
      throw new CloneNotSupportedException("idString is Invalid");
    }
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#hashCode()
   */
  @Override
  public int hashCode()
  {
    return new HashCodeBuilder()
      .append(this.idString)
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
    Id other = (Id)obj;
    
    return new EqualsBuilder()
      .append(this.idString, other.idString)
      .isEquals();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Id o)
  {
    return new CompareToBuilder()
      .append(this.idString, o.idString)
      .toComparison();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return new ToStringBuilder(this)
        .append("idString", this.idString)
        .toString();
  }
  
  /**
   * Returns the string representation of this object, but in JSON format
   * 
   * @return the object's string representation in JSON format
   */
  public String toJsonString()
  {
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("idString", this.idString)
        .toString();
  }
}