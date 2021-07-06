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

package edu.pitt.gallowdd.persephone.container;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultWeightedEdge;

import edu.pitt.gallowdd.persephone.PersephoneException;
import edu.pitt.gallowdd.persephone.PersephoneException.ErrorCode;
import edu.pitt.gallowdd.persephone.util.ArraySelector;
import edu.pitt.gallowdd.persephone.util.ArraySelectorException;
import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Id;
import edu.pitt.gallowdd.persephone.util.IdException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * The Container to allow for selecting agents connected to other agents in a graph
 * 
 * @author David Galloway
 */
public class DirectedWeightedGraphIdContainer implements IdConnectable {
  
  private static final Logger LOGGER = LogManager.getLogger(DirectedWeightedGraphIdContainer.class.getName());
  
  private Graph<Id, DefaultWeightedEdge> completeGraph;
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#add(Id)
   */
  @Override
  public void add(Id id)
  {
    this.completeGraph.addVertex(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    return this.completeGraph.removeVertex(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectablee#remove(String)
   */
  @Override
  public boolean remove(String idString)
  {
    try
    {
      return this.completeGraph.removeVertex(new Id(idString));
    }
    catch (IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return false;
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#clear(String)
   */
  @Override
  public void clear()
  {
    this.completeGraph.removeAllVertices(this.completeGraph.vertexSet());
  }
  
  @Override
  public Id getConnected(Id id)
  {
    try
    {
      Set<DefaultWeightedEdge> edgeSet = null;
      
      edgeSet = this.completeGraph.outgoingEdgesOf(id);
      
      final int size = edgeSet.size();
      
      if(size >= 1)
      {
        Id[] idArr = new Id[size];
        double[] weightArr = new double[size];
        int index = 0;
        
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          Id tmpId = this.completeGraph.getEdgeTarget(tempEdge);
          if(tmpId.equals(id))
          {
            if(!this.completeGraph.getType().isDirected() && !this.completeGraph.getType().isAllowingSelfLoops())
            {
              // If the graph is not directed and not allowing self loops, there is some kind of problem
              throw new PersephoneException(ErrorCode.ERR_DIR_GRAPH_CRIT.message("The directed graph should not allow self-loops, yet it has source = target."));
            }
            else
            {
              tmpId = this.completeGraph.getEdgeSource(tempEdge);
              if(tmpId.equals(id))
              {
                // Again, this should not happen
                throw new PersephoneException(ErrorCode.ERR_DIR_GRAPH_CRIT.message("The directed graph should not allow self-loops, yet it has source = target."));
              }
            }
          }
          idArr[index] = tmpId;
          weightArr[index] = this.completeGraph.getEdgeWeight(tempEdge);
          ++index;
        }
        
        ArraySelector<Id> selector = new ArraySelector<>();
        return selector.getRandomItemFromArrayGivenArrayOfWeights(idArr, weightArr);
      }
    }
    catch(ArraySelectorException e)
    {
      DirectedWeightedGraphIdContainer.LOGGER.fatal(e.getMessage());
      System.exit(Constants.EX_SOFTWARE);
    }
    catch(PersephoneException e)
    {
      DirectedWeightedGraphIdContainer.LOGGER.fatal(e.getMessage());
      System.exit(Constants.EX_SOFTWARE);
    }
    
    return null;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#getConnected(String)
   */
  @Override
  public Id getConnected(String idString)
  {
    try
    {
      final Id id = new Id(idString);
      return this.getConnected(id);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return null;
    }
  }
  
  @Override
  public List<Id> getConnected(Id id, int howMany)
  {
    try
    {
      Set<DefaultWeightedEdge> edgeSet = null;
      
      edgeSet = this.completeGraph.outgoingEdgesOf(id);
      
      final int size = edgeSet.size();
      
      if(size >= 1)
      {
        Id[] idArr = new Id[size];
        double[] weightArr = new double[size];
        int index = 0;
        
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          Id tmpId = this.completeGraph.getEdgeTarget(tempEdge);
          if(tmpId.equals(id))
          {
            if(!this.completeGraph.getType().isDirected() && !this.completeGraph.getType().isAllowingSelfLoops())
            {
              // If the graph is not directed and not allowing self loops, there is some kind of problem
              throw new PersephoneException(ErrorCode.ERR_DIR_GRAPH_CRIT.message("The directed graph should not allow self-loops, yet it has source = target."));
            }
            else
            {
              tmpId = this.completeGraph.getEdgeSource(tempEdge);
              if(tmpId.equals(id))
              {
                // Again, this should not happen
                throw new PersephoneException(ErrorCode.ERR_DIR_GRAPH_CRIT.message("The directed graph should not allow self-loops, yet it has source = target."));
              }
            }
          }
          idArr[index] = tmpId;
          weightArr[index] = this.completeGraph.getEdgeWeight(tempEdge);
          ++index;
        }
        
        ArraySelector<Id> selector = new ArraySelector<>();
        
        List<Id> retVal = new ArrayList<>();
        // An ArrayList that I will remove items from as they are selected
        List<Id> tempIdArrList = new ArrayList<>();
        Collections.addAll(tempIdArrList, idArr);
        // An ArrayList that I will remove weights from as they are selected
        List<Double> tempDblArrList = DoubleStream.of(weightArr) 
            .boxed()
            .collect(Collectors.toList());
        
        if(size > howMany)
        {
          for(int i = 0; i < howMany; ++i)
          {
             Id selectedId = selector.getRandomItemFromArrayGivenArrayOfWeights(idArr, weightArr);
             int removeIndex = tempIdArrList.indexOf(selectedId);
             retVal.add(tempIdArrList.remove(removeIndex));
             tempDblArrList.remove(removeIndex);
             
             int newArrSize = tempIdArrList.size();
             idArr = new Id[newArrSize];
             idArr = (Id[])tempIdArrList.toArray();
             weightArr = new double[newArrSize];
             for(int j = 0; j < newArrSize; ++j)
             {
               weightArr[j] = tempDblArrList.get(j);
             }
          }
        }
        else
        {
          // I am asking for more Ids than are connected, so just return the entire array as a list
          for(int i = 0; i < idArr.length; ++i)
          {
            retVal.add(idArr[i]);
          }
        }
        return retVal;
      }
    }
    catch(ArraySelectorException e)
    {
      DirectedWeightedGraphIdContainer.LOGGER.fatal(e.getMessage());
      System.exit(Constants.EX_SOFTWARE);
    }
    catch(Exception e)
    {
      DirectedWeightedGraphIdContainer.LOGGER.fatal(e.getMessage());
      System.exit(Constants.EX_SOFTWARE);
    }
    
    return null;
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.IdConectable#getConnected(T, int)
   */
  @Override
  public List<Id> getConnected(String idString, int howMany)
  {
    try
    {
      final Id id = new Id(idString);
      
      return this.getConnected(id, howMany);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return null;
    }
  }
  
  /**
   * 
   * @param idString the idString to search for
   * @param howMany
   * @return a {@code List<String>} of the Id Strings that connect to inId, or {@code null} if none exist
   */
  public List<Id> getIncomingConnectedIds(String idString, int howMany)
  {
    Set<DefaultWeightedEdge> edgeSet = null;
    try
    {
      edgeSet = this.completeGraph.incomingEdgesOf(new Id(idString));
    }
    catch (IdException e)
    {
      // Since the idString is invalid, there can be no connected Ids
      return null;
    }
    
    final int size = edgeSet.size();
    if(size > 0)
    {
      List<Id> retVal = new ArrayList<>();
      
      if(howMany >= size)
      {
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          retVal.add(this.completeGraph.getEdgeTarget(tempEdge));
        }
        return retVal;
      }
      else
      {
        int count = 0;
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          retVal.add(this.completeGraph.getEdgeTarget(tempEdge));
          if(++count == howMany)
          {
            break;
          }
        }
        return retVal;
      }
    }
    return null;
  }
  
  /**
   * 
   * @param idString
   * @param howMany
   * @return a {@code List<Id>} of the Ids that inIdString connects to, or {@code null} if none exist
   */
  public List<Id> getOutgoingConnectedIds(String idString, int howMany)
  {
    Set<DefaultWeightedEdge> edgeSet = null;
        
    try
    {
      edgeSet = this.completeGraph.outgoingEdgesOf(new Id(idString));
    }
    catch(IdException e)
    {
      // Since the idString is invalid, there can be no connected Ids
      return null;
    }
    
    final int size = edgeSet.size();
    if(size > 0)
    {
      List<Id> retVal = new ArrayList<>();
      
      if(howMany >= size)
      {
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          retVal.add(this.completeGraph.getEdgeTarget(tempEdge));
        }
        return retVal;
      }
      else
      {
        int count = 0;
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          retVal.add(this.completeGraph.getEdgeTarget(tempEdge));
          if(++count == howMany)
          {
            break;
          }
        }
        return retVal;
      }
    }
    
    return null;
  }
  
  @Override
  public Id getRandom()
  {
    Set<Id> allIdSet = this.completeGraph.vertexSet();
    int randomIndex = Utils.getRandomInt(allIdSet.size());
    return (Id)allIdSet.toArray()[randomIndex];
  }
}
