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
public class GroupQuarterDAO {
  
  private static final Logger LOGGER = LogManager.getLogger(GroupQuarterDAO.class.getName());
  
  private final static String NL = System.getProperty("line.separator") ;
  
  private static final String DB_NAME = "persephone_group_quarter.db";
  private static final String TABLE_NAME = "group_quarter";
  
  /*
   * CREATE TABLE group_quarter (
  gq_id integer PRIMARY KEY,
  gq_id_persephone text NOT NULL UNIQUE
  gq_type text NOT NUL
   */
  private static String INSERT_STMT = "";
  
  /*
   *  hh_id integer PRIMARY KEY,
   *  hh_id_persephone text NOT NULL UNIQUE
   */
  static {
    int columnCount = 2;
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ")
    .append(GroupQuarterDAO.TABLE_NAME)
    .append("(")
    .append("gq_id, ")
    .append("gq_id_persephone")
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
    
    GroupQuarterDAO.INSERT_STMT = builder.toString();
    LOGGER.debug(GroupQuarterDAO.INSERT_STMT);
  }
  
  /**
   * @param gqId
   * @return true if gqId exists in the database, false otherwise
   */
  public static boolean exists(int gqId)
  {
    
    try(
         Connection conn = PeresephoneSqliteConnection.getConnection(GroupQuarterDAO.DB_NAME);
         PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM " + GroupQuarterDAO.TABLE_NAME + " WHERE gq_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, gqId);
       
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
      GroupQuarterDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return false;
  }
  
  /**
   * @param gqId
   * @return the gq_id_persephone if it finds gq_id or null if gq_id is not found
   */
  public static String findMatchingPersephoneGroupQuarterId(int gqId)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(GroupQuarterDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT gq_id_persephone FROM " + GroupQuarterDAO.TABLE_NAME + " WHERE gq_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, gqId);
       
       ResultSet rs  = pstmt.executeQuery();
     
       // loop through the result set
       while(rs.next()) 
       {
         return rs.getString("gq_id_persephone");
       }
    } 
    catch(SQLException e) 
    {
      GroupQuarterDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return null;
  }
  
  /**
   * @param gqId
   * @param gqIdPersephone
   * @return true if insert succeeds, false otherwise
   */
  public static boolean insert(int gqId, String gqIdPersephone)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(GroupQuarterDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement(GroupQuarterDAO.INSERT_STMT);
       )
    {
      /*
       * 1 gq_id integer PRIMARY KEY,
       * 2 gq_id_persephone text NOT NULL UNIQUE,
       */
      pstmt.setInt(1, gqId);
      pstmt.setString(2, gqIdPersephone);
      pstmt.executeUpdate();
    } 
    catch(SQLException e) 
    {
      LOGGER.error("Error with prepared statement " + GroupQuarterDAO.INSERT_STMT + " ... " + GroupQuarterDAO.NL + e.getMessage());
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
        Connection conn = PeresephoneSqliteConnection.getConnection(GroupQuarterDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("DELETE FROM " + GroupQuarterDAO.TABLE_NAME);
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
