package edu.pitt.gallowdd.persephone.location;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * A class that holds a latitude and longitude. It also has a method to calculate the Haversine distance between any two Latitude/Longitude
 * on Earth
 * 
 * @author David Galloway
 *
 */
public class LatitudeLongitude {
  
  private final double latitude;
  private final double longitude;
  
  /**
   * 
   * @param latitude
   * @param longitude
   */
  public LatitudeLongitude(double latitude, double longitude)
  {
    this.latitude = latitude;
    this.longitude = longitude;
  }
  
  /**
   * @return the latitude
   */
  public double getLatitude()
  {
    return this.latitude;
  }
  
  /**
   * @return the longitude
   */
  public double getLongitude()
  {
    return this.longitude;
  }
  
  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString()
  {
    return new ToStringBuilder(this)
        .append("latitude", this.latitude)
        .append("longitude", this.longitude)
        .toString();
  }
  
  /**
   * Note: This calculates the distance between two points on the Earth's surface. 
   * Remember that the distance between two longitudes at the Equator is going to be the biggest,
   * while the distance between two longitudes at the poles is 0.0
   * 
   * @param lat1 the latitude of point 1
   * @param lon1 the longitude of point 1
   * @param lat2 the latitude of point 2
   * @param lon2 the longitude of point 2
   * @return the distance in Kilometers between two points on Earth
   */
  public static double haversine(double lat1, double lon1, double lat2, double lon2)
  {
    final double R = 6372.8; // The Radius of the Earth in KM
    double dLat = Math.toRadians(lat2 - lat1);
    double dLon = Math.toRadians(lon2 - lon1);
    lat1 = Math.toRadians(lat1);
    lat2 = Math.toRadians(lat2);
    
    double a = Math.pow(Math.sin(dLat / 2),2) + Math.pow(Math.sin(dLon / 2),2) * Math.cos(lat1) * Math.cos(lat2);
    double c = 2 * Math.asin(Math.sqrt(a));
    return R * c;
  }
}
