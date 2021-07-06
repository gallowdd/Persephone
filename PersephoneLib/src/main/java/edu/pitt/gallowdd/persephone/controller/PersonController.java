package edu.pitt.gallowdd.persephone.controller;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Phaser;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import edu.pitt.gallowdd.persephone.messaging.Message;
import edu.pitt.gallowdd.persephone.overseer.OverseerControllerInterface;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * @author David Galloway
 *
 */
public class PersonController extends Controller {
  
  //private static final Logger LOGGER = LogManager.getLogger(PersonController.class.getName());

  /**
   * @param threadCommQueue
   * @param overseer 
   * @param phaser
   * @throws IdException if the id is not valid
   */
  public PersonController(ConcurrentLinkedQueue<Message> threadCommQueue, OverseerControllerInterface overseer, Phaser phaser) throws IdException
  {
    super(threadCommQueue, overseer, phaser);
  }
  
  /**
   * @param id 
   * @param threadCommQueue
   * @param overseer 
   * @param phaser
   * @throws IdException if the id is not valid
   */
  public PersonController(String id, ConcurrentLinkedQueue<Message> threadCommQueue, OverseerControllerInterface overseer, Phaser phaser) throws IdException
  {
    super(id, threadCommQueue, overseer, phaser);
  }
  
//  /**
//   * Maps person's Id to the person itself in this table.
//   * 
//   * @param person the agent to add
//   * @return the previous person associated with key, or @code{null} if there was no mapping for key
//   */
//  public boolean addPerson(Person person)
//  {
//    GenericAgent agt = this.addAgent(person);
//    if(agt == null)
//    {
//      return null;
//    }
//    else if(agt instanceof Person)
//    {
//      return (Person)agt;
//    }
//    else
//    {
//      PersonController.LOGGER.warn("PersonController had agent that was not of type Person: " + agt.getClass().toString());
//      return null;
//    }
//  }
//  
//  /**
//   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
//   * 
//   * @param personId the Id of the person to remove
//   * @return the removed agent or @code{null} if not found
//   */
//  public Person removePerson(String personId)
//  {
//    GenericAgent agt = this.removeAgent(personId);
//    if(agt == null)
//    {
//      return null;
//    }
//    else if(agt instanceof Person)
//    {
//      return (Person)agt;
//    }
//    else
//    {
//      PersonController.LOGGER.warn("PersonController had agent that was not of type Person: " + agt.getClass().toString());
//      return null;
//    }
//  }
//  
//  /**
//   * Removes the key (and its corresponding value) from this map. This method does nothing if the key is not in the map.
//   * 
//   * @param person the person to remove
//   * @return the removed agent or @code{null} if not found
//   */
//  public Person removePerson(Person person)
//  {
//    GenericAgent agt = this.removeAgent(person.getId());
//    if(agt == null)
//    {
//      return null;
//    }
//    else if(agt instanceof Person)
//    {
//      return (Person)agt;
//    }
//    else
//    {
//      PersonController.LOGGER.warn("PersonController had agent that was not of type Person: " + agt.getClass().toString());
//      return null;
//    }
//  }
}
