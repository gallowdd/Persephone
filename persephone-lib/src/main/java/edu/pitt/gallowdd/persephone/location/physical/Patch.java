package edu.pitt.gallowdd.persephone.location.physical;

import edu.pitt.gallowdd.persephone.container.GenericIdMixingContainer;

/**
 * 
 * @author David Galloway
 *
 */
public class Patch {
  
  private final GenericIdMixingContainer mixingContainer;
  private final double minLatitude;
  private final double maxLatitude;
  private final double minLongitude;
  private final double maxLongitude;
  private final double minElevation;
  private final double maxElevation;
  
  /**
   * @param mixingContainer
   * @param minLatitude
   * @param maxLatitude
   * @param minLongitude
   * @param maxLongitude
   * @param minElevation
   * @param maxElevation
   * @throws PhysicalLocationException if minLatitude is >= maxLatitude or minLongitude >= maxLongitude
   */
  public Patch(GenericIdMixingContainer mixingContainer, double minLatitude, double maxLatitude, double minLongitude, double maxLongitude,
      double minElevation, double maxElevation) throws PhysicalLocationException
  {
    super();
    this.mixingContainer = mixingContainer;
    
    if(minLatitude < -90.0 || minLatitude >= maxLatitude || minLongitude < -90.0 || minLongitude >= maxLongitude)
    {
      StringBuffer sb1 = new StringBuffer("");
      sb1.append("minLatitude = " + minLatitude + "' ");
      sb1.append("maxLatitude = " + maxLatitude + "' ");
      sb1.append("minLongitude = " + minLongitude + "' ");
      sb1.append("maxLongitude = " + maxLongitude);
      throw new PhysicalLocationException(sb1.toString());
    }
    
    if(minElevation > maxElevation)
    {
      StringBuffer sb1 = new StringBuffer("");
      sb1.append("minElevation = " + minElevation + "' ");
      sb1.append("maxElevation = " + maxElevation);
      throw new PhysicalLocationException(sb1.toString());
    }
    
    this.minLatitude = minLatitude;
    this.maxLatitude = maxLatitude;
    this.minLongitude = minLongitude;
    this.maxLongitude = maxLongitude;
    
    this.minElevation = minElevation;
    this.maxElevation = maxElevation;
  }
  
  /**
   * @return the minLatitude
   */
  public double getMinLatitude()
  {
    return this.minLatitude;
  }
  
  /**
   * @return the maxLatitude
   */
  public double getMaxLatitude()
  {
    return this.maxLatitude;
  }
  
  /**
   * @return the minLongitude
   */
  public double getMinLongitude()
  {
    return this.minLongitude;
  }
  
  /**
   * @return the maxLongitude
   */
  public double getMaxLongitude()
  {
    return this.maxLongitude;
  }
  
  /**
   * @return the minElevation
   */
  public double getMinElevation()
  {
    return this.minElevation;
  }
  
  /**
   * @return the maxElevation
   */
  public double getMaxElevation()
  {
    return this.maxElevation;
  }
  
}
