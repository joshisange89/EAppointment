package classes.com.model.EAppointment_Users;

import classes.com.model.Database.QueryDatabase;

/**
 *
 * @author Ekta Khiani
 */
public class User {
	String sFName, sLName, sMobile, sEmail;

	public String getsFName() {
		return sFName;
	}

	public void setsFName(String sFName) {
		this.sFName = sFName;
	}

	public String getsLName() {
		return sLName;
	}

	public void setsLName(String sLName) {
		this.sLName = sLName;
	}

	public String getsMobile() {
		return sMobile;
	}

	public void setsMobile(String sMobile) {
		this.sMobile = sMobile;
	}

	public String getsEmail() {
		return sEmail;
	}
	//Set Name of the person based on its Email by querying Database.
	public void setsEmail(String sEmail) {
		this.sEmail = sEmail;	
	}
	
	
	
	
}
