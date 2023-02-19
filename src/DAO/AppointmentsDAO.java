package DAO;
import controller.MainController;
import controller.ReportsController;
import model.Appointment;
import model.Customer;
import model.User;
import model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;
import utility.JDBC;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object Class for appointments. */
public class AppointmentsDAO {
    /**Function responsible for checking if there is an appointment within 15 minutes of the current time.
      * @return Returns the Appointment ID of the upcoming Appointment is returned, if no upcoming is in the next 15 minutes the value remains 0.
      * @throws SQLException When a DB exception is detected. */
    public static int upcomingAppointment() throws SQLException {
        LocalTime currentTime = LocalTime.now().truncatedTo(ChronoUnit.MINUTES);
        String sqlStatement = "SELECT * FROM client_schedule.appointments WHERE date_format(Start, '%Y-%m-%d') = curDate() and Start >= curtime() limit 1";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        int upcomingApptId = 0;
        if (rs.next()){
            Time apptStartTime = rs.getTime("Start");
            long timeDifference = ChronoUnit.MINUTES.between(currentTime, apptStartTime.toLocalTime());
            if (timeDifference <= 15) upcomingApptId = rs.getInt("Appointment_ID");
        } else upcomingApptId = 0;
        return upcomingApptId;
    }

    /**Function responsible for finding the total appointments matching a given month and appointment type.
      * @return Returns a filtered total number of appointments by type and month.
      * @throws SQLException When a DB exception is detected.  */
    public static ObservableList<Appointment> totalApptsByMonthAndType() throws SQLException {
        String sqlStatement = "SELECT COUNT(Appointment_ID) AS totalAppts, Type, month(start) AS monthStart FROM appointments GROUP BY month(start), Type ORDER BY month(start)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        int filteredTotal = -1;
        ObservableList<Appointment> countOfAppointments = FXCollections.observableArrayList();
        while (rs.next()) {
            String type2 = rs.getString("Type");
            filteredTotal = rs.getInt(1);
            int countOfAppts = rs.getInt("totalAppts");
            int monthStart = rs.getInt("monthStart");
            Appointment a = new Appointment(type2, countOfAppts, monthStart);
            countOfAppointments.add(a);
            System.out.println(a.getType());
        }
        return countOfAppointments;
    }

    /**Function responsible for counting the total number of customers in the database.
      * @return Returns the total number of customers in the DB is returned to update label text.
      * @throws SQLException When a DB exception is detected. */
    public static int totalCustomersLabelTxt() throws SQLException {
        String sqlStatement = "SELECT COUNT(Customer_ID) AS totalCust FROM customers";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        int totalCust = -1;
        if(rs.next()) {
            totalCust = rs.getInt(1);
        }
        return totalCust;
    }

