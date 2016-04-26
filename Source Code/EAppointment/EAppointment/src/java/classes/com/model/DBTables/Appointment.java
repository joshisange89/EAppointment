
package classes.com.model.DBTables;

import classes.com.model.EAppointment_Users.AppointeeDetails;

/**
 *  This class has all the appointment details.
 * @author Ekta Khiani
 * @date 3/18/2016
 * 
 */
public class Appointment {
	
	private String sApptStatus, sApptNotes, sDate, 
		sUserType, sEmailOfAppointmentWith, sEmailOfAppointee, sFullName, sTime, sStartTime, sEndTime;
	
	public Appointment() {
            setsApptStatus("Pending");
	}
	
	
        	//Getters & Setters
	public String getsApptStatus() {
		return sApptStatus;
	}

	public void setsApptStatus(String sApptStatus) {
		this.sApptStatus = sApptStatus;
	}

	public String getsApptNotes() {
		return sApptNotes;
	}

	public void setsApptNotes(String sApptNotes) {
		this.sApptNotes = sApptNotes;
	}

	public String getsDate() {
		return sDate;
	}

	public void setsDate(String sDate) {
		this.sDate = sDate;
	}

	public String getsUserType() {
		return sUserType;
	}

	public void setsUserType(String sUserType) {
		this.sUserType = sUserType;
	}

	public String getsEmailOfAppointmentWith() {
		return sEmailOfAppointmentWith;
	}

	public void setsEmailOfAppointmentWith(String sEmailOfAppointmentWith) {
		this.sEmailOfAppointmentWith = sEmailOfAppointmentWith;
	}

	public String getsEmailOfAppointee() {
		return sEmailOfAppointee;
	}

	public void setsEmailOfAppointee(String sEmailOfAppointee) {
		this.sEmailOfAppointee = sEmailOfAppointee;
	}

	public String getsFullName() {
		return sFullName;
	}

	public void setsFullName(String sFullName) {
		this.sFullName = sFullName;
	}

	public String getsStartTime() {
		return sStartTime;
	}

	public void setsStartTime(String sStartTime) {
		this.sStartTime = sStartTime;
	}

	public String getsEndTime() {
		return sEndTime;
	}

	public void setsEndTime(String sEndTime) {
		this.sEndTime = sEndTime;
	}
        
        public void setsTime()
        {
            sTime = sStartTime+" - "+sEndTime;
        }
        


}
