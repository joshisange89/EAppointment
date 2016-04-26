package classes.com.model.DBTables;

import java.util.ArrayList;

/**
 *
 * @author Ekta
 * @date 03/30/2016
 */
public class Schedule {
	private String sDay, sStartHour, sStartMinutes, sStartAmPm, 
		sEndHour, sEndMinutes, sEndAmPm, sDuration;
		ArrayList<String>TimeSlots;
		
	public Schedule()
	{
		sDuration = "0";
		
	}

	public ArrayList<String> getTimeSlots() {
		return TimeSlots;
	}

	public void setTimeSlots(ArrayList<String> TimeSlots) {
		this.TimeSlots = TimeSlots;
	}

	public String getsDuration() {
		return sDuration;
	}

	public void setsDuration(String sDuration) {
		this.sDuration = sDuration;
	}

	public String getsDay() {
		return sDay;
	}

	public void setsDay(String sDay) {
		this.sDay = sDay;
	}

	public String getsStartHour() {
		return sStartHour;
	}

	public void setsStartHour(String sStartHour) {
		this.sStartHour = sStartHour;
	}

	public String getsStartMinutes() {
		return sStartMinutes;
	}

	public void setsStartMinutes(String sStartMinutes) {
		this.sStartMinutes = sStartMinutes;
	}

	public String getsStartAmPm() {
		return sStartAmPm;
	}

	public void setsStartAmPm(String sStartAmPm) {
		this.sStartAmPm = sStartAmPm;
	}

	public String getsEndHour() {
		return sEndHour;
	}

	public void setsEndHour(String sEndHour) {
		this.sEndHour = sEndHour;
	}

	public String getsEndMinutes() {
		return sEndMinutes;
	}

	public void setsEndMinutes(String sEndMinutes) {
		this.sEndMinutes = sEndMinutes;
	}

	public String getsEndAmPm() {
		return sEndAmPm;
	}

	public void setsEndAmPm(String sEndAmPm) {
		this.sEndAmPm = sEndAmPm;
	}

	
	
}
