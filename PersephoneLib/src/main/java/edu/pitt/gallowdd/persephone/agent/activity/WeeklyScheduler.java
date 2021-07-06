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

package edu.pitt.gallowdd.persephone.agent.activity;

import java.time.DateTimeException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pitt.gallowdd.persephone.PersephoneException;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * 
 * @author David Galloway
 *
 */
public class WeeklyScheduler {
  
  private static final Map<DayOfWeek, Integer> DAYS_OF_WEEK_MAP = new HashMap<>();
  private static final List<DayOfWeek> DAYS_OF_WEEK_ARR_LIST = new ArrayList<>();
  
  static {
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.SUNDAY, 0);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.MONDAY, 1);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.TUESDAY, 2);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.WEDNESDAY, 3);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.THURSDAY, 4);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.FRIDAY, 5);
    WeeklyScheduler.DAYS_OF_WEEK_MAP.put(DayOfWeek.SATURDAY, 6);
    
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.SUNDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.MONDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.TUESDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.WEDNESDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.THURSDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.FRIDAY);
    WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.add(DayOfWeek.SATURDAY);
  }
  
  /**
   * @param year
   * @param weekOfYear
   * @param type 
   * @return a new WeeklySchedule
   * @throws PersephoneException 
   */
  public static WeeklySchedule generateWeeklySchedule(int year, int weekOfYear, ScheduleType type) throws PersephoneException
  {
    WeeklySchedule retVal = new WeeklySchedule();
    
    try
    {
      LocalDate myDate = LocalDate.ofYearDay(year, 1);
      
      // Start the week on a SUNDAY
      switch(myDate.getDayOfWeek())
      {
        case MONDAY:
          myDate = myDate.minusDays(1);
          break;
        case TUESDAY:
          myDate = myDate.minusDays(2);
          break;
        case WEDNESDAY:
          myDate = myDate.minusDays(3);
          break;
        case THURSDAY:
          myDate = myDate.minusDays(4);
          break;
        case FRIDAY:
          myDate = myDate.minusDays(5);
          break;
        case SATURDAY:
          myDate = myDate.minusDays(6);
          break;
        default:
          break;
      }
      // Push forward weekOfYear
      myDate = myDate.plusWeeks(weekOfYear - 1);
      
      System.out.println("DEBUG:" + myDate.toString());
      
      switch(type)
      {
        case BANKER:
          WeeklyScheduler.generateBankerSchedule(myDate, retVal);
          break;
        case BANKER_PT:
          WeeklyScheduler.generatePartTimeBankerSchedule(myDate, retVal);
          break;
        case PRIMARY_SCHOOL:
          break;
        case SECONDARY_SCHOOL:
          break;
        case SHIFT_16HR_1ST_TURN_2ND_TURN:
          WeeklyScheduler.generateShiftFirstAndSecondTurnWorkerSchedule(myDate, retVal);
          break;
        case SHIFT_16HR_2ND_TURN_3RD_TURN:
          WeeklyScheduler.generateShiftSecondAndThirdTurnWorkerSchedule(myDate, retVal);
          break;
        case SHIFT_24HR:
          WeeklyScheduler.generateShiftWorkerSchedule(myDate, retVal);
          break;
        case STEADY_1ST_TURN:
          WeeklyScheduler.generateSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_1ST_TURN);
          break;
        case STEADY_1ST_TURN_PT:
          WeeklyScheduler.generatePartTimeSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_1ST_TURN_PT);
          break;
        case STEADY_2ND_TURN:
          WeeklyScheduler.generateSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_2ND_TURN);
          break;
        case STEADY_2ND_TURN_PT:
          WeeklyScheduler.generatePartTimeSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_2ND_TURN_PT);
          break;
        case STEADY_3RD_TURN:
          WeeklyScheduler.generateSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_3RD_TURN);
          break;
        case STEADY_3RD_TURN_PT:
          WeeklyScheduler.generatePartTimeSteadyShiftWorkerSchedule(myDate, retVal, ScheduleType.STEADY_3RD_TURN_PT);
          break;
        case WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN:
          WeeklyScheduler.generateWeekendShiftWorkerSchedule(myDate, retVal, ScheduleType.WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN);
          break;
        case WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN:
          WeeklyScheduler.generateWeekendShiftWorkerSchedule(myDate, retVal, ScheduleType.WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN);
          break;
        case WEEKEND_SHIFT_24HR:
          WeeklyScheduler.generateWeekendShiftWorkerSchedule(myDate, retVal, ScheduleType.WEEKEND_SHIFT_24HR);
          break;
        default:
          break;
      }
    }
    catch(DateTimeException e)
    {
      throw new PersephoneException(e);
    }
    
    return retVal;
  }
  
  private static boolean isUnitedStatesBankHoliday(LocalDate checkDate)
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
  
