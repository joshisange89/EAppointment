/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes.com.model.EAppointment_Users;

/**
 *
 * @author Ekta Khiani
 */
public class Non_Admin_Factory {
		
   public Non_Admin_Uer getUserObject(String userType)
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
	
      return null;
   }
	
}
