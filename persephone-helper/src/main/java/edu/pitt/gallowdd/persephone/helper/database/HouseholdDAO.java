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
package edu.pitt.gallowdd.persephone.helper.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author David Galloway
 *
 */
public class HouseholdDAO {
  
  private static final Logger LOGGER = LogManager.getLogger(HouseholdDAO.class.getName());
  
  private final static String NL = System.getProperty("line.separator") ;
  
  private static final String DB_NAME = "persephone_household.db";
  private static final String TABLE_NAME = "household";
  
  private static String INSERT_STMT = "";
  
  /*
   *  hh_id integer PRIMARY KEY,
   *  hh_id_persephone text NOT NULL UNIQUE
   */
  static {
    int columnCount = 2;
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ")
    .append(HouseholdDAO.TABLE_NAME)
    .append("(")
    .append("hh_id, ")
    .append("hh_id_persephone")
    .append(")")
    .append(" VALUES (");
    
    for(int i = 0; i < columnCount; ++i)
    {
      builder.append("?");
      if(i + 1 < columnCount)
      {
        builder.append(", ");
      }
    }  
    
    builder.append(")");
    
    HouseholdDAO.INSERT_STMT = builder.toString();
    LOGGER.debug(HouseholdDAO.INSERT_STMT);
  }
  
  /**
   * @param hhId
   * @return true if hhId exists in the database, false otherwise
   */
  public static boolean exists(int hhId)
  {
    
    try(
         Connection conn = PeresephoneSqliteConnection.getConnection(HouseholdDAO.DB_NAME);
         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM " + HouseholdDAO.TABLE_NAME + " WHERE hh_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, hhId);
       
       ResultSet rs  = pstmt.executeQuery();
     
       // loop through the result set
       while(rs.next()) 
       {
         if(rs.getInt("count") == 1)
         {
           return true;
         }
         else if(rs.getInt("count") == 0)
         {
           return false;
         }
         else
         {
           throw new RuntimeException("SHOULD NEVER HAPPEN");
         }
       }
    } 
    catch(SQLException e) 
    {
      HouseholdDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return false;
  }
  
  /**
   * @param hhId
   * @return the hh_id_persephone if it finds hh_id or null if hh_id is not found
   */
  public static String findMatchingPersephoneHouseholdId(int hhId)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(HouseholdDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT hh_id_persephone FROM " + HouseholdDAO.TABLE_NAME + " WHERE hh_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, hhId);
       
       ResultSet rs  = pstmt.executeQuery();
     
       // loop through the result set
       while(rs.next()) 
       {
         return rs.getString("hh_id_persephone");
       }
    } 
    catch(SQLException e) 
    {
      HouseholdDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return null;
  }
  
  /**
   * @param hhId
   * @param hhIdPersephone
   * @return true if insert succeeds, false otherwise
   */
  public static boolean insert(int hhId, String hhIdPersephone)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(HouseholdDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement(HouseholdDAO.INSERT_STMT);
       )
    {
      /*
       * 1 hh_id integer PRIMARY KEY,
       * 2 hh_id_persephone text NOT NULL UNIQUE,
       */
      pstmt.setInt(1, hhId);
      pstmt.setString(2, hhIdPersephone);
      pstmt.executeUpdate();
    } 
    catch(SQLException e) 
    {
      LOGGER.error("Error with prepared statement " + HouseholdDAO.INSERT_STMT + " ... " + HouseholdDAO.NL + e.getMessage());
      return false;
    }
    return true;
  }
  
  /**
   * 
   * @return true if successful, false otherwise
   */
  public static boolean deleteAll()
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(HouseholdDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + HouseholdDAO.TABLE_NAME);
       )
    {
      
      pstmt.executeUpdate();
    } 
    catch(SQLException e) 
    {
      LOGGER.error("Error with prepared statement in delete: " + e.getMessage());
      return false;
    }
    return true;
  }
}
