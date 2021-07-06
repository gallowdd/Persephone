package edu.pitt.gallowdd.persephone.agent.experimental;

import java.util.UUID;

import com.google.common.eventbus.Subscribe;

import edu.pitt.gallowdd.persephone.agent.experimental.ExampleController.AdvanceTimeEvent;
import edu.pitt.gallowdd.persephone.agent.experimental.ExampleController.AgentMoveEvent;
import edu.pitt.gallowdd.persephone.agent.experimental.ExampleController.MixEvent;

/**
 * 
 * @author ddg5
 *
 */
public class ExampleListener {
  
  static public int countHour = 0;
  private String id;
  private String myType;
  private boolean[] isOpenHour = new boolean[24];
  
  /**
   * 
   */
  public void mix()
  {
    
  }
  
  /**
   * 
   * @param type
   */
  public ExampleListener(String type)
  {
    this.id = UUID.randomUUID().toString();
    this.myType = type;
    for(int i = 0; i < this.isOpenHour.length; ++i)
    {
      this.isOpenHour[i] = (Math.random() < 0.2);
    }
  }
  
  /**
   * @return the id
   */
  public String getId()
  {
    return this.id;
  }
  
  /**
   * @return the myType
   */
  public String getMyType()
  {
    return this.myType;
  }
  
  /**
   * @param advanceTimeEvent
   */
  @Subscribe
  public void handleAdvanceTimeEvent(AdvanceTimeEvent advanceTimeEvent)
  {
//    if(advanceTimeEvent.getTimestep().equals("Hour") && this.respondHourly)
//    {
//      countHour++;
      System.out.println("Location [" + this.id + "] should advance one " + advanceTimeEvent.getTimestep());
//    }
  }
  
  /**
   * @param agentMoveEvent
   */
  @Subscribe
  public void handleAgentMoveEvent(AgentMoveEvent agentMoveEvent)
  {
    if(agentMoveEvent.getCurrentPlaceId().equals(this.id))
    {
      System.out.println("The Agent ID [" + agentMoveEvent.getAgentId() + "] is leaving my location [" + this.id + "]");
    }
    else if(agentMoveEvent.getNewPlaceId().equals(this.id))
    {
      System.out.println("The Agent ID [" + agentMoveEvent.getAgentId() + "] is joining my location [" + this.id + "]");
    }
  }
  
  /**
   * @param mixEvent
   */
  @Subscribe
  public void handleMixEvent(MixEvent mixEvent)
  {
    if(mixEvent.getPlaceType().equals(this.myType)
        && mixEvent.getCurrentHour() >= 0
        && mixEvent.getCurrentHour() < this.isOpenHour.length
        && this.isOpenHour[mixEvent.getCurrentHour()])
    {
      this.mix();
    }
  }
  
//  @Subscribe
//  public void someCustomEvent(CustomEvent customEvent) {
//      eventsHandled++;
//  }
}
