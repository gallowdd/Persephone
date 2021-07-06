package edu.pitt.gallowdd.persephone.agent.activity;

import static org.junit.jupiter.api.Assertions.*;

import java.time.DayOfWeek;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.pitt.gallowdd.persephone.PersephoneException;
import edu.pitt.gallowdd.persephone.agent.activity.ActivityType;
import edu.pitt.gallowdd.persephone.agent.activity.ScheduleType;
import edu.pitt.gallowdd.persephone.agent.activity.WeeklySchedule;
import edu.pitt.gallowdd.persephone.agent.activity.WeeklyScheduler;

class WeeklyScheduleTest {

  @BeforeAll
  static void setUpBeforeClass() throws Exception
  {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception
  {
  }

  @BeforeEach
  void setUp() throws Exception
  {
  }

  @AfterEach
  void tearDown() throws Exception
  {
  }

  @Test
  final void test()
  {
    WeeklySchedule schedule = new WeeklySchedule();
    
    System.out.println(schedule.toString());
    
    try
    {
      schedule.setScheduledActivity(DayOfWeek.TUESDAY, 22, ActivityType.HOME_NEIGHBORHOOD);
    }
    catch(PersephoneException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    try
    {
      schedule.setScheduledActivity(DayOfWeek.TUESDAY, 12, ActivityType.WORKPLACE);
    }
    catch(PersephoneException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    
    System.out.println(schedule.toString());
    
    
    try
    {
      schedule.combine(WeeklyScheduler.generateWeeklySchedule(2020, 27, ScheduleType.WEEKEND_SHIFT_16HR_2ND_TURN_3RD_TURN));
    }
    catch(PersephoneException e)
    {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println(schedule.toString());
  }
  
  @Test
  final void testSetScheduledActivity_BadHour()
  {
    WeeklySchedule schedule = new WeeklySchedule();
    
    System.out.println("Try hour -1 ...");
    assertThrows(PersephoneException.class, () -> {
      schedule.setScheduledActivity(DayOfWeek.TUESDAY, -1, ActivityType.HOME_NEIGHBORHOOD);
    });
    
    System.out.println("Try hour 24 ...");
    assertThrows(PersephoneException.class, () -> {
      schedule.setScheduledActivity(DayOfWeek.TUESDAY, 24, ActivityType.HOME_NEIGHBORHOOD);
    });
  }
  
  @Test
  final void testGetScheduledActivity_BadHour()
  {
    WeeklySchedule schedule = new WeeklySchedule();
    
    System.out.println("Try hour -1 ...");
    assertThrows(PersephoneException.class, () -> {
      schedule.getScheduledActivity(DayOfWeek.TUESDAY, -1);
    });
    
    System.out.println("Try hour 24 ...");
    assertThrows(PersephoneException.class, () -> {
      schedule.getScheduledActivity(DayOfWeek.TUESDAY, 24);
    });
  }

}
