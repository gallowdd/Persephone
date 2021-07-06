package edu.pitt.gallowdd.persephone.event;

import edu.pitt.gallowdd.persephone.location.LocationTypeEnum;
import edu.pitt.gallowdd.persephone.util.Id;

/**
 * An event that is used to signal a particular location type should begin its mixing
 * 
 * @author David Galloway
 *
 */
public class MixEvent implements Event {
  
  private LocationTypeEnum locationType;
  private int currentHour;
  
  @Override
  public EventTypeEnum getType()
  {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Id getId()
  {
    // TODO Auto-generated method stub
    return null;
  }
  
  /**
   * 
   * @param locationType
   * @param currentHour
   */
  public MixEvent(LocationTypeEnum locationType, int currentHour)
  {
    this.locationType = locationType;
    this.currentHour = currentHour;
  }
  
  /**
   * @return the locationType
   */
  public LocationTypeEnum getLocationType()
  {
    return this.locationType;
  }
  
  /**
   * @return the currentHour
   */
  public int getCurrentHour()
  {
    return this.currentHour;
  }


  
}
