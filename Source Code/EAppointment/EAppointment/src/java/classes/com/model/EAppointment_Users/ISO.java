package classes.com.model.EAppointment_Users;

import Constants.Constants;
import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.Database.QueryDatabase;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Ekta Khiani
 */
public class ISO extends Staff {
	
	
	public ISO()
	{
		super.setsUserType("ISO");
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
	
	 
        //Fetch all ISO's
        @Override
	public HashMap<String, String>getAllStaffNames(String sStudentEmail)
	{
		return QueryDatabase.queryToFetchISOs(getsUserType());

	}
	 
	
}
