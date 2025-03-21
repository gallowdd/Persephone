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
package edu.pitt.gallowdd.persephone.belief;

import java.text.DecimalFormat;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 
 * @author David Galloway
 *
 */
public abstract class Belief {
  
  //private static final Logger LOGGER = LogManager.getLogger(Belief.class.getName());
  
  private static final DecimalFormat DF = new DecimalFormat("#0.00000");
  private static final double MARGIN = 0.0001;
  private static final double MIN = -1.0;
  private static final double MAX = 1.0;
  
  private double x = 0.0;
  
  /**
   * Create a Position in one dimension (x)
   * 
   * @param x
   */
  public Belief(double x)
  {
    this.setX(x);
  }
  
  /**
   * @return the x position
   */
  public double getX()
  {
    return this.x;
  }
  
  /**
   * @param x the x position to set
   */
  public void setX(double x)
  {
    if(x > Belief.MAX || x < Belief.MIN)
    {
      throw new RuntimeException("x Position is Out of range (" + Belief.MIN + ", " + Belief.MAX + ")");
    }
    else if(x <= Belief.MAX && x > Belief.MAX - Belief.MARGIN)
    {
      this.x = Belief.MAX - Belief.MARGIN;
    }
    else if(x >= Belief.MIN && x < Belief.MIN + Belief.MARGIN)
    {
      this.x = Belief.MIN + Belief.MARGIN;
    }
    else
    {
      this.x = x;
    }
  }
  
  /**
   * 
   * @param belief the Belief to add to this Belief
   */
  public void add(Belief belief)
  {
    this.addX(belief.getX());
  }
  
  /**
   * Add a scalar to the x value of belief vector only
   * 
   * @param x how much to add to this x value of the Belief vector
   */
  abstract public void addX(double x);
  
  /**
   * 
   * @param belief the Belief to subtract from this Belief
   */
  public void subtract(Belief belief)
  {
    this.subtractX(belief.getX());
  }
  
  /**
   * 
   * @param x how much to subtract from this position
   */
  abstract public void subtractX(double x);
  
  /**
   * 
   * @param m
   */
  abstract public void multiply(int m);
  
  /* (non-Javadoc)
   * @see java.lang.Object#equals(Object)
   */
  @Override
  public boolean equals(Object obj) {
    if(this == obj)
    {
      return true;
    }
    
    if(obj == null)
    {
      return false;
    }
    
    if(getClass() != obj.getClass())
    {
      return false;
    }
    
    Belief rhs = (Belief)obj;
    EqualsBuilder equalsBuilder = new EqualsBuilder();
    equalsBuilder.append(this.x, rhs.x);
    return equalsBuilder.isEquals();
  }
  
  /**
   * 
   * @return this object as a JSON string
   */
  public String toJSONString()
  {
    
    return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
        .append("x", this.x)
        .toString();
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return "[" + Belief.DF.format(this.x) + "]";
  }
}
