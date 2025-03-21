/**
 * 
 */
package edu.pitt.gallowdd.persephone.util;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author gallowdd
 *
 */
class UtilsTest {

  /**
   * @throws java.lang.Exception
   */
  @BeforeAll
  static void setUpBeforeClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterAll
  static void tearDownAfterClass() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @BeforeEach
  void setUp() throws Exception
  {
  }

  /**
   * @throws java.lang.Exception
   */
  @AfterEach
  void tearDown() throws Exception
  {
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandomBirthdate(int)}.
   */
  @Test
  final void testGetRandomBirthdateInt()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandomBirthdate(int, java.time.LocalDate)}.
   */
  @Test
  final void testGetRandomBirthdateIntLocalDate()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandom()}.
   */
  @Test
  final void testGetRandom()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandomInt(int)}.
   */
  @Test
  final void testGetRandomInt()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandomNumberGenerator()}.
   */
  @Test
  final void testGetRandomNumberGenerator()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getResource(java.lang.String)}.
   */
  @Test
  final void testGetResource()
  {
    fail("Not yet implemented"); // TODO
  }

  /**
   * Test method for {@link edu.pitt.gallowdd.persephone.util.Utils#getRandomIntFromArrayOfWeights(java.lang.Double[])}.
   */
  @Test
  final void testGetRandomIntFromArrayOfWeights()
  {
    double [] myWeights = {1.0, 3.0, 9.0, 9.1};
    int [] myCounts = new int[myWeights.length];
    int attempts = 1000000;
    
    for(int i = 0; i < myWeights.length; ++i)
    {
      myCounts[i] = 0;
    }
    
    for(int i = 0; i < attempts; ++i)
    {
      int index = Utils.getRandomIntFromArrayOfWeights(myWeights);
      ++myCounts[index];
    }
    
    for(int i = 0; i < myWeights.length; ++i)
    {
      System.out.println("[" + i + "] = " + (double)myCounts[i] / (double)attempts);
    }
  }

}
