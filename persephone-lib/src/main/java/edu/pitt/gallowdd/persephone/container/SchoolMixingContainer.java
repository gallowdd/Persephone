/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2023  David Galloway / University of Pittsburgh
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

package edu.pitt.gallowdd.persephone.container;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * 
 * 
 * @author David Galloway
 */
public class SchoolMixingContainer extends GenericIdMixingContainer 
{
  private final Set<Id> teacherIdSet = new HashSet<>();
  private final Set<Id> studentIdSet = new HashSet<>();
  private final Set<Id> staffIdSet = new HashSet<>();
  private final Set<Id> visitorIdSet = new HashSet<>();
//  private final Map<String, Double> attributeWeightMap = new HashMap<>();
  
  /**
   * Default constructor creates a Mixing Container for School locations
   * 
   * This Container allows for Staff (Employees who are not teachers), Students, and Teachers
   */
  public SchoolMixingContainer()
  {
    super(MixingRoleTypeEnum.EMPLOYEE, MixingRoleTypeEnum.TEACHER, MixingRoleTypeEnum.STUDENT, MixingRoleTypeEnum.VISITOR);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#add(Id)
   */
  @Override
  public void add(Id id)
  {
    // Since we don't have a role defined, we just assume that the agent is a Visitor
    this.visitorIdSet.add(id);
  }
  
  /**
   * Add the Id to the this collection
   *
   * @param mixingRoleType the MixingRoleType that we want to add to the container
   * @param id the id to add
   * @throws MixingContainerTypeMatchException if the container does not accept agents in the mixingTypeRole
   */
  public void add(MixingRoleTypeEnum mixingRoleType, Id id) throws MixingContainerTypeMatchException
  {
    switch(mixingRoleType)
    {
      case EMPLOYEE:
        this.staffIdSet.add(id);
        break;
      case STUDENT:
        this.studentIdSet.add(id);
        break;
      case TEACHER:
        this.teacherIdSet.add(id);
        break;
      case VISITOR:
        this.visitorIdSet.add(id);
        break;
      default:
        throw new MixingContainerTypeMatchException(id.getIdString(), mixingRoleType);
      
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    if(this.staffIdSet.contains(id))
    {
      return this.staffIdSet.remove(id);
    }
    else if(this.studentIdSet.contains(id))
    {
      return this.studentIdSet.remove(id);
    }
    else if(this.teacherIdSet.contains(id))
    {
      return this.teacherIdSet.remove(id);
    }
    else if(this.visitorIdSet.contains(id))
    {
      return this.visitorIdSet.remove(id);
    }
    
    return false;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(String)
   */
  @Override
  public boolean remove(String idString) throws IdException
  {
    try
    {
      return this.remove(new Id(idString));
    } 
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return false;
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#clear()
   */
  @Override
  public void clear()
  {
    this.staffIdSet.clear();
    this.studentIdSet.clear();
    this.teacherIdSet.clear();
    this.visitorIdSet.clear();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getRandom()
   */
  @Override
  public Optional<Id> getRandom()
  {
    final int staffSetSize = this.staffIdSet.size();
    final int studentSetSize = this.studentIdSet.size();
    final int teacherSetSize = this.teacherIdSet.size();
    final int visitorSetSize = this.visitorIdSet.size();
    final int totalPopulation = staffSetSize + studentSetSize + teacherSetSize + visitorSetSize;
    
    if(totalPopulation == 0)
    {
      return Optional.empty();
    }
    
    final double staffProb = (double)staffSetSize / (double)totalPopulation;
    final double sudentProb = (double)studentSetSize / (double)totalPopulation;
    final double teacherProb = (double)teacherSetSize / (double)totalPopulation;
    
    double randDbl = Utils.getRandomNumberGenerator().nextDouble();
    
    if(randDbl < staffProb)
    {
      return this.staffIdSet.stream().skip(Utils.getRandomNumberGenerator().nextInt(staffSetSize)).findFirst();
    }
    else if(randDbl < staffProb + sudentProb)
    {
      return this.studentIdSet.stream().skip(Utils.getRandomNumberGenerator().nextInt(studentSetSize)).findFirst();
    }
    else if(randDbl < staffProb + sudentProb + teacherProb)
    {
      return this.teacherIdSet.stream().skip(Utils.getRandomNumberGenerator().nextInt(teacherSetSize)).findFirst();
    }
    else
    {
      return this.visitorIdSet.stream().skip(Utils.getRandomNumberGenerator().nextInt(visitorSetSize)).findFirst();
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id)
   */
  @Override
  public Optional<Id> getConnected(Id id)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String)
   */
  @Override
  public Optional<Id> getConnected(String idString)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id, int)
   */
  @Override
  public Optional<List<Id>> getConnected(Id id, int howMany)
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String, int)
   */
  @Override
  public Optional<List<Id>> getConnected(String idString, int howMany)
  {
    // TODO Auto-generated method stub
    return null;
  }
}