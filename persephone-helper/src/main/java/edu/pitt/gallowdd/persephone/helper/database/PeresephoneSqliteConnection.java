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
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author David Galloway
 *
 */
public class PeresephoneSqliteConnection {
  
  private static final Logger LOGGER = LogManager.getLogger(PeresephoneSqliteConnection.class.getName());
  
  // SQLite connection string
  private static final String URL_PREFIX = "jdbc:sqlite:/Volumes/DaveSeagate/persephone_DB/";
  
  /**
   * @param dbName The name of the database to connect to
   * @return The Sqlite connection
   */
  public static Connection getConnection(String dbName)
  {
    try 
    {
      String url = PeresephoneSqliteConnection.URL_PREFIX + dbName;
      
      return DriverManager.getConnection(url);
    } 
    catch(SQLException e) 
    {
      PeresephoneSqliteConnection.LOGGER.error(e.getMessage());
      return null;
    }
  }
}
