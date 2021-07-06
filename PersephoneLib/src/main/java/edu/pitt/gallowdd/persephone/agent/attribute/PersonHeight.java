package edu.pitt.gallowdd.persephone.agent.attribute;

import org.apache.commons.math3.distribution.NormalDistribution;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * Information for calculating the height of a person given age and sex
 * 
 * @author David Galloway
 *
 */
public class PersonHeight {
  
  // These two values ensure that we don't produce Heights that are more too far from the mean value
  private static final double MAX_CUM_PROB = .995;
  private static final double MIN_CUM_PROB = .005;
  
  private static final RangeMap<Double, Double> MALE_MEAN_HEIGHT_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 1.0), 19.7)
      .put(Range.closedOpen(1.0, 2.0), 21.5)
      .put(Range.closedOpen(2.0, 3.0), 23.0)
      .put(Range.closedOpen(3.0, 6.0), 24.1)
      .put(Range.closedOpen(6.0, 9.0), 26.6)
      .put(Range.closedOpen(9.0, 12.0), 28.3)
      .put(Range.closedOpen(12.0, 18.0), 29.8)
      .put(Range.closedOpen(18.0, 24.0), 32.4)
      .put(Range.closedOpen(24.0, 36.0), 34.5)
      .put(Range.closedOpen(36.0, 4.0 * 12.0), 37.1)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 40.1)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 43.1)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 45.7)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 48.1)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 50.2)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 52.2)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 54.3)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 56.6)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 59.1)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 61.8)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 64.3)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 65.5)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 68.2)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 69.2)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 69.5)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 69.4)
      .put(Range.closedOpen(20.0 * 12.0, 30.0 * 12.0), 69.4)
      .put(Range.closedOpen(30.0 * 12.0, 40.0 * 12.0), 69.4)
      .put(Range.closedOpen(40.0 * 12.0, 50.0 * 12.0), 69.6)
      .put(Range.closedOpen(50.0 * 12.0, 60.0 * 12.0), 69.5)
      .put(Range.closedOpen(60.0 * 12.0, 70.0 * 12.0), 68.9)
      .put(Range.closedOpen(70.0 * 12.0, 80.0 * 12.0), 68.2)
      .put(Range.closedOpen(80.0 * 12.0, Double.MAX_VALUE), 67.2)
      .build();
  
  private static final RangeMap<Double, Double> MALE_STDDEV_HEIGHT_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 1.0), 0.74)
      .put(Range.closedOpen(1.0, 2.0), 0.76)
      .put(Range.closedOpen(2.0, 3.0), 0.78)
      .put(Range.closedOpen(3.0, 6.0), 0.8)
      .put(Range.closedOpen(6.0, 9.0), 0.84)
      .put(Range.closedOpen(9.0, 12.0), 0.88)
      .put(Range.closedOpen(12.0, 18.0), 0.93)
      .put(Range.closedOpen(18.0, 24.0), 1.06)
      .put(Range.closedOpen(24.0, 36.0), 1.21)
      .put(Range.closedOpen(36.0, 4.0 * 12.0), 1.59)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 1.71)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 1.81)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 1.94)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 2.11)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 2.31)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 2.51)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 2.71)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 2.87)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 2.99)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 3.04)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 3.03)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 2.97)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 2.89)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 2.8)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 2.74)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 2.75)
      .put(Range.closedOpen(20.0 * 12.0, 30.0 * 12.0), 2.86)
      .put(Range.closedOpen(30.0 * 12.0, 40.0 * 12.0), 2.86)
      .put(Range.closedOpen(40.0 * 12.0, 50.0 * 12.0), 2.91)
      .put(Range.closedOpen(50.0 * 12.0, 60.0 * 12.0), 2.98)
      .put(Range.closedOpen(60.0 * 12.0, 70.0 * 12.0), 2.92)
      .put(Range.closedOpen(70.0 * 12.0, 80.0 * 12.0), 2.74)
      .put(Range.closedOpen(80.0 * 12.0, Double.MAX_VALUE), 2.61)
      .build();
  
  private static final RangeMap<Double, Double> FEMALE_MEAN_HEIGHT_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 1.0), 19.4)
      .put(Range.closedOpen(1.0, 2.0), 21.1)
      .put(Range.closedOpen(2.0, 3.0), 22.5)
      .put(Range.closedOpen(3.0, 6.0), 23.5)
      .put(Range.closedOpen(6.0, 9.0), 25.8)
      .put(Range.closedOpen(9.0, 12.0), 27.6)
      .put(Range.closedOpen(12.0, 18.0), 29.1)
      .put(Range.closedOpen(18.0, 24.0), 31.8)
      .put(Range.closedOpen(24.0, 36.0), 33.9)
      .put(Range.closedOpen(36.0, 4.0 * 12.0), 36.8)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 39.8)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 42.6)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 45.1)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 47.6)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 50.0)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 52.4)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 54.8)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 57.1)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 59.4)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 61.3)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 62.9)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 63.9)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 64.4)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 64.3)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 64.1)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 64.0)
      .put(Range.closedOpen(20.0 * 12.0, 30.0 * 12.0), 64.2)
      .put(Range.closedOpen(30.0 * 12.0, 40.0 * 12.0), 64.3)
      .put(Range.closedOpen(40.0 * 12.0, 50.0 * 12.0), 64.2)
      .put(Range.closedOpen(50.0 * 12.0, 60.0 * 12.0), 63.9)
      .put(Range.closedOpen(60.0 * 12.0, 70.0 * 12.0), 63.6)
      .put(Range.closedOpen(70.0 * 12.0, 80.0 * 12.0), 62.6)
      .put(Range.closedOpen(80.0 * 12.0, Double.MAX_VALUE), 61.4)
      .build();
  
  private static final RangeMap<Double, Double> FEMALE_STDDEV_HEIGHT_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 1.0), 0.74)
      .put(Range.closedOpen(1.0, 2.0), 0.77)
      .put(Range.closedOpen(2.0, 3.0), 0.8)
      .put(Range.closedOpen(3.0, 6.0), 0.83)
      .put(Range.closedOpen(6.0, 9.0), 0.89)
      .put(Range.closedOpen(9.0, 12.0), 0.95)
      .put(Range.closedOpen(12.0, 18.0), 1.02)
      .put(Range.closedOpen(18.0, 24.0), 1.14)
      .put(Range.closedOpen(24.0, 36.0), 1.28)
      .put(Range.closedOpen(36.0, 4.0 * 12.0), 1.61)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 1.79)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 1.93)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 2.1)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 2.28)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 2.46)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 2.63)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 2.74)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 2.8)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 2.8)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 2.75)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 2.66)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 2.58)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 2.53)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 2.52)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 2.55)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 2.57)
      .put(Range.closedOpen(20.0 * 12.0, 30.0 * 12.0), 2.86)
      .put(Range.closedOpen(30.0 * 12.0, 40.0 * 12.0), 2.8)
      .put(Range.closedOpen(40.0 * 12.0, 50.0 * 12.0), 2.74)
      .put(Range.closedOpen(50.0 * 12.0, 60.0 * 12.0), 2.43)
      .put(Range.closedOpen(60.0 * 12.0, 70.0 * 12.0), 2.43)
      .put(Range.closedOpen(70.0 * 12.0, 80.0 * 12.0), 2.6)
      .put(Range.closedOpen(80.0 * 12.0, Double.MAX_VALUE), 2.49)
      .build();
  
