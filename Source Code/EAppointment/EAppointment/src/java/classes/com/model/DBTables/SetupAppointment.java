package classes.com.model.DBTables;

import java.util.ArrayList;

/**
 *This class contains the parameters of setup Appointment.
 * @author Ekta Khiani
 * @date 03/27/2016
 */
public class SetupAppointment {
	
	private String sEmail;
	ArrayList<Schedule> schedule;
	
	public SetupAppointment()
	{
		schedule = new ArrayList<>();
	}

	public String getsEmail() {
		return sEmail;
	}

	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;
	}

	public ArrayList<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(ArrayList<Schedule> schedule) {
		this.schedule = schedule;
	}
	
	public void setASchedule(Schedule schedule)
	{
		this.schedule.add(schedule);
	}
	
	public Schedule getASchedule(int key)
	{
		return this.schedule.get(key);
	}

	
}
