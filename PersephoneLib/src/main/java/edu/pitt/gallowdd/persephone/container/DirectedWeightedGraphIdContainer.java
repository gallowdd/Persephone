/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2022  David Galloway / University of Pittsburgh
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
import java.util.Optional;
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
public class DirectedWeightedGraphIdContainer extends GenericIdMixingContainer {
  
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
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(Id)
   */
  @Override
  public boolean remove(Id id)
  {
    return this.completeGraph.removeVertex(id);
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#remove(String)
   */
  @Override
  public boolean remove(String idString)
  {
    try
    {
      return this.completeGraph.removeVertex(new Id(idString));
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
    this.completeGraph.removeAllVertices(this.completeGraph.vertexSet());
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id)
   */
  @Override
  public Optional<Id> getConnected(Id id)
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
    
    return Optional.empty();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String)
   */
  @Override
  public Optional<Id> getConnected(String idString)
  {
    try
    {
      final Id id = new Id(idString);
      return this.getConnected(id);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return Optional.empty();
    }
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(Id, int)
   */
  @Override
  public Optional<List<Id>> getConnected(Id id, int howMany)
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
        
        List<Id> tempList = new ArrayList<>();
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
             Optional<Id> selectedOptionalId = selector.getRandomItemFromArrayGivenArrayOfWeights(idArr, weightArr);
             if(selectedOptionalId.isPresent())
             {
               int removeIndex = tempIdArrList.indexOf(selectedOptionalId.get());
               tempList.add(tempIdArrList.remove(removeIndex));
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
             else
             {
               // There should always be something, so this is not good
               DirectedWeightedGraphIdContainer.LOGGER.fatal("Was expecting an Id to be present and it was not in DirectedWeightedGraphIdContainer.getConnected()");
               System.exit(Constants.EX_SOFTWARE);
             }
          }
        }
        else
        {
          // I am asking for more Ids than are connected, so just return the entire array as a list
          for(int i = 0; i < idArr.length; ++i)
          {
            tempList.add(idArr[i]);
          }
        }
        return Optional.of(tempList);
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
    
    return Optional.empty();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getConnected(String, int)
   */
  @Override
  public Optional<List<Id>> getConnected(String idString, int howMany)
  {
    try
    {
      final Id id = new Id(idString);
      
      return this.getConnected(id, howMany);
    }
    catch(IdException e)
    {
      // Since the idString is invalid, it could not be in the this.idSet
      return Optional.empty();
    }
  }
  
  /**
   * Given an idString, return a List of howMany INCOMING connected {@code Id}s
   * 
   * Note: this will return an Optional List of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id (i.e. if there aren't enough connected to fill the List, 
   *   then you will get one that is size < howMany)
   * 
   * @param idString the id to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return An Optional List of at most howMany elements of type {@code Id} connected INCOMING to the id searched for or {@code Optional.empty()} if none
   */
  public Optional<List<Id>> getIncomingConnectedIds(String idString, int howMany)
  {
    Set<DefaultWeightedEdge> edgeSet = null;
    try
    {
      edgeSet = this.completeGraph.incomingEdgesOf(new Id(idString));
    }
    catch(IdException e)
    {
      // Since the idString is invalid, there can be no connected Ids
      return Optional.empty();
    }
    
    final int size = edgeSet.size();
    if(size > 0)
    {
      List<Id> tempList = new ArrayList<>();
      
      if(howMany >= size)
      {
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          tempList.add(this.completeGraph.getEdgeTarget(tempEdge));
        }
        return Optional.of(tempList);
      }
      else
      {
        int count = 0;
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          tempList.add(this.completeGraph.getEdgeTarget(tempEdge));
          if(++count == howMany)
          {
            break;
          }
        }
        return Optional.of(tempList);
      }
    }
    return Optional.empty();
  }
  
  /**
   * Given an idString, return a List of howMany OUTGOING connected {@code Id}s
   * 
   * Note: this will return an Optional List of type {@code Id} and will return a List of AT MOST 
   *   howMany elements of type {@code Id} connected to the id (i.e. if there aren't enough connected to fill the List, 
   *   then you will get one that is size < howMany)
   * 
   * @param idString the id to search for
   * @param howMany the (MAX) size of the List returned
   * 
   * @return An Optional List of at most howMany elements of type {@code Id} connected OUTGOING to the id searched for or {@code Optional.empty()} if none
   */
  public Optional<List<Id>> getOutgoingConnectedIds(String idString, int howMany)
  {
    Set<DefaultWeightedEdge> edgeSet = null;
        
    try
    {
      edgeSet = this.completeGraph.outgoingEdgesOf(new Id(idString));
    }
    catch(IdException e)
    {
      // Since the idString is invalid, there can be no connected Ids
      return Optional.empty();
    }
    
    final int size = edgeSet.size();
    if(size > 0)
    {
      List<Id> tempList = new ArrayList<>();
      
      if(howMany >= size)
      {
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          tempList.add(this.completeGraph.getEdgeTarget(tempEdge));
        }
        return Optional.of(tempList);
      }
      else
      {
        int count = 0;
        for(DefaultWeightedEdge tempEdge : edgeSet)
        {
          tempList.add(this.completeGraph.getEdgeTarget(tempEdge));
          if(++count == howMany)
          {
            break;
          }
        }
        return Optional.of(tempList);
      }
    }
    
    return Optional.empty();
  }
  
  /* (non-Javadoc)
   * @see edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer#getRandom()
   */
  @Override
  public Optional<Id> getRandom()
  {
    Set<Id> allIdSet = this.completeGraph.vertexSet();
    int randomIndex = Utils.getRandomInt(allIdSet.size());
    return Optional.of((Id)allIdSet.toArray()[randomIndex]);
  }
}
