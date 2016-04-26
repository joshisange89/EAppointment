package classes.com.model.Profile;

import classes.com.model.Database.QueryDatabase;

/**
 *
 * @author Ekta Khiani
 */
public class Validation {
	
	public static String validateCredentials(String sEmail, String sPwd, String sUserType)
	{
		return QueryDatabase.validateLogin(sEmail, sPwd, sUserType);
	}
	
	public static boolean validateOldPassword(String sEmail, String sOldPwd)
	{
		return QueryDatabase.validateOldPassword(sEmail, sOldPwd);
	}
	
	public static int createNewPassword(String sEmail, String sNewPwd)
	{
		return QueryDatabase.createNewPassword(sEmail, sNewPwd);
	}
	
}
