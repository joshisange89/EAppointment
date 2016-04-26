package classes.com.model.EAppointment_Users;

import Constants.Constants;
import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.Database.QueryDatabase;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ekta Khiani
 */
public class Faculty extends Staff {
	
	public Faculty()
	{
		super.setsUserType("Faculty");
	}
	
	@Override
	public  ArrayList<Appointment> MyAppointments(String sEmail) throws ParseException 
	{ 
		return QueryDatabase.queryMyAppointmentsOfStaff(sEmail);
	
	}
	
	@Override
	public Constants.Status updateStatusOf(Appointment appointment)
	{
		System.out.println("I am in Faculty");
		return InsertToDatabase.updateStatusOf(appointment);
	}
	
	
	
}
