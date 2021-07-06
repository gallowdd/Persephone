package edu.pitt.gallowdd.persephone.agent.activity;

import java.time.DateTimeException;

/**
 * @author David Galloway
 *
 */
public interface Schedulable {
  
  /**
   * 
   * @param activityType the type of activity to check
   * @param hour hour of the day 0 - 23
   * @param dayOfYear Julian day of the year 1 - 365 (366 for leap years)
   * @param year 4 digit year (must be valid year)
   * @return {@code true} if the Activity is on the schedule for the given hour of the day, day of the year, and year, {@code false} otherwise
   * @throws DateTimeException
   */
  boolean isScheduled(ActivityType activityType, int hour, int dayOfYear, int year) throws DateTimeException;
  
  //Schedulable combine()
}
