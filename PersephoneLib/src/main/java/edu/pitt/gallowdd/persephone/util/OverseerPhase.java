/*
 * Persephone: An Agent-Based Modeling Platform
 * Copyright (c) 2019-2021  David Galloway / University of Pittsburgh
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package edu.pitt.gallowdd.persephone.util;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.annotations.SerializedName;


/**
 * An enumeration of all Phases of the Overseer and its Controllers
 *  
 * @author David Galloway
 */
@SuppressWarnings("javadoc")
public enum OverseerPhase
{
  
  @SerializedName("OverseerInitializeRequest")
  OVRSR_INIT_RQST(0)
  {
    @Override
    public String toString()
    {
      return "OverseerInitializeRequest";
    }
  },
  
  @SerializedName("ControllerInitializationResponse")
  CNTRLR_INIT_RSPNS(1)
  {
    @Override
    public String toString()
    {
      return "ControllerInitializationResponse";
    }
  },
  
  @SerializedName("OverseerNormalizeRequest")
  OVRSR_NRMLZ_RQST(2)
  {
    @Override
    public String toString()
    {
      return "OverseerNormalizeRequest";
    }
  },
  
  @SerializedName("ControllerNormalizeResponse")
  CNTRLR_NRMLZ_RSPNS(3)
  {
    @Override
    public String toString()
    {
      return "ControllerNormalizeResponse";
    }
  },
  
  @SerializedName("OutOfRange")
  OUT_OF_RANGE(4)
  {
    @Override
    public String toString()
    {
      return "OutOfRange";
    }
  };
  
  private int value;
  private static Map<Integer, OverseerPhase> map = new HashMap<>();
  
  private OverseerPhase(int value)
  {
    this.value = value;
  }
  
  /**
   * @return the integer value of this enumerated value
   */
  public int getValue()
  {
    return this.value;
  }
  
  static 
  {
    for(OverseerPhase race : OverseerPhase.values())
    {
      OverseerPhase.map.put(race.value, race);
    }
  }
  
  /**
   * Return an Enumeration that matches an integer code
   * 
   * @param phase
   * @return the enumerated value matching the integer code
   * @throws EnumNotFoundException if the integer value is not in the map
   */
  public static OverseerPhase valueOf(int phase) throws EnumNotFoundException
  {
    OverseerPhase retVal = OverseerPhase.map.get(phase);
    if(retVal != null)
    {
      return retVal;
    }
    else
    {
      throw new EnumNotFoundException(phase, OverseerPhase.class);
    }
  }
  
  public abstract String toString();
  
}
