
package classes.com.model.DBTables;

/**
 *This is a wrapper class which wraps the setup data fetched from the database. 
 * @author Ekta Khiani
 * @date 03/31/2016
 */
public class SetUpWrapper {
	String sDay, sTimeSlots, sDuration;

	public String getsDay() {
		return sDay;
	}

	public void setsDay(String sDay) {
		this.sDay = sDay;
	}

	public String getsTimeSlots() {
		return sTimeSlots;
	}

	public void setsTimeSlots(String sTimeSlots) {
		this.sTimeSlots = sTimeSlots;
	}

	public String getsDuration() {
		return sDuration;
	}

	public void setsDuration(String sDuration) {
		this.sDuration = sDuration;
	}
	
}
