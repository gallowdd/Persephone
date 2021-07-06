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
package edu.pitt.gallowdd.persephone;

import java.util.regex.Matcher;

/**
 * The Base exception class used by classes in {@code edu.pitt.gallowdd.persephone} packages.
 *
 * In addition to a message string, a reference to another {@code Throwable} ({@code Error} or 
 * {@code Exception}) is maintained. This reference, if non-null, refers to the event that 
 * caused this exception to occur.
 *
 * @author David Galloway
 */
public class PersephoneException extends Exception {

  private static final long serialVersionUID = 1L;

  /**
   * Default Constructor
   */
  public PersephoneException()
  {
    super(ErrorCode.ERR_GENERAL.message());
  }
  
  /**
   * Constructs a {@code PersephoneException} with a given message {@code String}.
   * No underlying cause is set; {@code getCause} will return null.
   *
   * @param message - the error message
   */
  public PersephoneException(String message)
  {
    super(message);
  }

  /**
   * Constructs a {@code PersephoneException} with a {@code Throwable} that was its 
   * underlying cause.
   *
   * @param cause the {@code Throwable} ({@code Error} or {@code Exception} that caused this exception to occur.
   */
  public PersephoneException(Throwable cause)
  {
    super(cause);
  }

  /**
   * Constructs a {@code PersephoneException} with a given message {@code String} 
   * and a {@code Throwable} that was its underlying cause.
   *
   * @param message - the error message.
   * @param cause the {@code Throwable} ({@code Error} or {@code Exception} that caused this exception to occur.
   */
  public PersephoneException(String message, Throwable cause)
  {
    super(message, cause);
  }
  
  /**
   * All defined error codes in the Persephone Agent-Based Modeling Platform are defined here
   * 
   * @author David Galloway
   */
  @SuppressWarnings("javadoc")
  public enum ErrorCode 
  {
    ERR_ARR_WGHT_SYNC("The two arrays should have the same length. The item array has length [_1_], while the weight array has length [_2_].", 2),
    ERR_CNTRLLR("There was an error in a Controller: [_1_]", 1),
    ERR_ENUM_NOT_FND("The enum value, _1_, does not match an enum in _2_", 2),
    ERR_GENERAL("There was a general system error", 0),
    ERR_HOUR_RANGE("The hour, _1_, is out of range 0 - 23", 1),
    ERR_ID("The ID needs to be in the format XX{XXXX}_00000000-0000-0000-0000-000000000000. This is what was used: [_1_].", 1),
    ERR_LNG_DWNCNVRT_INT("The long value, _1_, is out of range to be converted to an integer value", 1),
    ERR_LOC_NOT_CONTAINED("The location [_1_] is not contained within [_2_]", 2),
    ERR_MSG_NOT_UNDRSTD("The Message Type, _1_, is not understood by _2_", 2),
    ERR_NULL_PARAM("The parameter, [_1_], can not be null.", 1),
    ERR_PARAMS("There was an error trying to load Paramters", 0),
    ERR_XML_VALIDATION("There was an error when trying to Validate XML File: _1_, against XML Schema file: _2_", 2),
    ERR_LOC_PHYSICAL("The was a problem trying to create the physical Location with the parameters [_1_].", 1),
    ERR_LOC_MGR_SIZE_EXCEED("The Location Manager [_1_] can no longer add locations.", 1),
    ERR_LOC_MGR_TYPE_MATCH("The Location Manager [_1_] can only add location of type [_2_].", 2),
    ERR_DIR_GRAPH_CRIT("There was a critical error with a directed graph: _1_.", 1);
    
    private final String message;
    private final int argCount;
    
    /** 
     * @param message
     * @param argCount
     */
    private ErrorCode(String message, int argCount) 
    {
      this.message = message;
      this.argCount = argCount;
    }
    
    /**
     * Each {@code ErrorCode} has a predefined number of configurable text place holders. These are denoted by "_X_" in the text of the message 
     * (where X is number greater than or equal to one).
     * <pre>
     * For example:
     *   {@code ErrorCode.ERR_LNG_DWNCNVRT_INT.message("20000000000");}
     *   
     *   Returns "The long value, 20000000000, is out of range to be converted to an integer value"
     * </pre>
     * @param args Allows the user to fill in the configurable text (_X_) in each ErrorCode's message
     * @return the message as a String
     */
    public String message(String ... args) 
    { 
      if(args.length == this.argCount)
      {
        String fullMessage = this.toString() + ": " + this.message;
        for(int i = 1; i <= this.argCount; ++i)
        {
          fullMessage = fullMessage.replaceAll("_" + i + "_", Matcher.quoteReplacement(args[i - 1]));
        }
        return fullMessage;
      }
      else
      {
        // not enough or too many arguments were passed in, so return the message with ? in the spots
        String fullMessage = this.toString() + ": " + this.message;
        for(int i = 1; i <= this.argCount; ++i)
        {
          fullMessage = fullMessage.replaceAll("_" + i + "_", Matcher.quoteReplacement("?"));
        }
        return fullMessage;
      }
    }
  }
}
