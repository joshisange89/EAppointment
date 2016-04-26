package classes.com.model.EAppointment_Users;

import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.Database.QueryDatabase;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;

/**
 * This is a UserDetails class which has all the details of the user using the system.
 * @date 03/17/2016
 * @author Ekta Khiani
 */
public class Current_Student extends Non_Admin_Uer{
	
	public	Current_Student()
	{
		super.setsUserType("Student");
	
	}
	
	public Status bookAnAppointment(Appointment appointment) throws SQLException, ParseException
	{
		return InsertToDatabase.InsertAppointments(appointment, getsUserType());
	
	}
	
	@Override
	public  ArrayList<Appointment> MyAppointments(String sEmail) throws ParseException 
	{ 
		return QueryDatabase.queryMyAppointmentsOfStudent(sEmail);
	}
	
}
