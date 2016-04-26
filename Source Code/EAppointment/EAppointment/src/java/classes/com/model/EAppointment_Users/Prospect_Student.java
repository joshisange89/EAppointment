package classes.com.model.EAppointment_Users;

import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.Database.InsertToDatabase;
import java.sql.SQLException;
import java.text.ParseException;

/**
 *
 * @author Ekta Khiani
 */
public class Prospect_Student extends User{
	
	public Status bookAnAppointment(Appointment appointment) throws SQLException, ParseException
	{
		return(InsertToDatabase.InsertAppointments(appointment, "Prospect"));
	}
	
	
	
}
