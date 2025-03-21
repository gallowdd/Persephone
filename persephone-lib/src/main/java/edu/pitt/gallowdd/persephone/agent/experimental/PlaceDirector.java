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

package edu.pitt.gallowdd.persephone.agent.experimental;

import java.util.ArrayList;
import java.util.UUID;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

import edu.pitt.gallowdd.persephone.agent.experimental.ExampleController.AdvanceTimeEvent;
import edu.pitt.gallowdd.persephone.agent.experimental.ExampleController.AgentMoveEvent;

/**
 * 
 * @author David Galloway
 *
 */
public class PlaceDirector {
  
  private EventBus eventBus;
  
  /**
   * 
   */
  public PlaceDirector()
  {
    this.eventBus = new EventBus("BLAH");
    //this.eventBus.register(this);
    ArrayList<ExampleListener> myListeners = new ArrayList<>(1000);
    
    System.out.println("Begin creation and registration of listeners ...");
    for(int i = 0; i < 1000; ++i)
    {
      System.out.println("SUB" + String.valueOf(i));
      ExampleListener tmpListener = new ExampleListener("TEMPTYPE");
      myListeners.add(tmpListener);
      //this.eventBus.register(tmpListener);
    }
    
    myListeners.parallelStream().forEach((listener) -> {
      this.eventBus.register(listener);
    });
    
  }
  
  /**
   * @param advanceTimeEvent
   */
  @Subscribe
  public void handleAdvanceTimeEvent(AdvanceTimeEvent advanceTimeEvent)
  {
    if(advanceTimeEvent.getTimestep().equals("Hour"))
    {
      //System.out.println("Posting to my eventbus [" + this.eventBus.identifier() + "]");
      this.eventBus.post(advanceTimeEvent);
    }
  }
  
  /**
   * @param agentMoveEvent
   */
  @Subscribe
  public void handleAgentMoveEvent(AgentMoveEvent agentMoveEvent)
  {
    //this.eventBus.post(agentMoveEvent);
  }
}