//  private static boolean isBankHoliday(LocalDate checkDate, int localization)
//  {
//    if(localization == 1)
//    {
//      return WeeklyScheduler.isUnitedStatesBankHoliday(checkDate);
//    }
//    else
//    {
//      return false;
//    }
//  }
  
  /**
   * Generate schedule for Mon - Fri, 08:00 - 16:59 / Holidays Off
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateBankerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule) throws PersephoneException
  {
    LocalDate testDate = myDate;
    for(int i = 0; i < 7; ++i)
    {
      if(testDate.getDayOfWeek() != DayOfWeek.SATURDAY && testDate.getDayOfWeek() != DayOfWeek.SUNDAY && !WeeklyScheduler.isUnitedStatesBankHoliday(testDate))
      {
        for(int hour = 8; hour < 17; ++hour)
        {
          // 60% of time will be in office
          // 12 Noon will be in WORK_NEIGHBORHOOD for hour
          if(hour == 12)
          {
            weeklySchedule.setScheduledActivity(testDate.getDayOfWeek(), hour, ActivityType.WORK_NEIGHBORHOOD);
          }
          else
          {
            if(Utils.getRandom() < 0.6)
            {
              weeklySchedule.setScheduledActivity(testDate.getDayOfWeek(), hour, ActivityType.OFFICE);
            }
            else
            {
              weeklySchedule.setScheduledActivity(testDate.getDayOfWeek(), hour, ActivityType.WORKPLACE);
            }
          }
        }
      }
      testDate = testDate.plusDays(1);
    }
  }
  
  /**
   * Generate Part-Time (2-4 Days per week) schedule for Mon - Fri, 08:00 - 16:59 / Holidays Off
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generatePartTimeBankerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule) throws PersephoneException
  {
    int daysWorkingTemp = 2 + Utils.getRandomInt(3); // 2 - 4 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      
      // No Weekend work
      if(tempDayOfWeek == DayOfWeek.SATURDAY || tempDayOfWeek == DayOfWeek.SUNDAY)
      {
        ++daysWorkingTemp; // So I can draw again
        continue;
      }
      
      if(WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      for(int hour = 8; hour < 17; ++hour)
      {
        // 60% of time will be in office
        // 12 Noon will be in WORK_NEIGHBORHOOD for hour
        if(hour == 12)
        {
          double randVal = Utils.getRandom();
          if(randVal < 0.6)
          {
            weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORK_NEIGHBORHOOD);
          }
          else if(randVal < 0.85)
          {
            weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
          }
          else
          {
            weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
          }
        }
        else
        {
          if(Utils.getRandom() < 0.6)
          {
            weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
          }
          else
          {
            weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
          }
        }
      }
    }
  }
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected. 
   * 5 days selected from Sun - Sat. 08:00 - 15:59, 16:00 - 23:59
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateShiftFirstAndSecondTurnWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule) throws PersephoneException
  {
    int daysWorkingTemp = 5; // 5 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    boolean isFirstTurn = Utils.getRandom() < 0.5;
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      for(int hour = (isFirstTurn ? 8 : 16); hour < (isFirstTurn ? 16 : 24); ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
  
  /**
   * Two shifts covered 16 hours a day. Each shift has .5 probability of being selected. 
   * 5 days selected from Sun - Sat. 16:00 - 23:59, 00:00 - 07:59
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateShiftSecondAndThirdTurnWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule) throws PersephoneException
  {
    int daysWorkingTemp = 5; // 5 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    boolean isSecondTurn = Utils.getRandom() < 0.5;
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      for(int hour = (isSecondTurn ? 16 : 0); hour < (isSecondTurn ? 24 : 8); ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
  
  /**
   * Three shifts covered 24 hours a day. Each shift has 0.33 probability of being selected.
   * 5 Days selected from Sun - Sat. 08:00 - 15:59, 16:00 - 23:59, 00:00 - 07:59
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateShiftWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule) throws PersephoneException
  {
    int daysWorkingTemp = 5; // 5 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    double randomShift = Utils.getRandom();
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    int startHour = 0;
    int endHour = 0;
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      if(randomShift < 0.333)
      {
        startHour = 0;
        endHour = 8;
      }
      else if(randomShift < 0.667)
      {
        startHour = 8;
        endHour = 16;
      }
      else
      {
        startHour = 16;
        endHour = 24;
      }
      
      for(int hour = startHour; hour < endHour; ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
  
  /**
   * STEADY_1ST_TURN:
   * - 5 Days selected from Sun - Sat, 08:00 - 15:59
   * 
   * STEADY_2ND_TURN:
   * - 5 Days selected from Sun - Sat, 16:00 - 23:59
   * 
   * STEADY_3RD_TURN:
   * - 5 Days selected from Sun - Sat, 00:00 - 07:59
   * 
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateSteadyShiftWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule, ScheduleType type) throws PersephoneException
  {
    int daysWorkingTemp = 5; // 5 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    int startHour = 0;
    int endHour = 0;
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      if(type == ScheduleType.STEADY_1ST_TURN)
      {
        startHour = 8;
        endHour = 16;
      }
      else if(type == ScheduleType.STEADY_2ND_TURN)
      {
        startHour = 16;
        endHour = 24;
      }
      else
      {
        startHour = 0;
        endHour = 8;
      }
      
      for(int hour = startHour; hour < endHour; ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
  
  /**
   * STEADY_1ST_TURN_PT:
   * - 2-4 Days selected from Sun - Sat, 08:00 - 15:59
   * 
   * STEADY_2ND_TURN_PT:
   * - 2-4 Days selected from Sun - Sat, 16:00 - 23:59
   * 
   * STEADY_3RD_TURN_PT:
   * - 2-4 Days selected from Sun - Sat, 00:00 - 07:59
   * 
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generatePartTimeSteadyShiftWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule, ScheduleType type) throws PersephoneException
  {
    int daysWorkingTemp = 2 + Utils.getRandomInt(3); // 2 - 4 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    int startHour = 0;
    int endHour = 0;
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      if(type == ScheduleType.STEADY_1ST_TURN_PT)
      {
        startHour = 8;
        endHour = 16;
      }
      else if(type == ScheduleType.STEADY_2ND_TURN_PT)
      {
        startHour = 16;
        endHour = 24;
      }
      else
      {
        startHour = 0;
        endHour = 8;
      }
      
      for(int hour = startHour; hour < endHour; ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
  
  /**
   * WEEKEND_SHIFT_24HR:
   * - Three shifts covered 24 hours a day. Each shift has 0.33 probability of being selected. 
   *   1 or 2 Days selected from Sat & Sun 08:00 - 15:59, 16:00 - 23:59, 00:00 - 07:59
   * 
   * WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN:
   * - Two shifts covered 16 hours a day. Each shift has .5 probability of being selected.
   *   1 or 2 Days elected from Sat & Sun. 08:00 - 15:59, 16:00 - 23:59
   * 
   * WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN:
   * - Two shifts covered 16 hours a day. Each shift has .5 probability of being selected.
   *   1 or 2 Days elected from Sat & Sun. 16:00 - 23:59, 08:00 - 15:59
   *   
   * @param myDate the LocalDate of the Sunday for the given week
   * @param weeklySchedule the WeeklySchedule to update
   * @throws PersephoneException if the hour is out of bounds
   */
  private static void generateWeekendShiftWorkerSchedule(LocalDate myDate, WeeklySchedule weeklySchedule, ScheduleType type) throws PersephoneException
  {
    int daysWorkingTemp = 1 + Utils.getRandomInt(2); // 1 or 2 days
    Collections.shuffle(WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST);
    double randomShift = Utils.getRandom();
    boolean isHolidayWorker = Utils.getRandom() < 0.85; // 85% of shift workers don't get holidays off
    int startHour = 0;
    int endHour = 0;
    for(int i = 0; i < daysWorkingTemp; ++i)
    {
      // Check if the date is a holiday
      LocalDate testDate = myDate;
      DayOfWeek tempDayOfWeek = WeeklyScheduler.DAYS_OF_WEEK_ARR_LIST.get(i);
      int daysToAdd = WeeklyScheduler.DAYS_OF_WEEK_MAP.get(tempDayOfWeek);
      
      if(tempDayOfWeek != DayOfWeek.SATURDAY && tempDayOfWeek != DayOfWeek.SUNDAY)
      {
        ++daysWorkingTemp; // So I can draw again
        continue;
      }
      
      if(!isHolidayWorker && WeeklyScheduler.isUnitedStatesBankHoliday(testDate.plusDays(daysToAdd)))
      {
        continue;
      }
      
      if(type == ScheduleType.WEEKEND_SHIFT_24HR) {
        if(randomShift < 0.333)
        {
          startHour = 0;
          endHour = 8;
        }
        else if(randomShift < 0.667)
        {
          startHour = 8;
          endHour = 16;
        }
        else
        {
          startHour = 16;
          endHour = 24;
        }
      }
      else if(type == ScheduleType.WEEKEND_SHIFT_16HR_1ST_TURN_2ND_TURN)
      {
        if(randomShift < 0.5)
        {
          startHour = 8;
          endHour = 16;
        }
        else
        {
          startHour = 16;
          endHour = 24;
        }
      }
      else // WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN
      {
        if(randomShift < 0.5)
        {
          startHour = 0;
          endHour = 8;
        }
        else
        {
          startHour = 16;
          endHour = 24;
        }
      }
      
      for(int hour = startHour; hour < endHour; ++hour)
      {
        // 40% of time will be in office
        if(Utils.getRandom() < 0.4)
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.OFFICE);
        }
        else
        {
          weeklySchedule.setScheduledActivity(tempDayOfWeek, hour, ActivityType.WORKPLACE);
        }
      }
    }
  }
}
