package edu.pitt.gallowdd.persephone.agent.activity;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

import edu.pitt.gallowdd.persephone.PersephoneException;
import edu.pitt.gallowdd.persephone.PersephoneException.ErrorCode;
import edu.pitt.gallowdd.persephone.util.Utils;


/**
 * 
 * @author David Galloway
 *
 */
public class WeeklySchedule {
  
  private final Map<DayOfWeek, ActivityType[]> weeklySchedule;
  static private final int HOURS_IN_DAY = 24;
  
  /**
   * Create a default weekly schedule that has an agent staying home every hour of every day for the week
   */
  public WeeklySchedule()
  {
    this.weeklySchedule = new HashMap<>();
    
    for(DayOfWeek day : DayOfWeek.values())
    {
      ActivityType [] tempArr = new ActivityType[WeeklySchedule.HOURS_IN_DAY];
      for(int hour = 0; hour < tempArr.length; ++hour)
      {
        tempArr[hour] = ActivityType.HOME;
      }
      this.weeklySchedule.put(day, tempArr);
    }
  }
  
  /**
   * 
   * @param day
   * @param hour
   * @param activity
   * @return true if the agent is scheduled for the activity on the given day of the week and hour of the day, false otherwise
   * @throws PersephoneException if the hour is outside of the range 0 - 23
   */
  public boolean isScheduled(DayOfWeek day, int hour, ActivityType activity) throws PersephoneException
  {
    if(hour >= 0 && hour < WeeklySchedule.HOURS_IN_DAY)
    {
      return(this.weeklySchedule.get(day)[hour] == activity);
    }
    else
    {
      throw new PersephoneException(ErrorCode.ERR_HOUR_RANGE.message(String.valueOf(hour)));
    }
  }
  
  /**
   * @param day
   * @param hour
   * @return The scheduled activity for the given day of the week and hour of the day
   * @throws PersephoneException if the hour is outside of the range 0 - 23
   */
  public ActivityType getScheduledActivity(DayOfWeek day, int hour) throws PersephoneException
  {
    if(hour >= 0 && hour < WeeklySchedule.HOURS_IN_DAY)
    {
      return this.weeklySchedule.get(day)[hour];
    }
    else
    {
      throw new PersephoneException(ErrorCode.ERR_HOUR_RANGE.message(String.valueOf(hour)));
    }
  }
  
  /**
   * 
   * @param otherSchedule
   * @throws PersephoneException 
   */
  public void combine(WeeklySchedule otherSchedule) throws PersephoneException
  {
    for(DayOfWeek day : DayOfWeek.values())
    {
      for(int hour = 0; hour < WeeklySchedule.HOURS_IN_DAY; ++hour)
      {
        // If the other activity is higher rank than the one currently scheduled, do the other activity
        if(this.weeklySchedule.get(day)[hour].getRank() < otherSchedule.getScheduledActivity(day, hour).getRank())
        {
          this.weeklySchedule.get(day)[hour] = otherSchedule.getScheduledActivity(day, hour);
        }
        else if(this.weeklySchedule.get(day)[hour].getRank() == otherSchedule.getScheduledActivity(day, hour).getRank())
        {
          // If the activities are the same rank, then there is 50/50 chance of doing either one
          if(Utils.getRandom() < 0.5)
          {
            this.weeklySchedule.get(day)[hour] = otherSchedule.getScheduledActivity(day, hour);
          }
        }
      }
    }
  }
  
  /**
   * @param day
   * @param hour
   * @param activity
   * @throws PersephoneException if the hour is outside of the range 0 - 23
   */
  public void setScheduledActivity(DayOfWeek day, int hour, ActivityType activity) throws PersephoneException
  {
    if(hour >= 0 && hour < WeeklySchedule.HOURS_IN_DAY)
    {
      this.weeklySchedule.get(day)[hour] = activity;
    }
    else
    {
      throw new PersephoneException(ErrorCode.ERR_HOUR_RANGE.message(String.valueOf(hour)));
    }
  }
  
  @Override
  public String toString()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("WeeklySchedule:").append(System.lineSeparator());
    builder.append("Hour");
    for(DayOfWeek day : DayOfWeek.values())
    {
      builder.append(",").append(day.toString());
    }
    builder.append(System.lineSeparator());
    
    for(int hour = 0; hour < WeeklySchedule.HOURS_IN_DAY; ++hour)
    {
      builder.append(hour);
      for(DayOfWeek day : DayOfWeek.values())
      {
        ActivityType [] tempArr = this.weeklySchedule.get(day);
        builder.append(",").append(tempArr[hour].toString());
      }
      builder.append(System.lineSeparator());
    }
    
    return builder.toString();
  }
  
}
