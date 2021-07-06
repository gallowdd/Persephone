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
package edu.pitt.gallowdd.persephone.helper.initialization;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author David Galloway
 *
 */
public class PopulationInitInfo  extends InitializationInfo {
  
  private int popCount;
  private int hhCount;
  private int schoolCount;
  private int workplaceCount;
  
  /**
   * Default Constructor
   */
  public PopulationInitInfo() 
  {
    super();
  }
  
  /**
   * @return the popCount
   */
  public int getPopCount()
  {
    return this.popCount;
  }

  /**
   * @param popCount the popCount to set
   */
  public void setPopCount(int popCount)
  {
    this.popCount = popCount;
  }

  /**
   * @return the hhCount
   */
  public int getHhCount()
  {
    return this.hhCount;
  }

  /**
   * @param hhCount the hhCount to set
   */
  public void setHhCount(int hhCount)
  {
    this.hhCount = hhCount;
  }

  /**
   * @return the schoolCount
   */
  public int getSchoolCount()
  {
    return this.schoolCount;
  }

  /**
   * @param schoolCount the schoolCount to set
   */
  public void setSchoolCount(int schoolCount)
  {
    this.schoolCount = schoolCount;
  }

  /**
   * @return the workplaceCount
   */
  public int getWorkplaceCount()
  {
    return this.workplaceCount;
  }

  /**
   * @param workplaceCount the workplaceCount to set
   */
  public void setWorkplaceCount(int workplaceCount)
  {
    this.workplaceCount = workplaceCount;
  }
  
  @Override
  public String toString()
  {
    return new ToStringBuilder(this).
      append("popCount", String.valueOf(this.popCount)).
      append("hhCount", String.valueOf(this.hhCount)).
      append("schoolCount", String.valueOf(this.schoolCount)).
      append("workplaceCount", String.valueOf(this.workplaceCount)).
      toString();
  }
}