package classes.com.model.Database;

import Constants.Constants;
import Constants.Constants.Status;
import classes.com.model.DBTables.Appointment;
import classes.com.model.DBTables.Schedule;
import classes.com.model.DBTables.SetupAppointment;
import classes.com.model.EAppointment_Users.User;
import classes.com.model.EAppointment_Users.Login_Access_User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * InsertToDatabase.java - A class that populates different tables of the
 * Eappointment Database
 *
 * @author Ekta Khiani
 * @version 1.0
 * @date 03/16/2016
 */
public class InsertToDatabase {

    public static Status InsertUserDetails(Login_Access_User userdetails) throws SQLException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;
        int nNoOfRowsAffected = 0;
        Status status = Status.FAILURE;

        try {
            if (con.isClosed()) {
                return status;
            } else {
                String sEmail = userdetails.getsEmail();
                String tmppassword = "ITU@123";
				String sUser = QueryDatabase.validateLogin(sEmail, tmppassword, userdetails.getsUserType());
                
			/*	if(sUser.equals("UnKnown"))
					return status;
				*/
				String sQuery = "insert into user_details (email_id, firstname, lastname, mobile, password, user_type, itu_id)  values (?, ?, ? ,? ,?, ?, ?)";

                ps = con.prepareStatement(sQuery);
                ps.setString(1, sEmail);
                ps.setString(2, userdetails.getsFName());
                ps.setString(3, userdetails.getsLName());
                ps.setString(4, userdetails.getsMobile());
                ps.setString(5, tmppassword);
                ps.setString(6, userdetails.getsUserType());
                ps.setString(7, userdetails.getITUid());

                nNoOfRowsAffected = ps.executeUpdate();
                //Add Courses
                ArrayList<String> courseArr = userdetails.getCourseList();
                if (!courseArr.isEmpty()) {
                    InsertCourses(sEmail, courseArr);
                }
            }//else
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nNoOfRowsAffected > 0) {
            status = Status.SUCCESS;
        }

