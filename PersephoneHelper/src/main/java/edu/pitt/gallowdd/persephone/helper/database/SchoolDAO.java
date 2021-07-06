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
public class SchoolDAO {
  
  private static final Logger LOGGER = LogManager.getLogger(SchoolDAO.class.getName());
  
  private final static String NL = System.getProperty("line.separator");
  
  private static final String DB_NAME = "persephone_school.db";
  private static final String TABLE_NAME = "school";
  
  private static String INSERT_STMT = "";
  
  /*
   *  school_id integer PRIMARY KEY,
   *  school_id_persephone text NOT NULL UNIQUE,L
   */
  static {
    int columnCount = 2;
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ")
    .append(TABLE_NAME)
    .append("(")
    .append("school_id, ")
    .append("school_id_persephone")
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
    
    SchoolDAO.INSERT_STMT = builder.toString();
    LOGGER.debug(SchoolDAO.INSERT_STMT);
  }
  
  /**
   * @param schoolId
   * @return true or false depending on existence of schoolId
   */
  public static boolean exists(int schoolId)
  {
    
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(SchoolDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM " + SchoolDAO.TABLE_NAME + " WHERE school_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, schoolId);
       
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
      SchoolDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return false;
  }
  
  /**
   * @param schoolId
   * @return the school_id_persephone if it finds school_id or null if school_id is not found
   */
  public static String findMatchingPersephoneSchoolId(int schoolId)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(SchoolDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT school_id_persephone FROM " + SchoolDAO.TABLE_NAME + " WHERE school_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, schoolId);
       
       ResultSet rs  = pstmt.executeQuery();
     
       // loop through the result set
       while(rs.next()) 
       {
         return rs.getString("school_id_persephone");
       }
    } 
    catch(SQLException e) 
    {
      SchoolDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return null;
  }
  
  /**
   * @param schoolId
   * @param schoolIdPersephone
   * @return true if insert is successful, false otherwise
   */
  public static boolean insert(int schoolId, String schoolIdPersephone)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(SchoolDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement(SchoolDAO.INSERT_STMT)
       ) 
    {
      /*
       * 1 hh_id integer PRIMARY KEY,
       * 2 hh_id_persephone text NOT NULL UNIQUE,
       */
      pstmt.setInt(1, schoolId);
      pstmt.setString(2, schoolIdPersephone);
      
      pstmt.executeUpdate();
    } 
    catch(SQLException e) 
    {
      LOGGER.error("Error with prepared statement " + SchoolDAO.INSERT_STMT + " ... " + SchoolDAO.NL + e.getMessage());
      return false;
    }
    return true;
  }
}
