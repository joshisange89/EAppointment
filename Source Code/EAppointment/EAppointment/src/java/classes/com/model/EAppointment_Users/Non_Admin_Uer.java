package classes.com.model.EAppointment_Users;

import classes.com.model.DBTables.Appointment;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Ekta Khiani
 */
public abstract class Non_Admin_Uer extends Login_Access_User {
	
	public abstract ArrayList<Appointment> MyAppointments(String sEmail) throws ParseException ;
	
}
