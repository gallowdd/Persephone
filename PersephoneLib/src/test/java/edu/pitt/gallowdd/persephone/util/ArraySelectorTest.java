/**
 * 
 */
package edu.pitt.gallowdd.persephone.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author David Galloway
 *
 */
class ArraySelectorTest {

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
   * Test method for {@link edu.pitt.gallowdd.persephone.util.ArraySelector#getRandomItemFromArrayGivenArrayOfWeights(T[], double[])}.
   */
  @Test
  final void testGetRandomItemFromArrayGivenArrayOfWeights()
  {
    String[] myItems = {"Wayne", "Dave", "Bob", "Bill", "Mike"};
    double[] myWeights = {.1, .3, .2, .15, .25};
    Map<String, Integer> itemCountMap = new HashMap<>();
    int attempts = 1000000;
    ArraySelector<String> mySelector = new ArraySelector<>();
    
    for(int i = 0; i < myItems.length; ++i)
    {
      itemCountMap.put(myItems[i], 0);
    }
    
    for(int i = 0; i < attempts; ++i)
    {
      try
      {
        String item = mySelector.getRandomItemFromArrayGivenArrayOfWeights(myItems, myWeights);
        itemCountMap.put(item, itemCountMap.get(item) + 1);
      }
      catch (ArraySelectorException e)
      {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
    itemCountMap.forEach((item, count) -> {
      System.out.println("item = " + item 
          + ", count = " + count 
          + ", probability = " + (double)count / (double)attempts);
    });
  }

}
