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
public class WorkplaceDAO {
  
  private static final Logger LOGGER = LogManager.getLogger(WorkplaceDAO.class.getName());
  
  private final static String NL = System.getProperty("line.separator") ;
  
  private static final String DB_NAME = "persephone_workplace.db";
  private static final String TABLE_NAME = "workplace";
  
  private static String INSERT_STMT = "";
  
  /*
   * workplace_id integer PRIMARY KEY,
   * workplace_id_persephone text NOT NULL UNIQUE
   */
  static {
    int columnCount = 2;
    StringBuilder builder = new StringBuilder();
    builder.append("INSERT INTO ")
    .append(TABLE_NAME)
    .append("(")
    .append("workplace_id, ")
    .append("workplace_id_persephone")
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
    
    WorkplaceDAO.INSERT_STMT = builder.toString();
    LOGGER.debug(WorkplaceDAO.INSERT_STMT);
  }
  
  /**
   * @param workplaceId
   * @return true or false depending on existence of workplaceId
   */
  public static boolean exists(int workplaceId)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(WorkplaceDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT COUNT(*) AS count FROM " + WorkplaceDAO.TABLE_NAME + " WHERE workplace_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1,workplaceId);
       
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
      WorkplaceDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
    }
    return false;
  }
  
  /**
   * @param workplaceId
   * @return the workplace_id_persephone if it finds workplace_id or null if workplace_id is not found
   */
  public static String findMatchingPersephoneWorkplaceId(int workplaceId)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(WorkplaceDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement("SELECT workplace_id_persephone FROM " + WorkplaceDAO.TABLE_NAME + " WHERE workplace_id = ?")
       )
    {
       // set the value
       pstmt.setInt(1, workplaceId);
       
       ResultSet rs  = pstmt.executeQuery();
     
       // loop through the result set
       while(rs.next()) 
       {
         return rs.getString("workplace_id_persephone");
       }
    } 
    catch(SQLException e) 
    {
      WorkplaceDAO.LOGGER.error("Error with prepared statement in find: " + e.getMessage());
      System.exit(1);
    }
    return null;
  }
  
  /**
   * @param workplaceId
   * @param workplaceIdPersephone
   * @return true if insert is successful, false otherwise
   */
  public static boolean insert(int workplaceId, String workplaceIdPersephone)
  {
    try(
        Connection conn = PeresephoneSqliteConnection.getConnection(WorkplaceDAO.DB_NAME);
        PreparedStatement pstmt = conn.prepareStatement(WorkplaceDAO.INSERT_STMT)
       ) 
    {
      /*
       * 1 workplace_id integer PRIMARY KEY,
       * 2 workplace_id_persephone text NOT NULL UNIQUE,
       */
      pstmt.setInt(1, workplaceId);
      pstmt.setString(2, workplaceIdPersephone);
      
      pstmt.executeUpdate();
    } 
    catch(SQLException e) 
    {
      LOGGER.error("Error with prepared statement " + WorkplaceDAO.INSERT_STMT + " ... " + WorkplaceDAO.NL + e.getMessage());
      System.exit(1);
    }
    return true;
  }
}
