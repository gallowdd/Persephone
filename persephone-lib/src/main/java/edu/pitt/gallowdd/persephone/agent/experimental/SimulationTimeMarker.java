package edu.pitt.gallowdd.persephone.agent.experimental;


/**
 * @author David Galloway
 *
 */
public class SimulationTimeMarker implements Comparable<SimulationTimeMarker> {

  
  
  public static class SimYear implements Comparable<SimYear> {
    
    private int yyyy = -1;
    
    /**
     * @param yyyy
     */
    public SimYear(int yyyy)
    {
      
    }
    
    @Override
    public int compareTo(SimYear o)
    {
      // TODO Auto-generated method stub
      return 0;
    }
    
  }

  @Override
  public int compareTo(SimulationTimeMarker o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
