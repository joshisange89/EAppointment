package classes.com.model.EAppointment_Users;

import classes.com.model.Database.QueryDatabase;
import java.util.ArrayList;

/**
 *
 * @author Ekta Khiani
 */
public class Login_Access_User extends User
{
	private String sPassword="ITU@123", sUserType, ITUid;//?# To Change

	public String getITUid() {
		return ITUid;
	}

	public void setITUid(String ITUid) {
		this.ITUid = ITUid;
	}
	private ArrayList<String> courseList;

	public Login_Access_User() {
		courseList = new ArrayList<>();
	}
	
	

	public ArrayList<String> getCourseList() {
		return courseList;
	}

	public void setCourseList(ArrayList<String> courseList) {
		this.courseList = courseList;
	}

	public void setsUserType(String sUserType) {
		this.sUserType = sUserType;
	}

	public String getsUserType() {
		return sUserType;
	}

	
	public String getsPassword() {
		return sPassword;
	}

	
	public void setsPassword(String sPassword) {
		this.sPassword = sPassword;
	}
	
	@Override
	public void setsEmail(String sEmail) 
	{
		super.setsEmail(sEmail);
		if(sEmail!=null)
		{
			String sName = QueryDatabase.getNameOfAMember(sEmail);
			if(!sName.equals("UnKnown"))
			{
				String[] result = sName.split("\\s");
		
				setsFName(result[0]);
				setsLName(result[1]);
			}
		
		}
		
	}
	
	
}
