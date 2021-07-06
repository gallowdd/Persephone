package edu.pitt.gallowdd.persephone.event;

/**
 * 
 * @author David Gallowau
 *
 */
public class AdvanceTimeEvent {
  
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
