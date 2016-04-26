package classes.com.model.Database;

import classes.com.model.DBTables.Appointment;
import classes.com.model.DBTables.SetUpWrapper;
import classes.com.model.EAppointment_Users.Login_Access_User;
import classes.com.model.EAppointment_Users.Staff;
import classes.com.model.EAppointment_Users.StaffFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.registry.infomodel.EmailAddress;

/**
 *
 * @author Ekta Khiani Dated: 03/18/2016
 */
public class QueryDatabase {

    //This Will get all the Student Advisors from the DB. This is for Prospective Students
    public static HashMap<String, Staff> QueryDbToFetchStaffAppointmentsSetUp(String sUserType) throws SQLException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;
        HashMap<String, Staff> map = new HashMap<>();
        System.out.println("Connection" + con);
        if (con.isClosed()) {
       
            return map;
        } else {
            try {
                String sql = "SELECT setup_appointments.day, setup_appointments.start_time, user_details.firstname,  user_details.lastname, setup_appointments.email"
                        + " FROM user_details"
                        + " INNER JOIN setup_appointments"
                        + " ON setup_appointments.email=user_details.email_id AND user_details.user_type = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, sUserType);

                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    //Staff staff;
                    StaffFactory factory = new StaffFactory();
                    Staff staff;

                    if (!map.containsKey(rs.getString("email"))) {
                        //staff = new Staff();
                        staff = factory.getUserObject(sUserType);
                        staff.setsFName(rs.getString("firstname"));
                        staff.setsLName(rs.getString("lastname"));
                        staff.setsEmail(rs.getString("email"));

                        map.put(rs.getString("email"), staff);
                    } else {
                        staff = map.get(rs.getString("email"));
                    }

                    HashMap<String, ArrayList<String>> mapDaytoSlots = staff.getMapOfDayToTimeSlots();
                    if (mapDaytoSlots.containsKey(rs.getString("day"))) {
                        mapDaytoSlots.get(rs.getString("day")).add(rs.getString("start_time"));
                    }

                }//while

            } catch (SQLException ex) {
                Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
            }

        }//else
        return map;
    }

    //Fetch the TimeSlots of the Staff Members.
    public static HashSet<String> QueryDbToFetchAppointmentsOfStaffMember(String sEmail, String selectedDate) throws SQLException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;

        String queryString = "Select  appointment_start_time from appointments where staff_email = ? "
                + " and date = ?";
        ps = con.prepareStatement(queryString);

        ps.setString(1, sEmail);
        ps.setString(2, selectedDate);

        ResultSet rs = ps.executeQuery();

        HashSet<String> appointedSlotsSet = new HashSet<>();
        while (rs.next()) {
            appointedSlotsSet.add(rs.getString("appointment_start_time"));
        }

        return appointedSlotsSet;
    }

    //This is for Current Students. This Fetches the setup of the appointments of a staff member.
    public static ArrayList<String> queryToFetchTimeSlotsOfAStaffMember(String sEmail, String sDay) {
        ArrayList<String> timeSlotsList = new ArrayList<String>();

        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            String queryString = "Select start_time, end_time from"
                    + " setup_appointments where email=? and day= ?";

            ps = con.prepareStatement(queryString);
            ps.setString(1, sEmail);
            ps.setString(2, sDay);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                timeSlotsList.add(rs.getString("start_time"));
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return timeSlotsList;

    }
    
    private static boolean validateDateOfAppointment(String sDate) throws ParseException
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date date = sdf.parse(sDate);
        Date sTodaysDate = new Date();
        
        return(sdf.format(date).compareTo(sdf.format(sTodaysDate)) >= 0); 

    }
    //It Fetches all the appointments of a user.

    public static ArrayList<Appointment> QueryToFetchAllAppointments(String email) throws ParseException {
        ArrayList<Appointment> myAppointmentsList = new ArrayList<>();
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            String queryString = "SELECT appointments.prospect_email, appointments.appointment_status, appointments.appointment_start_time, appointment_end_time, appointments.date, appointments.appointment_notes,"
                    + " prospect_students.first_name, prospect_students.last_name from appointments"
                    + " INNER JOIN prospect_students ON appointments.staff_email= ? AND prospect_students.email = appointments.prospect_email";

            ps = con.prepareStatement(queryString);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
           
            while (rs.next()) {
                
                String sDate = rs.getString("date");
                if(!validateDateOfAppointment(sDate))
                {
                    continue;
                }
                Appointment myAppointment = new Appointment();
                myAppointment.setsEmailOfAppointee(rs.getString("prospect_email"));
                myAppointment.setsFullName(rs.getString("first_name") + " " + rs.getString("last_name"));
                myAppointment.setsStartTime(rs.getString("appointment_start_time"));
                myAppointment.setsEndTime(rs.getString("appointment_end_time"));
                myAppointment.setsDate(rs.getString("date"));
                myAppointment.setsApptStatus(rs.getString("appointment_status"));
                myAppointment.setsTime();
				myAppointment.setsApptNotes(rs.getString("appointment_notes"));
                myAppointmentsList.add(myAppointment);
            }//while	

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myAppointmentsList;
    }

    //Get Appointments of a student
    public static ArrayList<Appointment> queryMyAppointmentsOfStudent(String email) throws ParseException {
        ArrayList<Appointment> myAppointmentsList = new ArrayList<>();
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            String queryString = "select user_details.firstname, user_details.lastname, user_details.email_id, user_details.user_type,"
                    + " appointments.appointment_start_time, appointments.appointment_end_time, appointments.date,"
                    + " appointments.appointment_status, appointments.appointment_notes from appointments inner join"
                    + " user_details on user_details.email_id=appointments.staff_email and appointments.student_email = ?";

            ps = con.prepareStatement(queryString);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                String sDate = rs.getString("date");
                if(!validateDateOfAppointment(sDate))
                {
                    continue;
                }
                  Appointment myAppointment = new Appointment();

                myAppointment.setsFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
                myAppointment.setsUserType(rs.getString("user_type"));
                myAppointment.setsEmailOfAppointmentWith(rs.getString("email_id"));
                myAppointment.setsStartTime(rs.getString("appointment_start_time"));
                myAppointment.setsDate(rs.getString("date"));
                myAppointment.setsApptStatus(rs.getString("appointment_status"));
                myAppointment.setsEndTime(rs.getString("appointment_end_time"));
                myAppointment.setsTime();
				myAppointment.setsApptNotes(rs.getString("appointment_notes"));
                myAppointmentsList.add(myAppointment);
            }//while	

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myAppointmentsList;
    }

    //Get Appointments of a student
    public static ArrayList<Appointment> queryMyAppointmentsOfStaff(String email) throws ParseException {
        ArrayList<Appointment> myAppointmentsList = new ArrayList<>();
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            String queryString = "select user_details.firstname, user_details.lastname, user_details.email_id,"
                    + " appointments.appointment_start_time, appointments.appointment_end_time, appointments.date,"
                    + " appointments.appointment_status, appointments.appointment_notes from appointments inner join"
                    + " user_details on user_details.email_id=appointments.student_email and appointments.staff_email = ?";

            ps = con.prepareStatement(queryString);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                
                String sDate = rs.getString("date");
                if(!validateDateOfAppointment(sDate))
                {
                    continue;
                }
               
                Appointment myAppointment = new Appointment();

                myAppointment.setsFullName(rs.getString("firstname") + " " + rs.getString("lastname"));
                myAppointment.setsEmailOfAppointee(rs.getString("email_id"));
                myAppointment.setsStartTime(rs.getString("appointment_start_time"));
                myAppointment.setsEndTime(rs.getString("appointment_end_time"));
                myAppointment.setsDate(rs.getString("date"));
                myAppointment.setsApptStatus(rs.getString("appointment_status"));
                myAppointment.setsTime();
				myAppointment.setsApptNotes(rs.getString("appointment_notes"));

                myAppointmentsList.add(myAppointment);
            }//while	

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return myAppointmentsList;
    }

    public static String getNameOfAMember(String sEmail) {
        String sName = "UnKnown";
        try {

            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return sName;
            }

            String query = "Select firstname, lastname from user_details  where email_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sEmail);

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                sName = rs.getString("firstname") + " " + rs.getString("lastname");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sName;
    }

    //Validates the login 
    public static String validateLogin(String sEmail, String sPwd, String sUserType) {
        String sValidate = "UnKnown";
        try {

            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return sValidate;
            }

            String query = "Select firstname, lastname from user_details  where email_id = ? and password = ? and user_type = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sEmail);
            ps.setString(2, sPwd);
            ps.setString(3, sUserType);

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                sValidate = rs.getString("firstname") + " " + rs.getString("lastname");
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        return sValidate;

    }

    //Validate old password. This is pre-requisite for changing to new changing
    public static boolean validateOldPassword(String sEmail, String sOldPwd) {
        boolean validate = false;
        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return validate;
            }

            String query = "Select * from user_details  where email_id = ? and password = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sEmail);
            ps.setString(2, sOldPwd);

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                validate = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validate;
    }

    //Change the password
    public static int createNewPassword(String sEmail, String sNewPwd) {
        int nNoOfRowsAffected = 0;

        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return nNoOfRowsAffected;
            }

            String query = "update user_details set password = ? where email_id = ? ";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sNewPwd);
            ps.setString(2, sEmail);

            nNoOfRowsAffected = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nNoOfRowsAffected;
    }
	
	 public static boolean propectExist(String sEmail) {
        boolean validate = false;
        try {

            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return validate;
            }

            String query = "Select first_name from prospect_students where email = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, sEmail);
			
			System.out.println("I am in Validate of PRospect"+validate+sEmail);
        
            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                validate = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validate;
    }


    public static ArrayList<SetUpWrapper> querySetUpAppointments(String sEmail) {
        ArrayList<SetUpWrapper> setUpList = new ArrayList<>();
        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return null;
            }
            String sQuery = "SELECT DISTINCT day, timeslots, duration from setup_appointments where email = ? ORDER BY FIELD(day, 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday')";

            PreparedStatement ps = con.prepareStatement(sQuery);
            ps.setString(1, sEmail);

            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                SetUpWrapper setup = new SetUpWrapper();
                setup.setsDay(rs.getString("day"));
                setup.setsDuration(rs.getString("duration"));
                setup.setsTimeSlots(rs.getString("timeslots"));

                setUpList.add(setup);

            }

            for (SetUpWrapper wrap : setUpList) {
                System.out.println("In Query" + wrap);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return setUpList;
    }

    //This method returns list of all ISO's
    public static HashMap<String, String> queryToFetchISOs(String sUserType) {
        HashMap<String, String> emailToNameMap = new HashMap<>();
        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return null;
            }

            String sQuery = "Select firstname, lastname, email_id from user_details where user_type=?";

            PreparedStatement ps = con.prepareStatement(sQuery);
            ps.setString(1, sUserType);

            ResultSet rs = ps.executeQuery();

            String sEmail, sName;
            while (rs.next()) {
                sEmail = rs.getString("email_id");
                sName = rs.getString("firstname") + " " + rs.getString("lastname");
                emailToNameMap.put(sEmail, sName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailToNameMap;
    }

    //This method returns list of Staff members email with its corresponding name based on given staff type.
    public static HashMap<String, String> queryToFetchAAdvisorsOrFacultyNames(String sUserType, String sStudentEmail) {
        HashMap<String, String> emailToNameMap = new HashMap<>();
        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return null;
            }

            String sQueryToGetCourseOfStudent = "select courses.course_id from courses"
                    + " inner join user_courses on"
                    + " user_courses.course_id= courses.course_id"
                    + " and user_courses.user_email =?";

            PreparedStatement ps = con.prepareStatement(sQueryToGetCourseOfStudent);
            ps.setString(1, sStudentEmail);

            ResultSet rs;
            String sCourseId = null;
            rs = ps.executeQuery();
            if (rs.next()) {
                sCourseId = rs.getString("course_id");
            }

            //String sQuery = "Select firstname, lastname, email_id from user_details where user_type=?";
            String sQuery = "Select courses.course_name, courses.course_id, user_details.firstname, user_details.lastname, user_details.email_id"
                    + " from user_details  inner join user_courses on user_details.email_id = user_courses.user_email"
                    + " inner join courses on user_courses.course_id = courses.course_id and user_details.user_type=?"
                    + " and user_courses.course_id =?";
            ps = con.prepareStatement(sQuery);
            ps.setString(1, sUserType);
            ps.setString(2, sCourseId);

            rs = ps.executeQuery();

            String sEmail, sName;
            while (rs.next()) {
                sEmail = rs.getString("email_id");
                sName = rs.getString("firstname") + " " + rs.getString("lastname");
                emailToNameMap.put(sEmail, sName);
            }

        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailToNameMap;
    }

    public static HashMap<String, String> queryToFetchCourses() {
        HashMap<String, String> idtoCourseNameMap = new HashMap<>();
        try {

            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return null;
            }
            String sQuery = "select course_id, course_name from courses order by course_id desc";
            PreparedStatement ps = con.prepareStatement(sQuery);

            ResultSet rs;
            rs = ps.executeQuery();

            while (rs.next()) {
                idtoCourseNameMap.put(rs.getString("course_id"), rs.getString("course_name"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idtoCourseNameMap;
    }

    public static Login_Access_User queryToFetchUserDetails(String sEmail) {
        Login_Access_User user = new Login_Access_User();
        try {
            Connection con = DbConnector.getConnection();
            if (con.isClosed()) {
                return user;
            }

            String sQuery = "select firstname, lastname, mobile, itu_id, user_type from user_details where email_id = ? ";

            PreparedStatement ps = con.prepareStatement(sQuery);
            ps.setString(1, sEmail);

            ResultSet rs;
            rs = ps.executeQuery();

            if (rs.next()) {
                user.setsFName(rs.getString("firstname"));
                user.setsLName(rs.getString("lastname"));
                user.setsMobile(rs.getString("mobile"));
                user.setITUid(rs.getString("itu_id"));
                user.setsUserType(rs.getString("user_type"));

            }

            sQuery = "select course_id from user_courses where user_email = ? ";
            ps = con.prepareStatement(sQuery);
            ps.setString(1, sEmail);

            rs = ps.executeQuery();
            while (rs.next()) {
                ArrayList<String> courseList = user.getCourseList();
                courseList.add(rs.getString("course_id"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(QueryDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }

}//class

