package classes.com.model.EAppointment_Users;

import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.QueryDatabase;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ekta Khiani
 */
public class Student_Advisor extends Staff {
	
	public Student_Advisor()
	{
		super.setsUserType("Student Advisor");
		
	}
	
	@Override
	public  ArrayList<Appointment> MyAppointments(String sEmail) throws ParseException 
	{
		return QueryDatabase.QueryToFetchAllAppointments(sEmail);
	}
}
