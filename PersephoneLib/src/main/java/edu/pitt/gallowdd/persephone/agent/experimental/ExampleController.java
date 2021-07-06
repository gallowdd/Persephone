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

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.UUID;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;

/**
 * 
 * @author ddg5
 *
 */
public class ExampleController {
  
  private EventBus eventBus = new EventBus("MAIN"); //UUID.randomUUID().toString());
  
  /**
   * @param args 
   * 
   */
  public static void main(String[] args)
  {
    LocalDateTime begin = LocalDateTime.now();
    ExampleController me = new ExampleController();
    me.eventBus.register(me);
    ArrayList<ExampleListener> myListeners = new ArrayList<>(100000);
    
    System.out.println("Begin creation and registration of listeners ...");
    for(int i = 0; i < 100000; ++i)
    {
      System.out.println(String.valueOf(i));
      ExampleListener tmpListener = new ExampleListener("TEMPTYPE");
      myListeners.add(tmpListener);
    }
    
    myListeners.parallelStream().forEach(tmpListener ->
    {
      me.eventBus.register(tmpListener);
    });
    
    System.out.println("Post an event to the Bus ...");
    me.eventBus.post(new AdvanceTimeEvent("Hour"));
    me.eventBus.post(new AgentMoveEvent("Agent1", myListeners.get(2).getId(), myListeners.get(9).getId()));
    me.eventBus.post(new AgentMoveEvent("Agent2", myListeners.get(0).getId(), myListeners.get(8).getId()));
    me.eventBus.post("String Event");
    System.out.println("countHour = " + ExampleListener.countHour);
    LocalDateTime end = LocalDateTime.now();
    
    long millis = begin.until(end, ChronoUnit.MILLIS);

    System.out.println("begin = " + begin);
    System.out.println("end   = " + end);
    System.out.printf("The difference is %s millis", millis);
    /////////////
//    LocalDateTime begin = LocalDateTime.now();
//    ExampleController me = new ExampleController();
//    me.eventBus.register(me);
//    ArrayList<PlaceController> myListeners = new ArrayList<>(100);
//    
//    System.out.println("Begin creation and registration of listeners ...");
//    for(int i = 0; i < 1000; ++i)
//    {
//      System.out.println(String.valueOf(i));
//      PlaceController tmpListener = new PlaceController();
//      myListeners.add(tmpListener);
////      me.eventBus.register(tmpListener);
//    }
//    
//    myListeners.parallelStream().forEach((listener) -> {
//      me.eventBus.register(listener);
//    });
//    
//    System.out.println("Post an event to the Bus ...");
//    me.eventBus.post(new AdvanceTimeEvent("Hour"));
//    me.eventBus.post(new AgentMoveEvent("Agent1", "ONE", "TwO"));
//    me.eventBus.post(new AgentMoveEvent("Agent2", "ZERO", "EIGHT"));
//    me.eventBus.post("String Event");
//    System.out.println("countHour = " + ExampleListener.countHour);
//    LocalDateTime end = LocalDateTime.now();
//    
//    long millis = begin.until(end, ChronoUnit.MILLIS);
//
//    System.out.println("begin = " + begin);
//    System.out.println("end   = " + end);
//    System.out.printf("The difference is %s millis", millis);
  }
  
  /**
   * 
   * @author David Galloway
   *
   */
  public static class AdvanceTimeEvent {
    private String timestep;
    
    /**
     * 
     * @param timestep
     */
    public AdvanceTimeEvent(String timestep)
    {
      this.timestep = timestep;
    }
    
    /**
     * @return the timestep
     */
    public String getTimestep()
    {
      return this.timestep;
    }
  }
  
  /**
   * @param deadEvent
   */
  @Subscribe
  public void handleDeadEvent(DeadEvent deadEvent) {
      System.out.println("DEAD EVENT --- I.E. No listener for this type of event");
  }
  
  /**
   * 
   * @author ddg5
   *
   */
  public static class AgentMoveEvent {
    
    private String agentId;
    private String currentPlaceId;
    private String newPlaceId;
    
    /**
     * 
     * @param agentId
     * @param currentPlaceId
     * @param newPlaceId
     */
    public AgentMoveEvent(String agentId, String currentPlaceId, String newPlaceId)
    {
      this.agentId = agentId;
      this.currentPlaceId = currentPlaceId;
      this.newPlaceId = newPlaceId;
    }
    
    /**
     * @return the agentId
     */
    public String getAgentId()
    {
      return this.agentId;
    }
    
    /**
     * @return the currentPlaceId
     */
    public String getCurrentPlaceId()
    {
      return this.currentPlaceId;
    }
    
    /**
     * @return the newPlaceId
     */
    public String getNewPlaceId()
    {
      return this.newPlaceId;
    }
  }
  
  
  /**
   * 
   * @author ddg5
   *
   */
  public static class MixEvent {
    
    private String placeType;
    private int currentHour;
    
    /**
     * 
     * @param placeType
     * @param currentHour
     */
    public MixEvent(String placeType, int currentHour)
    {
      this.placeType = placeType;
      this.currentHour = currentHour;
    }
    
    /**
     * @return the placeType
     */
    public String getPlaceType()
    {
      return this.placeType;
    }
    
    /**
     * @return the currentHour
     */
    public int getCurrentHour()
    {
      return this.currentHour;
    }
  }
}
