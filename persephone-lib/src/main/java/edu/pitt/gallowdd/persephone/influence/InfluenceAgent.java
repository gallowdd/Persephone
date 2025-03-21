package edu.pitt.gallowdd.persephone.influence;

import java.util.Map;

import edu.pitt.gallowdd.persephone.agent.Person;
import edu.pitt.gallowdd.persephone.agent.attribute.Race;
import edu.pitt.gallowdd.persephone.agent.attribute.Sex;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * 
 * @author David Galloway
 *
 */
public class InfluenceAgent extends Person {
  
  private Map<String, PositionModel> positions = null;
  
  /**
   * Create a new Person agent (used if not reading from an input file)
   * 
   * @param age the Person's age in years
   * @param sex the Person's sex
   * @param race the Person's race
   * 
   * @throws IdException if the id is not valid
   */
  public InfluenceAgent(int age, Sex sex, Race race) throws IdException
  {
    super(age, sex, race);
  }
  
  /**
   * Create a new Person agent (used if reading from an input file that has agent id already)
   * 
   * @param id - The Person's id
   * @param age the Person's age in years
   * @param sex the Person's sex
   * @param race the Person's race
   * 
   * @throws IdException if the id is not valid
   */
  public InfluenceAgent(String id, int age, Sex sex, Race race) throws IdException
  {
    super(id, age, sex, race);
  }
}