    /**List containing all appointments. */
    public static ObservableList<Appointment> allAppts;
    /**Date and time formatter, establishes the format in which to display date and times. */
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**Function to get all appointments.
      * @return Return a list of all appointments in the DB.
      * @throws SQLException When a DB exception is detected. */
    public static ObservableList<Appointment> getAllAppts() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allAppts = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name AS Contact, " + "" +
                "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID FROM appointments AS A JOIN contacts AS c ON a.Contact_ID=c.Contact_ID");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            int contactId = rs.getInt("Contact_ID");
            String contact = rs.getString("Contact");
            String type = rs.getString("Type");
            LocalDateTime startData = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endData = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            allAppts.add(new Appointment(apptId, custId, userId, contactId, contact, apptTitle, desc, loc, type, startData, endData));
        }
        return allAppts;
    }
    /**Function to retrieve appointment data from the database for updates.
      * @param apptIdForUpdate the appointment ID of the appointment to be retrieved for updates.
      * @return returns a list of all appointments matching the appointment ID entered as a parameter, a single matching result is returned.
      * @throws SQLException When a DB exception is detected. */
    public static ObservableList<Appointment> getAllAppts(int apptIdForUpdate) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allAppts = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name AS Contact, " + "" +
                "a.Type, a.Start, a.End, a.Customer_ID, a.User_ID, a.Contact_ID FROM appointments AS A JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE a.Appointment_ID <> "+ apptIdForUpdate);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            int contactId = rs.getInt("Contact_ID");
            String contact = rs.getString("Contact");
            String type = rs.getString("Type");
            LocalDateTime startData = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endData = rs.getTimestamp("End").toLocalDateTime();
            int custId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            allAppts.add(new Appointment(apptId, custId, userId, contactId, contact, apptTitle, desc, loc, type, startData, endData));
        }
        return allAppts;
    }

    /**Function to get appointments from the current day and six following days.
      * @return Return all appoointments during the current week beginning on a Monday as per Java 8 yearweek method.
      * @throws SQLException When a DB exception is detected. */
    public static ObservableList<Appointment> getApptsForNext7Days() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allAppts = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name AS Contact, a.Start, a.End, a.Customer_ID, a.Type, " +
                "a.User_ID, a.Contact_ID FROM appointments AS A JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE yearweek(start) = yearweek(now())");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String contact = rs.getString("Contact");
            int contactId = rs.getInt("Contact_ID");
            String apptType = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cusId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            allAppts.add(new Appointment(apptId, cusId, userId, contactId, contact, apptTitle, desc, loc, apptType, startTime, endTime));
        }
        return allAppts;
    }

    /**Function to get appointments from the current day and following month.
      * @return Return a list of all appointments during the current month beginning on the 1st.
      * @throws SQLException When a DB exception is detected. */
    public static ObservableList<Appointment> getAppointmentsForNextMonth() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allAppts = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT a.Appointment_ID, a.Title, a.Description, a.Location, c.Contact_Name AS Contact, a.Start, a.End, a.Customer_ID, a.Type, " +
                "a.User_ID, a.Contact_ID FROM appointments AS A JOIN contacts AS c ON a.Contact_ID=c.Contact_ID WHERE month(start) = month(now())");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            String contact = rs.getString("Contact");
            int contactId = rs.getInt("Contact_ID");
            String apptType = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cusId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            allAppts.add(new Appointment(apptId, cusId, userId, contactId, contact, apptTitle, desc, loc, apptType, startTime, endTime));
        }
        return allAppts;
    }

    /**Function responsible for updating an appointment.
      * @param apptTitle title of the associated appointment.
      * @param desc description of the associated appointment.
      * @param loc location of the associated appointment.
      * @param type type of the associated appointment.
      * @param apptStart start time of the associated appointment.
      * @param apptEnd end time of the associated appointment.
      * @param cusId customer ID associated with the appointment.
      * @param userId user ID associated with the appointment.
      * @param contactId contact ID of contact associated with the appointment.
      * @param apptIdForUpdate appointment ID of the appointment.
      * @throws SQLException When a DB exception is detected.
      */
    public static void updateAppt(String apptTitle, String desc, String loc, String type, ZonedDateTime apptStart, ZonedDateTime apptEnd, int cusId, int userId, int contactId, int apptIdForUpdate) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("UPDATE appointments SET Title='"+apptTitle+"',Description='"+desc+"',Location='"+loc+"',Type='"+type+"',Start='"+formatter.format(apptStart.withZoneSameInstant(ZoneId.of("Europe/London")))+"',End='"+formatter.format(apptEnd.withZoneSameInstant(ZoneId.of("Europe/London")))+"',Last_Update='"+formatter.format(ZonedDateTime.now(ZoneId.of("UTC")))+
        "',Last_Updated_By='"+UsersDAO.getCurrentUser().getUserName()+
                "',Customer_ID="+ cusId +",User_ID="+ userId +",Contact_ID="+ contactId +" WHERE Appointment_ID="+ apptIdForUpdate);
        System.out.println(sqlStatement);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ps.execute();

    }

    /**Function responsible for deleting an appointment.
      * @param apptIdForDelete appointment ID of appointment to be deleted.
      * @return returns the number of rows affected to the console.
      * @throws SQLException When a DB exception is detected. */
    public static int deleteAppt(int apptIdForDelete) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("DELETE FROM appointments WHERE Appointment_ID = ?");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ps.setInt(1, apptIdForDelete);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**Function responsible for getting appointments filtered by customer.
      * @param customer the customer to filter appointments by.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of appointments narrowed to only matching the provided customer.*/
    public static ObservableList<Appointment> getApptsByCust(Customer customer) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Appointment> apptsByCust = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT * FROM appointments AS a JOIN customers AS b ON a.Customer_ID = b.Customer_ID WHERE a.Customer_ID=" + customer.getCusId());
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            int conId = rs.getInt("Contact_ID");
            String apptType = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cusId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            apptsByCust.add(new Appointment(apptId, cusId, userId, conId, ContactsDAO.getConNameFromId(conId), apptTitle, desc, loc, apptType, startTime, endTime));
        }
        return apptsByCust;
    }

    /**Function repsonsible for getting appointments filtered by user.
      * @param user the user to filter appointments by.
      * @throws SQLException When a DB exception is detected.
      * @return Returns appointments filtered by user. */
    public static ObservableList<Appointment> getApptsByUser(User user) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Appointment> apptsByUser = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT * FROM appointments AS a JOIN customers AS b ON a.Customer_ID = b.Customer_ID WHERE a.User_ID=" + user.getUserId());
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            int conId = rs.getInt("Contact_ID");
            String apptType = rs.getString("Type");
            LocalDateTime apptStart = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime apptEnd = rs.getTimestamp("End").toLocalDateTime();
            int cusId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");

            apptsByUser.add(new Appointment(apptId, cusId, userId, conId, ContactsDAO.getConNameFromId(conId), apptTitle, desc, loc, apptType, apptStart, apptEnd));
        }
        return apptsByUser;
    }

    /**Function responsible for getting appointments filtered by contact.
      * @param contact the customer to filter appointments by.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of appointments filtered by contact . */
    public static ObservableList<Appointment> getApptsByCon(Contact contact) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Appointment> apptsByCon = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT * FROM appointments AS a JOIN customers AS b ON a.Customer_ID = b.Customer_ID WHERE a.Contact_ID=" + contact.getConId());
        System.out.println(sqlStatement);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int apptId = rs.getInt("Appointment_ID");
            String apptTitle = rs.getString("Title");
            String desc = rs.getString("Description");
            String loc = rs.getString("Location");
            int conId = rs.getInt("Contact_ID");
            String apptType = rs.getString("Type");
            LocalDateTime startTime = rs.getTimestamp("Start").toLocalDateTime();
            LocalDateTime endTime = rs.getTimestamp("End").toLocalDateTime();
            int cusId = rs.getInt("Customer_ID");
            int userId = rs.getInt("User_ID");
            apptsByCon.add(new Appointment(apptId, cusId, userId, conId, ContactsDAO.getConNameFromId(conId), apptTitle, desc, loc, apptType, startTime, endTime));
        }
        return apptsByCon;
    }

    /**Function responsible for getting appointment IDs from the appointment table.
     * @throws SQLException When a DB exception is detected.
     * @return returns a list of appointment IDs. */
    public static ObservableList<Integer> getApptIDs() throws SQLException{
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Integer> apptIDs = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT Appointment_ID FROM appointments");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){}
        return apptIDs;
    }

    /**Function responsible for creating an appointment.
      * @param title title of the associated appointment.
      * @param desc description of the associated appointment.
      * @param loc location of the associated appointment.
      * @param type type of the associated appointment.
      * @param apptStart start time of the associated appointment.
      * @param apptEnd end time of the associated appointment.
      * @param cusId customer ID associated with the appointment.
      * @param userId user ID associated with the appointment.
      * @param conId contact ID associated with the appointment.
      * @throws SQLException When a DB exception is detected. */
    public static void createAppt( String title, String desc, String loc, String type, ZonedDateTime apptStart, ZonedDateTime apptEnd, int cusId, int userId, int conId) throws SQLException {
        System.out.println("creating appt");
        String sqlStatement = ("INSERT INTO appointments (Title, Description, Location, Type, Start, End, Create_Date, Created_By, Last_Update, Last_Updated_By, Customer_ID, User_ID, Contact_ID) VALUES ( ?, ?, ?, ?, ?, ?, now(), ?, now(), ?, ?, ?, ?)");
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, title);
        ps.setString(2, desc);
        ps.setString(3, loc);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(apptStart.toLocalDateTime()));
        ps.setTimestamp(6, Timestamp.valueOf(apptEnd.toLocalDateTime()));
        ps.setString(7, String.valueOf(UsersDAO.getCurrentUser()));
        ps.setString(8, String.valueOf(UsersDAO.getCurrentUser()));
        ps.setInt(9, cusId);
        ps.setInt(10, userId);
        ps.setInt(11, conId);
        Connection connection = DBConnection.getConnection();
        ps.execute();
    }
}
