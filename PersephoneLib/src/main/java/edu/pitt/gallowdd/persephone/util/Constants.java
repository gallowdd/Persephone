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

import java.util.UUID;

import com.google.gson.Gson;

/**
 * The Constant values used by the simulation
 * 
 * @author David Galloway
 **/
@SuppressWarnings("javadoc")
public final class Constants {
  
  
  public static final double DBL_UNSET = -1.0;
  public static final double DBL_NULL_LAT_LON_ELEV = -9999.0;
  public static final int INT_UNSET = -1;
  public static final String STRING_UNSET = "UNSET";
  public static final String FS = System.getProperty("file.separator");
  public static final String DIGITS = "0123456789";
  public static final UUID DEFAULT_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
  public static final int MANAGER_MAX_SUBSCRIBER_COUNT = 1000;
  
  // Exit codes that are standard for C/C++
  
  public static final int EX_OK  = 0;
  /**
   * Command line usage error
   */
  public static final int EX_USAGE = 64;
  
  /**
   * Data format error
   */
  public static final int EX_DATAERR = 65;
  
  /**
   * Cannot open input
   */
  public static final int EX_NOINPUT = 66;
  
  /**
   * Service unavailable
   */
  public static final int EX_UNAVAILABLE = 69;
  
  /**
   * Internal software error
   */
  public static final int EX_SOFTWARE = 70;
  
  /**
   * Can't create (user) output file
   */
  public static final int EX_CANTCREAT = 73;
  
  /**
   * Input/output error
   */
  public static final int EX_IOERR = 74;
  
  /**
   * Permission denied
   */
  public static final int EX_NOPERM = 77;
  
  /**
   * Configuration error
   */
  public static final int EX_CONFIG = 78;
  
  public static final Gson GSON = new Gson();
  
  public static final String DEFAULT_PARAM_FILENAME = "persephoneDefaultParams.xml";
  public static final String DEFAULT_PARAM_SCHEMA_FILENAME = "schema/persephoneDefaultParameters.xsd";
  public static final String DEFAULT_SYNTH_ENV_INFO_SCHEMA_FILENAME = "schema/syntheticEnvironmentInfo.xsd";
  public static final String DEFAULT_SYNTH_ENV_INFO_FILENAME = "syntheticEnvironmentInfo.xml";
  
  public static final String JAXB_PARAMETERS_CNTXT = "edu.pitt.gallowdd.persephone.parameters";
  public static final String JAXB_SYNTHENVINFO_CNTXT = "edu.pitt.gallowdd.persephone.synthenvinfo";

}
