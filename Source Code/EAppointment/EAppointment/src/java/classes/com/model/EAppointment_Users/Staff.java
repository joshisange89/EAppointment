package classes.com.model.EAppointment_Users;

import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.DBTables.Schedule;
import classes.com.model.DBTables.SetUpWrapper;
import classes.com.model.DBTables.SetupAppointment;
import classes.com.model.Database.InsertToDatabase;
import classes.com.model.Database.QueryDatabase;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 *This class sets & retrieves the SetUp of the Appointments of a Staff Member.
 * @author Ekta Khiani
 */

//Faculty, Student Advisor, Academic Advisor.
public abstract class Staff extends Non_Admin_Uer{
	
	public Staff() {
		
		mapOfDayToTimeSlots = new HashMap<>();
		mapOfDayToTimeSlots.put("Monday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Tuesday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Wednesday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Thursday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Friday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Saturday", new ArrayList<>());
		mapOfDayToTimeSlots.put("Sunday", new ArrayList<>());
		
	}
	
	HashMap<String, ArrayList<String>> mapOfDayToTimeSlots;

	public HashMap<String, ArrayList<String>> getMapOfDayToTimeSlots() {
		return mapOfDayToTimeSlots;
	}

	public void setMapOfDayToTimeSlots(HashMap<String, ArrayList<String>> mapOfDayToTimeSlots) {
		this.mapOfDayToTimeSlots = mapOfDayToTimeSlots;
	}
	
	
	//Fetch all the Staff members based on its type e:g Faculty, or Academic Advisor depending on the courses of the student.
	public HashMap<String, String>getAllStaffNames(String sStudentEmail)
	{
		return QueryDatabase.queryToFetchAAdvisorsOrFacultyNames(getsUserType(), sStudentEmail);

	}
	
	//Get TimeSlots of a particular Staff Member
	public ArrayList<String> getTimeSlotsOfAStaffMember(String sEmail, String sDay)
	{
		return QueryDatabase.queryToFetchTimeSlotsOfAStaffMember(sEmail, sDay);
	}
	
	//Get Fixed appointments of a member.
	public HashSet<String> getAppointmentsOfAStaff(String sEmail, String sDate) throws SQLException
	{
		return QueryDatabase.QueryDbToFetchAppointmentsOfStaffMember(sEmail, sDate);
	}
	
	//Appointments
	public static boolean setUpAppointments(SetupAppointment setup)
	{
	    
		for (Schedule schedule : setup.getSchedule()) 
		{
			String sStartHour = schedule.getsStartHour();
			String sEndHour = schedule.getsEndHour();
			int hour;
			
			if(schedule.getsStartAmPm().equalsIgnoreCase("pm") && !sStartHour.equals("12"))
			{	
				hour = Integer.parseInt(sStartHour) ;
				hour+=12;
				sStartHour = Integer.toString(hour);
			}
			if(schedule.getsEndAmPm().equalsIgnoreCase("pm") && !sEndHour.equals("12"))
			{	
				hour = Integer.parseInt(sEndHour) ;
				hour+=12;
				sEndHour = Integer.toString(hour);
			}
			
			
			String sStartTime = sStartHour+":"+schedule.getsStartMinutes();
			String sEndTime = sEndHour+":"+schedule.getsEndMinutes();
			
			LocalTime startTime = LocalTime.parse(sStartTime, DateTimeFormatter.ofPattern("HH:MM"));
			LocalTime endTime  = LocalTime.parse(sEndTime, DateTimeFormatter.ofPattern("HH:MM"));
			ArrayList timeSlotsList = new ArrayList<>();
			
			String strTime;
			if(startTime.getHour()>12) {
				strTime = startTime.minusHours(12).toString()+" PM";
			}
			else if (startTime.getHour()==12) {
				strTime = startTime.toString()+" PM";
			}
			else
			{
				strTime = startTime.toString()+" AM";
			}
			
			timeSlotsList.add(strTime);
			
			while(startTime.compareTo(endTime) < 0)
			{	
				if(startTime.plusMinutes(Integer.parseInt(schedule.getsDuration())).compareTo(endTime) < 0)
				{
					startTime = startTime.plusMinutes(Integer.parseInt(schedule.getsDuration()));
				}
				else
				{
					startTime = endTime;
				}
			
				if(startTime.getHour()>12) {
					strTime = startTime.minusHours(12).toString()+" PM";
				}
				else if (startTime.getHour()==12) {
					strTime = startTime.toString()+" PM";
				}
				else {
					strTime = startTime.toString()+" AM";
				}
			
				timeSlotsList.add(strTime);
			}
			System.out.println("The TimeSlots are :"+timeSlotsList);
			schedule.setTimeSlots(timeSlotsList);
		}//Schedule Loop
		
		//Insert the setup into database.
		InsertToDatabase.insertSetupAppointments(setup);
		return true;
	}
	
	
	public static ArrayList<SetUpWrapper> getSetUpAppointments(String sEmail)
	{
		return(QueryDatabase.querySetUpAppointments(sEmail));
	}
	
	
	//Update the Status of the Appointments of the Students.
	public Status updateStatusOf(Appointment appointment)
	{
		return Status.SUCCESS;
	}
}

