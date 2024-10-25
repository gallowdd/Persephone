package edu.pitt.gallowdd.persephone.util;

import java.util.Optional;

/**
 * 
 * @author David Galloway
 *
 * @param <T> the type of Object that the Array will be composed of
 */
public class ArraySelector<T> {

  /**
   * @param items
   * @param weights
   * @return the an Optional item that was randomly selected from items given the weights or {@code Optional.empty()} if not found
   * @throws ArraySelectorException if the arrays aren't both the same size
   */
  public Optional<T> getRandomItemFromArrayGivenArrayOfWeights(T[] items, double[] weights) throws ArraySelectorException
  {
    int length = items.length;
    if(weights.length != length)
    {
      throw new ArraySelectorException(items.length, weights.length);
    }
    
    double total = 0.0;
    double[] probArr = new double[length];
    
    for(int i = 0; i < length; ++i)
    {
      total += Math.abs(weights[i]);
      probArr[i] = Math.abs(weights[i]);
    }
    
    if(total > 0.0)
    {
      for(int i = 0; i < length; ++i)
      {
        probArr[i] = probArr[i] / total;
      }
      double randVal = Utils.getRandom();
      double currentUpperBound = 0.0;
      for(int i = 0; i < length - 1; ++i)
      {
        currentUpperBound += probArr[i];
        if(randVal < currentUpperBound)
        {
          return Optional.of(items[i]);
        }
      }
      return Optional.of(items[length - 1]);
    }
    
    return Optional.empty();
    
  }
}
