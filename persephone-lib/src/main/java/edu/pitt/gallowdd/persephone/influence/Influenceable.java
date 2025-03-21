package edu.pitt.gallowdd.persephone.influence;

import java.util.ArrayList;

/**
 * 
 * @author David Galloway
 *
 */
public interface Influenceable {

  /**
   * 
   * @param influencer
   */
  public void applyInfluence(Influencer influencer);
  
  /**
   * 
   * @param influencers
   */
  public void applyInfluence(ArrayList<Influencer> influencers);
}
