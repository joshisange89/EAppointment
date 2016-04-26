package classes.com.model.EAppointment_Users;

import Constants.Constants.Status;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.Database.QueryDatabase;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Admin is the administrator of the System.
 * @author Ekta Khiani
 */
public class Admin extends Login_Access_User{
	
	
	//Add users to the Database
	
	public Status addUsers(Login_Access_User user) throws SQLException
	{
		
		return(InsertToDatabase.InsertUserDetails(user));
	
	}
	
	public HashMap<String, String> LoadCourses()
	{	
		return QueryDatabase.queryToFetchCourses();
	}
	
	//Methods
	public Login_Access_User getUserDetails(String sEmail)
	{
		return QueryDatabase.queryToFetchUserDetails(sEmail);
	
	}
	
	//Update Users
	public Status UpdateUser(Login_Access_User user)
	{
		return InsertToDatabase.updateUser(user);
	}
	
	//Delete Users
	public Status DeleteUser(String sEmail, String sUserType)
	{
		return InsertToDatabase.deleteUser(sEmail, sUserType);
	}
}
