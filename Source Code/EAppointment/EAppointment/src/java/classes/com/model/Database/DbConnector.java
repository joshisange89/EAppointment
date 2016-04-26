package classes.com.model.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**  
* DbConnector.java - A class that connects to mysql database.  
* @author Ekta Khiani
* @version 1.0 
* @date 03/16/2016
*/

public class DbConnector {
    private static Connection con;
    
/**
* This method creates connection with the database.
* @param url is a database url to mysql connection.
* @param username is the username of the database.
* @param pwd is the password of the database
*
*/
  
    public static void createConnection(String url, String username, String pwd)
    {
        try
        { 
            con = null;
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            con = DriverManager.getConnection(url, username, pwd);
         }//try
            catch(Exception ex)
            {
                ex.printStackTrace();
            }//end catch

    }//End function
    
    /**
* Method to get Connection object to Database.
* @return Connection object if successfully initialized or null.
*
*/
    public static Connection getConnection()
     {  
        if (con!=null)
            return con;
        else
            return null;
     }
    
/**
* This method closes connection with the database.
*
*/
    public static void closeConnection()
    {
        if(con != null)
        try
        {
            con.close();
        }
        catch(SQLException ex) 
        {
            ex.printStackTrace();
        }
    }//end func

}//end class
