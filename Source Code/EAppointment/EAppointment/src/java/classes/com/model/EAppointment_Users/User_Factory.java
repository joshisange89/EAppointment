package classes.com.model.EAppointment_Users;

/**
 *This class uses Factory pattern to decide which object to create based on its type.
 * @author Ekta Khiani
 */
public class User_Factory {
	
	/**
	 *
	 * @param userType It is the type of the users of the system
	 * @return Object of a particular user is created & returned based on the type of User.
	 */
	
	
   public Login_Access_User getUserObject(String userType)
   {
	if(userType == null)
	{
		return null;
	}
  	if(userType.equalsIgnoreCase("Student Advisor"))
	{
		return new Student_Advisor();
	} 
	else if(userType.equalsIgnoreCase("Academic Advisor"))
	{
	         return new Academic_Advisor();
			 
	} 
	else if(userType.equalsIgnoreCase("Student"))
	{
		return new Current_Student();
	}
	else if(userType.equalsIgnoreCase("Faculty"))
	{
		return new Faculty();
	}
	else if(userType.equalsIgnoreCase("ISO"))
	{
		return new ISO();
	 }
	
	else if(userType.equalsIgnoreCase("Admin"))
	{
		return new Admin();
	 }
      return null;
   }
	
}
