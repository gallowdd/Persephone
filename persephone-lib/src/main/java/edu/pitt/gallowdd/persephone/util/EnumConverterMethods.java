package edu.pitt.gallowdd.persephone.util;

import edu.pitt.gallowdd.persephone.agent.attribute.CensusRelationship;
import edu.pitt.gallowdd.persephone.agent.attribute.Race;
import edu.pitt.gallowdd.persephone.agent.attribute.Sex;

/**
 * @author David Galloway
 *
 */
public class EnumConverterMethods {

  /**
   * @param sexCode
   * @return The Sex that matches the integer code
   * @throws EnumNotFoundException if the integer code is not recognized
   */
  public static Sex intToSexEnum(int sexCode) throws EnumNotFoundException
  {
    return Sex.valueOf(sexCode);
  }
  
  /**
   * @param sex
   * @return The Sex that matches the sex string (M or F)
   * @throws EnumNotFoundException if the integer code is not recognized
   */
  public static Sex stringToSexEnum(String sex) throws EnumNotFoundException
  {
    for(Sex sexEnum : Sex.values())
    { 
      if(sexEnum.toString().equals(sex))
      {
        return sexEnum;
      }
    }
    throw new EnumNotFoundException(sex, Sex.class);
  }
  
  /**
   * @param raceCode
   * @return Race that matches the integer code
   * @throws EnumNotFoundException if the integer code is not recognized
   */
  public static Race intToRaceEnum(int raceCode) throws EnumNotFoundException
  {
    return Race.valueOf(raceCode);
  }
  
  /**
   * @param relationshipCode
   * @return CensusRelationship that matches the integer code
   * @throws EnumNotFoundException if the integer code is not recognized
   */
  public static CensusRelationship intToRelationshipEnum(int relationshipCode) throws EnumNotFoundException
  {
    return CensusRelationship.valueOf(relationshipCode);
  }
}