        return status;

    }// End of Insert

    public static Status InsertAppointments(Appointment appointment, String type) throws SQLException, ParseException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;
        int nNoOfRowsAffected = 0;
        Status status = Status.FAILURE;

        try {
            if (!con.isClosed()) {
                String sDate = appointment.getsDate();
                SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
                Date date = formatter.parse(sDate);

                String sDateFormat = "EEEE";
                formatter = new SimpleDateFormat(sDateFormat);
                String sDay = formatter.format(date);

                String queryToFetchEndTime = "select  end_time from setup_appointments"
                        + " where email=? and day=? and start_time = ?";
                ps = con.prepareStatement(queryToFetchEndTime);
                ps.setString(1, appointment.getsEmailOfAppointmentWith());
                ps.setString(2, sDay);
                ps.setString(3, appointment.getsStartTime());

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    appointment.setsEndTime(rs.getString("end_time"));
                }

                String sEmailType = (type.equalsIgnoreCase("Prospect")) ? "prospect_email" : "student_email";

                String sQuery = "insert into appointments (staff_email, date, appointment_start_time, appointment_end_time, appointment_status, appointment_notes, " + sEmailType + ") "
                        + " values (?, ?, ? ,? ,?, ?, ?)";

                ps = con.prepareStatement(sQuery);
                ps.setString(1, appointment.getsEmailOfAppointmentWith());
                ps.setString(2, appointment.getsDate());
                ps.setString(3, appointment.getsStartTime());
                ps.setString(4, appointment.getsEndTime());
                ps.setString(5, appointment.getsApptStatus());
                ps.setString(6, appointment.getsApptNotes());
                ps.setString(7, appointment.getsEmailOfAppointee());

                nNoOfRowsAffected = ps.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nNoOfRowsAffected > 0) {
            status = Status.SUCCESS;
        }

        return status;

    }

    public static boolean InsertProspectStudents(User prospect) throws SQLException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;
        try {
            if (con.isClosed()) {
                return false;
            }
			else
			{
				String sEmail = prospect.getsEmail();
				String sQuery=null;
				if(QueryDatabase.propectExist(sEmail))
				{
				
					sQuery = "UPDATE prospect_students"
					+ " SET first_name=?, last_name=?, mobile=?"
					+ " WHERE email=? ";
				}
				else
				{
				
					sQuery = "insert into prospect_students"
				+ " (first_name, last_name, mobile, email) "
                        + " values (?, ?, ? ,? )";
				}
                ps = con.prepareStatement(sQuery);
                ps.setString(1, prospect.getsFName());
                ps.setString(2, prospect.getsLName());
                ps.setString(3, prospect.getsMobile());
				ps.setString(4, prospect.getsEmail());

                ps.executeUpdate();

            }
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;

    }

    //Set up the appointments 
    public static boolean insertSetupAppointments(SetupAppointment setup) {
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;
            if (con.isClosed()) {
                return false;
            } else {
				//Delete previous Setup.
				String queryDeleteSetup = "delete from setup_appointments where email=?";
				ps = con.prepareStatement(queryDeleteSetup);
				ps.setString(1, setup.getsEmail());
                ps.executeUpdate();

				//Insert New SetUp.
                String sql = "insert into setup_appointments(email, day, start_time, end_time, duration, timeslots) values (?, ?, ?, ?, ?, ?)";
                ps = con.prepareStatement(sql);
                String sRangeTimeSlot;
                int count = 0;

                for (Schedule schedule : setup.getSchedule()) {
                    ArrayList<String> timeSlotList = schedule.getTimeSlots();

                    sRangeTimeSlot = schedule.getsStartHour() + ":" + schedule.getsStartMinutes() + " " + schedule.getsStartAmPm() + " " + "-" + " " + schedule.getsEndHour() + ":" + schedule.getsEndMinutes() + " " + schedule.getsEndAmPm();
                    for (int eachTimeSlot = 0; eachTimeSlot < timeSlotList.size() - 1; eachTimeSlot++) {
                        ps.setString(1, setup.getsEmail());
                        ps.setString(2, schedule.getsDay());
                        ps.setString(3, timeSlotList.get(eachTimeSlot));
                        ps.setString(4, timeSlotList.get(eachTimeSlot + 1));
                        ps.setString(5, schedule.getsDuration());
                        ps.setString(6, sRangeTimeSlot);
                        ps.addBatch();

                        if (++count % Constants.BATCHSIZE == 0) {
                            ps.executeBatch();
                        }
                    }
                }

                ps.executeBatch();
                ps.close();
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }

    private static Status InsertCourses(String sEmail, ArrayList<String> courseList) throws SQLException {
        Connection con = DbConnector.getConnection();
        PreparedStatement ps = null;
        int nNoOfRowsAffected[] = null;
        Status status = Status.FAILURE;

        try {
            if (con.isClosed()) {
                return status;
            } else {

                String sQuery = "Insert into user_courses values(?, ?);";

                ps = con.prepareStatement(sQuery);

                for (String course : courseList) {
                    ps.setString(1, sEmail);
                    ps.setString(2, course);
                    ps.addBatch();
                }

                nNoOfRowsAffected = ps.executeBatch();
                ps.close();

            }//else
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nNoOfRowsAffected.length > 0) {
            status = Status.SUCCESS;
        }

        return status;

    }// End of Insert*/

    public static Status updateUser(Login_Access_User user) {
        Status status = Status.FAILURE;
        int nNoOfRowsAffected = 0;

        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Del its courses initially.
            deleteCourses(user.getsEmail());
            //Update details of user.
            String queryToUpdateUser = "UPDATE user_details"
                    + " SET  firstname=?, lastname=?, mobile=? WHERE email_id=?";

            ps = con.prepareStatement(queryToUpdateUser);
            ps.setString(1, user.getsFName());
            ps.setString(2, user.getsLName());
            ps.setString(3, user.getsMobile());
            ps.setString(4, user.getsEmail());
            nNoOfRowsAffected = ps.executeUpdate();
            //Insert Updated Courses 

            ArrayList<String> courseList = user.getCourseList();

            if (!courseList.isEmpty()) {
                InsertCourses(user.getsEmail(), courseList);
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (nNoOfRowsAffected > 0) {
            status = Status.SUCCESS;
        }

        return status;
    }

    private static Status deleteSetUp(String sEmail) {
        Status status = Status.FAILURE;

        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Del its courses initially.
            String queryToDelSetup = "DELETE FROM setup_appointments WHERE email=?";

            ps = con.prepareStatement(queryToDelSetup);
            ps.setString(1, sEmail);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;

    }

    private static Status deleteAppointments(String sEmail, String sUserType) {
        Status status = Status.FAILURE;

        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Del its courses initially.
            String sEmailType = (sUserType.equalsIgnoreCase("Student")) ? "student_email" : "staff_email";
            String queryToDelAppts = "DELETE FROM appointments WHERE " + sEmailType + "=?";

            ps = con.prepareStatement(queryToDelAppts);
            ps.setString(1, sEmail);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;

    }

    public static Status deleteCourses(String sEmail) {
        Status status = Status.FAILURE;

        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Del its courses initially.
            String queryToDelCourses = "DELETE FROM user_courses WHERE user_email=?";

            ps = con.prepareStatement(queryToDelCourses);
            ps.setString(1, sEmail);
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

    public static Status deleteUser(String sEmail, String sUserType) {
        int nNoofRowsAffected = 0;
        Status status = Status.FAILURE;
        try {
            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Delete Every Detail of the user
            deleteSetUp(sEmail);
            deleteAppointments(sEmail, sUserType);
            deleteCourses(sEmail);

            String queryToDelUser = "DELETE FROM user_details WHERE email_id=?";

            ps = con.prepareStatement(queryToDelUser);
            ps.setString(1, sEmail);
            nNoofRowsAffected = ps.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (nNoofRowsAffected > 0) {
            status = Status.SUCCESS;
        }

        return status;
    }

    public static Status updateStatusOf(Appointment appointment) {
        Status status = Status.FAILURE;
        try {
            int nNoOfRowsAffected = 0;

            Connection con = DbConnector.getConnection();
            PreparedStatement ps = null;

            if (con.isClosed()) {
                return status;
            }

            //Update details of user.
            String queryToUpdateStatus = "UPDATE appointments"
                    + " SET  appointment_status=? WHERE student_email=?"
                    + " and staff_email=?"
                    + " and date=?"
                    + " and appointment_start_time=?";

            ps = con.prepareStatement(queryToUpdateStatus);
            ps.setString(1, appointment.getsApptStatus());
            ps.setString(2, appointment.getsEmailOfAppointee());
            ps.setString(3, appointment.getsEmailOfAppointmentWith());
            ps.setString(4, appointment.getsDate());
            ps.setString(5, appointment.getsStartTime());

            nNoOfRowsAffected = ps.executeUpdate();
            if (nNoOfRowsAffected > 0) {
                status = Status.SUCCESS;
            }

        } catch (SQLException ex) {
            Logger.getLogger(InsertToDatabase.class.getName()).log(Level.SEVERE, null, ex);
        }
        return status;
    }

}//End of class
