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
public class Academic_Advisor extends Staff {
	
	public Academic_Advisor ()
	{
		super.setsUserType("Academic Advisor");
	}
	
	
	@Override
	public  ArrayList<Appointment> MyAppointments(String sEmail) throws ParseException 
	{ 
		return QueryDatabase.queryMyAppointmentsOfStaff(sEmail);
	
	}
       
	@Override
	public Constants.Status updateStatusOf(Appointment appointment)
	{
		return InsertToDatabase.updateStatusOf(appointment);
	}
	
}
