package classes.com.model.EAppointment_Users;

/**
 *
 * @author Ekta Khiani
 */

//?#To Make it singleton.
public class StaffFactory {
	
	public Staff getUserObject(String userType)
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
		else if(userType.equalsIgnoreCase("Faculty"))
		{
			return new Faculty();
		}
		else if(userType.equalsIgnoreCase("ISO"))
		{
			return new ISO();
		 }

		return null;
	}

	
}
