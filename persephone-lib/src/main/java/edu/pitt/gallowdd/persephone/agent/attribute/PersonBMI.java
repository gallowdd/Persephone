package edu.pitt.gallowdd.persephone.agent.attribute;

import org.apache.commons.statistics.distribution.ContinuousDistribution;
import org.apache.commons.statistics.distribution.LogNormalDistribution;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;

import edu.pitt.gallowdd.persephone.util.Constants;
import edu.pitt.gallowdd.persephone.util.Utils;

/**
 * Information for calculating the Body Mass Index (BMI) of a person given age and sex
 * 
 * NOTE: BMI is a calculated using the formulas below:
 * 
 * BMI = 703 * weight(lbs) / [height(in)]^2
 * BMI = mass(kg) / [height(m)]^2
 * 
 * @author David Galloway
 *
 */
public class PersonBMI {
  
  //These two values ensure that we don't produce BMI Values that are too far from the mean value
  private static final double MAX_CUM_PROB = .995;
  private static final double MIN_CUM_PROB = .005;
  
  private static final RangeMap<Double, Double> MALE_SCALE_BMI_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 4.0 * 12.0), 2.79586)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 2.79587)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 2.79587)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 2.81879)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 2.81941)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 2.88628)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 2.90766)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 2.96686)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 2.98991)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 2.98843)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 3.03686)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 3.08295)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 3.10095)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 3.13882)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 3.18692)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 3.18789)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 3.22647)
      .put(Range.closedOpen(20.0 * 12.0, 25.0 * 12.0), 3.22647)
      .put(Range.closedOpen(25.0 * 12.0, 30.0 * 12.0), 3.22412)
      .put(Range.closedOpen(30.0 * 12.0, 35.0 * 12.0), 3.29670)
      .put(Range.closedOpen(35.0 * 12.0, 40.0 * 12.0), 3.36038)
      .put(Range.closedOpen(40.0 * 12.0, 45.0 * 12.0), 3.36107)
      .put(Range.closedOpen(45.0 * 12.0, 50.0 * 12.0), 3.36107)
      .put(Range.closedOpen(50.0 * 12.0, 55.0 * 12.0), 3.36107)
      .put(Range.closedOpen(55.0 * 12.0, 60.0 * 12.0), 3.36107)
      .put(Range.closedOpen(60.0 * 12.0, 65.0 * 12.0), 3.36107)
      .put(Range.closedOpen(65.0 * 12.0, 70.0 * 12.0), 3.36877)
      .put(Range.closedOpen(70.0 * 12.0, 75.0 * 12.0), 3.36875)
      .put(Range.closedOpen(75.0 * 12.0, 80.0 * 12.0), 3.33620)
      .put(Range.closedOpen(80.0 * 12.0, 85.0 * 12.0), 3.31075)
      .put(Range.closedOpen(85.0 * 12.0, Double.MAX_VALUE), 3.28036)
      .build();
  
  private static final RangeMap<Double, Double> MALE_SHAPE_BMI_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 4.0 * 12.0), 0.08487)
      .put(Range.closedOpen(4.0 * 12, 5.0 * 12.0), 0.08487)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 0.08487)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 0.16312)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 0.16219)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 0.17451)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 0.20556)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 0.19628)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 0.21873)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 0.34759)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 0.30391)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 0.26018)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 0.25304)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 0.28209)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 0.20060)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 0.20182)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 0.24181)
      .put(Range.closedOpen(20.0 * 12.0, 25.0 * 12.0), 0.24182)
      .put(Range.closedOpen(25.0 * 12.0, 30.0 * 12.0), 0.41536)
      .put(Range.closedOpen(30.0 * 12.0, 35.0 * 12.0), 0.31643)
      .put(Range.closedOpen(35.0 * 12.0, 40.0 * 12.0), 0.24144)
      .put(Range.closedOpen(40.0 * 12.0, 45.0 * 12.0), 0.24085)
      .put(Range.closedOpen(45.0 * 12.0, 50.0 * 12.0), 0.24084)
      .put(Range.closedOpen(50.0 * 12.0, 55.0 * 12.0), 0.24084)
      .put(Range.closedOpen(55.0 * 12.0, 60.0 * 12.0), 0.24084)
      .put(Range.closedOpen(60.0 * 12.0, 65.0 * 12.0), 0.24084)
      .put(Range.closedOpen(65.0 * 12.0, 70.0 * 12.0), 0.20424)
      .put(Range.closedOpen(70.0 * 12.0, 75.0 * 12.0), 0.21063)
      .put(Range.closedOpen(75.0 * 12.0, 80.0 * 12.0), 0.18588)
      .put(Range.closedOpen(80.0 * 12.0, 85.0 * 12.0), 0.19108)
      .put(Range.closedOpen(85.0 * 12.0, Double.MAX_VALUE), 0.18085)
      .build();
  
  private static final RangeMap<Double, Double> FEMALE_SCALE_BMI_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 4.0 * 12.0), 2.7963)
      .put(Range.closedOpen(4.0 * 12.0, 5.0 * 12.0), 2.79023)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 2.79707)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 2.82935)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 2.86430)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 2.89844)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 2.93288)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 2.97216)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 3.01280)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 3.05413)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 3.05422)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 3.11087)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 3.12141)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 3.17602)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 3.17602)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 3.18445)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 3.26027)
      .put(Range.closedOpen(20.0 * 12.0, 25.0 * 12.0), 3.24021)
      .put(Range.closedOpen(25.0 * 12.0, 30.0 * 12.0), 3.27937)
      .put(Range.closedOpen(30.0 * 12.0, 35.0 * 12.0), 3.31685)
      .put(Range.closedOpen(35.0 * 12.0, 40.0 * 12.0), 3.33695)
      .put(Range.closedOpen(40.0 * 12.0, 45.0 * 12.0), 3.33696)
      .put(Range.closedOpen(45.0 * 12.0, 50.0 * 12.0), 3.37764)
      .put(Range.closedOpen(50.0 * 12.0, 55.0 * 12.0), 3.37848)
      .put(Range.closedOpen(55.0 * 12.0, 60.0 * 12.0), 3.37848)
      .put(Range.closedOpen(60.0 * 12.0, 65.0 * 12.0), 3.39283)
      .put(Range.closedOpen(65.0 * 12.0, 70.0 * 12.0), 3.39283)
      .put(Range.closedOpen(70.0 * 12.0, 75.0 * 12.0), 3.38117)
      .put(Range.closedOpen(75.0 * 12.0, 80.0 * 12.0), 3.38125)
      .put(Range.closedOpen(80.0 * 12.0, 85.0 * 12.0), 3.31602)
      .put(Range.closedOpen(85.0 * 12.0, Double.MAX_VALUE), 3.27462)
      .build();
  
  private static final RangeMap<Double, Double> FEMALE_SHAPE_BMI_BY_AGE_RANGE = 
      new ImmutableRangeMap.Builder<Double, Double>()
      .put(Range.closedOpen(0.0, 4.0 * 12.0), 0.07370)
      .put(Range.closedOpen(4.0 * 12, 5.0 * 12.0), 0.08004)
      .put(Range.closedOpen(5.0 * 12.0, 6.0 * 12.0), 0.09281)
      .put(Range.closedOpen(6.0 * 12.0, 7.0 * 12.0), 0.13565)
      .put(Range.closedOpen(7.0 * 12.0, 8.0 * 12.0), 0.14344)
      .put(Range.closedOpen(8.0 * 12.0, 9.0 * 12.0), 0.18178)
      .put(Range.closedOpen(9.0 * 12.0, 10.0 * 12.0), 0.20050)
      .put(Range.closedOpen(10.0 * 12.0, 11.0 * 12.0), 0.22000)
      .put(Range.closedOpen(11.0 * 12.0, 12.0 * 12.0), 0.21725)
      .put(Range.closedOpen(12.0 * 12.0, 13.0 * 12.0), 0.24711)
      .put(Range.closedOpen(13.0 * 12.0, 14.0 * 12.0), 0.24629)
      .put(Range.closedOpen(14.0 * 12.0, 15.0 * 12.0), 0.21486)
      .put(Range.closedOpen(15.0 * 12.0, 16.0 * 12.0), 0.23770)
      .put(Range.closedOpen(16.0 * 12.0, 17.0 * 12.0), 0.19974)
      .put(Range.closedOpen(17.0 * 12.0, 18.0 * 12.0), 0.19974)
      .put(Range.closedOpen(18.0 * 12.0, 19.0 * 12.0), 0.21608)
      .put(Range.closedOpen(19.0 * 12.0, 20.0 * 12.0), 0.23317)
      .put(Range.closedOpen(20.0 * 12.0, 25.0 * 12.0), 0.23815)
      .put(Range.closedOpen(25.0 * 12.0, 30.0 * 12.0), 0.23507)
      .put(Range.closedOpen(30.0 * 12.0, 35.0 * 12.0), 0.25630)
      .put(Range.closedOpen(35.0 * 12.0, 40.0 * 12.0), 0.27090)
      .put(Range.closedOpen(40.0 * 12.0, 45.0 * 12.0), 0.27090)
      .put(Range.closedOpen(45.0 * 12.0, 50.0 * 12.0), 0.24059)
      .put(Range.closedOpen(50.0 * 12.0, 55.0 * 12.0), 0.23856)
      .put(Range.closedOpen(55.0 * 12.0, 60.0 * 12.0), 0.23856)
      .put(Range.closedOpen(60.0 * 12.0, 65.0 * 12.0), 0.24146)
      .put(Range.closedOpen(65.0 * 12.0, 70.0 * 12.0), 0.24144)
      .put(Range.closedOpen(70.0 * 12.0, 75.0 * 12.0), 0.19106)
      .put(Range.closedOpen(75.0 * 12.0, 80.0 * 12.0), 0.19092)
      .put(Range.closedOpen(80.0 * 12.0, 85.0 * 12.0), 0.19088)
      .put(Range.closedOpen(85.0 * 12.0, Double.MAX_VALUE), 0.19511)
      .build();
  
  
  /**
   * @param ageInMonths the Person's age in months
   * @param sex the Person's sex
   * @return the value of BMI taken from a LogNormal Distribution
   */
  public static double getBMIByAgeAndSex(int ageInMonths, Sex sex)
  {
    return PersonBMI.getBMIByAgeAndSex((double)ageInMonths, sex);
  }
  
  /**
   * @param ageInMonths the Person's age in months
   * @param sex the Person's sex
   * @return the value of BMI taken from a LogNormal Distribution
   */
  public static double getBMIByAgeAndSex(double ageInMonths, Sex sex)
  {
    double retVal = Constants.DBL_UNSET;
    Double scale = Constants.DBL_UNSET;
    Double shape = Constants.DBL_UNSET;
    LogNormalDistribution logNormalDist = null;
    ContinuousDistribution.Sampler logNormalSampler = null;
    switch(sex)
    {
      case MALE:
        scale = PersonBMI.MALE_SCALE_BMI_BY_AGE_RANGE.get(ageInMonths);
        shape = PersonBMI.MALE_SHAPE_BMI_BY_AGE_RANGE.get(ageInMonths);
        if(scale == null || shape == null)
        {
          break;
        }
        logNormalDist = LogNormalDistribution.of(scale, shape);
        logNormalSampler = logNormalDist.createSampler(Utils.getRandomNumberSampler());
        retVal = logNormalSampler.sample();
        while(logNormalDist.cumulativeProbability(retVal) > PersonBMI.MAX_CUM_PROB || logNormalDist.cumulativeProbability(retVal) < PersonBMI.MIN_CUM_PROB)
        {
          retVal = logNormalSampler.sample();
        }
        break;
      case FEMALE:
        scale = PersonBMI.FEMALE_SCALE_BMI_BY_AGE_RANGE.get(ageInMonths);
        shape = PersonBMI.FEMALE_SHAPE_BMI_BY_AGE_RANGE.get(ageInMonths);
        if(scale == null || shape == null)
        {
          break;
        }
        logNormalDist = LogNormalDistribution.of(scale, shape);
        logNormalSampler = LogNormalDistribution.of(scale, shape).createSampler(Utils.getRandomNumberSampler());
        retVal = logNormalSampler.sample();
        while(logNormalDist.cumulativeProbability(retVal) > PersonBMI.MAX_CUM_PROB || logNormalDist.cumulativeProbability(retVal) < PersonBMI.MIN_CUM_PROB)
        {
          retVal = logNormalSampler.sample();
        }
        break;
      case UNSET:
        break;
      default:
        break;
    }
    return retVal;
  }
  
  /**
   * @param heightInInches the Person's height in Inches
   * @param bodyMassIndex the Person's Body Mass Index
   * @return the Person's weight in Pounds
   */
  public static double getWeightInPoundsByHeightInInchesAndBMI(int heightInInches, double bodyMassIndex)
  {
    return PersonBMI.getWeightInPoundsByHeightInInchesAndBMI((double)heightInInches, bodyMassIndex);
  }
  
  /**
   * @param heightInInches the Person's height in Inches
   * @param bodyMassIndex the Person's Body Mass Index
   * @return the Person's weight in Pounds
   */
  public static double getWeightInPoundsByHeightInInchesAndBMI(double heightInInches, double bodyMassIndex)
  {
    return bodyMassIndex * heightInInches * heightInInches / 703.0;
  }
  
  /**
   * @param heightInMeters the Person's height in Meters
   * @param bodyMassIndex the Person's Body Mass Index
   * @return the Person's weight in Pounds
   */
  public static double getWeightInKilogramsByHeightInMetersAndBMI(int heightInMeters, double bodyMassIndex)
  {
    return PersonBMI.getWeightInKilogramsByHeightInMetersAndBMI((double)heightInMeters, bodyMassIndex);
  }
  
  /**
   * @param heightInMeters the Person's height in Meters
   * @param bodyMassIndex the Person's Body Mass Index
   * @return the Person's weight in Pounds
   */
  public static double getWeightInKilogramsByHeightInMetersAndBMI(double heightInMeters, double bodyMassIndex)
  {
    return bodyMassIndex * heightInMeters * heightInMeters;
  }
}