//  private static final RangeMap<Double, NormalDistribution> MALE_NORMAL_DIST_HEIGHT_BY_AGE_RANGE = null;
//  private static final RangeMap<Double, NormalDistribution> FEMALE_NORMAL_DIST_HEIGHT_BY_AGE_RANGE = null;
  
  static {
//    final Double [] ageInMonthArr = {0.0, 1.0, 2.0, 3.0, 6.0, 9.0, 12.0, 
//                               18.0, 24.0, 36.0, 48.0, 60.0, 72.0, 
//                               84.0, 96.0, 108.0, 120.0, 132.0, 144.0, 
//                               156.0, 168.0, 180.0, 192.0, 204.0, 216.0, 
//                               228.0, 240.0, 360.0, 480.0, 600.0, 720.0,
//                               840.0, 960.0, Double.MAX_VALUE};
//    final List<Double> ageInMonthList = Arrays.asList(ageInMonthArr);
//    
//    for(int i = 0; i < ageInMonthList.size() - 1; ++i)
//    {
//      double mean = PersonHeight.MALE_MEAN_HEIGHT_BY_AGE_RANGE.get(ageInMonthList.get(i));
//      double stddev = PersonHeight.MALE_STDDEV_HEIGHT_BY_AGE_RANGE.get(ageInMonthList.get(i));
//      NormalDistribution maleHeightNormalDist = new NormalDistribution(Utils.getRandomNumberGenerator(), mean, stddev);
//      PersonHeight.MALE_NORMAL_DIST_HEIGHT_BY_AGE_RANGE.put(Range.closedOpen(ageInMonthList.get(i), ageInMonthList.get(i + 1)), maleHeightNormalDist);
//    }
//    
//    for(int i = 0; i < ageInMonthList.size() - 1; ++i)
//    {
//      double mean = PersonHeight.FEMALE_MEAN_HEIGHT_BY_AGE_RANGE.get(ageInMonthList.get(i));
//      double stddev = PersonHeight.FEMALE_STDDEV_HEIGHT_BY_AGE_RANGE.get(ageInMonthList.get(i));
//      NormalDistribution maleHeightNormalDist = new NormalDistribution(Utils.getRandomNumberGenerator(), mean, stddev);
//      PersonHeight.FEMALE_NORMAL_DIST_HEIGHT_BY_AGE_RANGE.put(Range.closedOpen(ageInMonthList.get(i), ageInMonthList.get(i + 1)), maleHeightNormalDist);
//    }
  }
  
  /**
   * @param ageInMonths the Person's age in months
   * @param sex the Person's sex
   * @return the value of height in Inches taken from a Normal Distribution
   */
  public static double getHeightInInchesByAgeAndSex(int ageInMonths, Sex sex)
  {
    return PersonHeight.getHeightInInchesByAgeAndSex((double)ageInMonths, sex);
  }
  
  /**
   * @param ageInMonths the Person's age in months
   * @param sex the Person's sex
   * @return the value of height in Inches taken from a Normal Distribution
   */
  public static double getHeightInInchesByAgeAndSex(double ageInMonths, Sex sex)
  {
    double retVal = Constants.DBL_UNSET;
    Double mean = Constants.DBL_UNSET;
    Double stddev = Constants.DBL_UNSET;
    NormalDistribution normalDist = null;
    switch(sex)
    {
      case MALE:
        mean = PersonHeight.MALE_MEAN_HEIGHT_BY_AGE_RANGE.get(ageInMonths);
        stddev = PersonHeight.MALE_STDDEV_HEIGHT_BY_AGE_RANGE.get(ageInMonths);
        if(mean == null || stddev == null)
        {
          break;
        }
        
        normalDist = new NormalDistribution(Utils.getRandomNumberGenerator(), mean, stddev);
        
        retVal = normalDist.sample();
        while(normalDist.cumulativeProbability(retVal) > PersonHeight.MAX_CUM_PROB || normalDist.cumulativeProbability(retVal) < PersonHeight.MIN_CUM_PROB)
        {
          retVal = normalDist.sample();
        }
        break;
      case FEMALE:
        mean = PersonHeight.FEMALE_MEAN_HEIGHT_BY_AGE_RANGE.get(ageInMonths);
        stddev = PersonHeight.FEMALE_STDDEV_HEIGHT_BY_AGE_RANGE.get(ageInMonths);
        if(mean == null || stddev == null)
        {
          break;
        }
        normalDist = new NormalDistribution(Utils.getRandomNumberGenerator(), mean, stddev);
        while(normalDist.cumulativeProbability(retVal) > PersonHeight.MAX_CUM_PROB || normalDist.cumulativeProbability(retVal) < PersonHeight.MIN_CUM_PROB)
        {
          retVal = normalDist.sample();
        }
        retVal = normalDist.sample();
        break;
      case UNSET:
        break;
      default:
        break;
    }
    return retVal;
  }
}
