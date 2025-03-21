package edu.pitt.gallowdd.persephone.influence;

import edu.pitt.gallowdd.persephone.controller.Controller;
import edu.pitt.gallowdd.persephone.util.IdException;

/**
 * 
 * @author ddg5
 *
 * @param <T>
 */
public class InfluenceController extends Controller {
  
  /**
   * 
   * @param id
   * @throws IdException 
   */
  public InfluenceController(String id) throws IdException
  {
    super(id, null, null, null);
    // TODO Auto-generated constructor stub
  }
  
//  /* (non-Javadoc)
//   * @see java.util.concurrent.Callable#call()
//   */
//  @Override
//  public ControllerOutput call() throws Exception
//  {
//    // TODO Auto-generated method stub
//    return null;
//  }
  
  /* (non-Javadoc)
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  @Override
  public int compareTo(Controller o)
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
