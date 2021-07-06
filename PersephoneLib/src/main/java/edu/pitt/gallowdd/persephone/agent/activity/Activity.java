package edu.pitt.gallowdd.persephone.agent.activity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * @author David Galloway
 *
 */
public abstract class Activity implements Schedulable {
  
  /**
   * @param year
   * @param weekOfYear
   * @param type 
   * @return 
   */
  public static Activity generateWeeklySchedule(int year, int weekOfYear, ScheduleType type)
  {
    switch(type)
    {
      case BANKER:
        break;
      case BANKER_PT:
        break;
      case PRIMARY_SCHOOL:
        break;
      case SECONDARY_SCHOOL:
        break;
      case SHIFT_16HR_1ST_TURN_2ND_TURN:
        break;
      case SHIFT_16HR_2ND_TURN_3RD_TURN:
        break;
      case SHIFT_24HR:
        break;
      case STEADY_1ST_TURN:
        break;
      case STEADY_1ST_TURN_PT:
        break;
      case STEADY_2ND_TURN:
        break;
      case STEADY_2ND_TURN_PT:
        break;
      case STEADY_3RD_TURN:
        break;
      case STEADY_3RD_TURN_PT:
        break;
      case WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN:
        break;
      case WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN:
        break;
      case WEEKEND_SHIFT_24HR:
        break;
      default:
        break;
      
    }
    return null;
  }
  
  private static boolean isBankHoliday(LocalDate checkDate)
  {
    // New Years Day
    if(checkDate.getDayOfYear() == 1)
    {
      return true;
    }
    
    // MLK (3rd Monday of January)
    if(checkDate.getMonth() == Month.JANUARY &&
        checkDate.getDayOfWeek() == DayOfWeek.MONDAY &&
        checkDate.minusDays(7 * 2).getMonth() == Month.JANUARY &&
        checkDate.minusDays(7 * 3).getMonth() == Month.DECEMBER)
    {
      return true;
    }
    
    // Memorial Day (4th Monday of May)
    if(checkDate.getMonth() == Month.MAY &&
        checkDate.getDayOfWeek() == DayOfWeek.MONDAY &&
        checkDate.minusDays(7 * 3).getMonth() == Month.MAY &&
        checkDate.minusDays(7 * 4).getMonth() == Month.APRIL)
    {
      return true;
    }
    
    /*
     *  4th of July (observed) (July 4th unless it falls on a weekend)
     *  -- 4th of July on Saturday means 3rd of July is holiday (observed)
     *  -- 4th of July on Sunday means 5th of July is holiday (observed)
     */
    if(checkDate.getMonth() == Month.JULY && checkDate.getDayOfMonth() == 4 &&
       (checkDate.getDayOfWeek() == DayOfWeek.MONDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.TUESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.THURSDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.FRIDAY))
    {
      return true;
    }
    else if(checkDate.getMonth() == Month.JULY && checkDate.getDayOfMonth() == 3 && checkDate.getDayOfWeek() == DayOfWeek.FRIDAY)
    {
      return true;
    }
    else if(checkDate.getMonth() == Month.JULY && checkDate.getDayOfMonth() == 5 && checkDate.getDayOfWeek() == DayOfWeek.MONDAY)
    {
      return true;
    }
    
    // Labor Day (1st Monday of September)
    if(checkDate.getMonth() == Month.SEPTEMBER &&
        checkDate.getDayOfWeek() == DayOfWeek.MONDAY &&
        checkDate.minusDays(7).getMonth() == Month.AUGUST)
    {
      return true;
    }
    
    // Thanksgiving Day (and day after)
    if(checkDate.getMonth() == Month.NOVEMBER &&
        (checkDate.getDayOfWeek() == DayOfWeek.THURSDAY || checkDate.getDayOfWeek() == DayOfWeek.FRIDAY) &&
        checkDate.minusDays(7 * 3).getMonth() == Month.NOVEMBER &&
        checkDate.minusDays(7 * 4).getMonth() == Month.OCTOBER)
    {
      return true;
    }
    
    /*
     *  Christmas Eve (December 24th unless it falls on a Saturday)
     *  -- Christmas Eve on Saturday means 23rd of December is holiday (observed)
     */
    if(checkDate.getMonth() == Month.DECEMBER && checkDate.getDayOfMonth() == 24 &&
       (checkDate.getDayOfWeek() == DayOfWeek.SUNDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.MONDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.TUESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.THURSDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.FRIDAY))
    {
      return true;
    }
    else if(checkDate.getMonth() == Month.DECEMBER && checkDate.getDayOfMonth() == 23 && checkDate.getDayOfWeek() == DayOfWeek.FRIDAY)
    {
      return true;
    }
    
    /*
     *  Christmas Day (December 25th unless it falls on a Sunday)
     *  -- Christmas Day on Sunday means 26th of December is holiday (observed)
     */
    if(checkDate.getMonth() == Month.DECEMBER && checkDate.getDayOfMonth() == 25 &&
       (checkDate.getDayOfWeek() == DayOfWeek.MONDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.TUESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.WEDNESDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.THURSDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.FRIDAY ||
        checkDate.getDayOfWeek() == DayOfWeek.SATURDAY))
    {
      return true;
    }
    else if(checkDate.getMonth() == Month.DECEMBER && checkDate.getDayOfMonth() == 26 && checkDate.getDayOfWeek() == DayOfWeek.MONDAY)
    {
      return true;
    }
    
    return false;
  }
  
  private static boolean isBankHoliday(LocalDate checkDate, int localization)
  {
    if(localization == 1)
    {
      return Activity.isBankHoliday(checkDate);
    }
    else
    {
      return false;
    }
  }
//  
//  public static Activity combine(Activity ... activities)
//  {
//    
//  }
}
