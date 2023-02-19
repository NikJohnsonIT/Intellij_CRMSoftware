package DAO;
import com.mysql.cj.Query;
import model.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;
import utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object for Contacts. */
public class ContactsDAO {

    /**List containing all contacts. */
    public static ObservableList<Contact> allContacts;

    /**Function responsible for getting all contacts.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of all contacts. */
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.startConnection();
        allContacts = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT * FROM contacts");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int conId = rs.getInt("Contact_ID");
            String conName = rs.getString("Contact_Name");
            String conEmail = rs.getString("Email");
            allContacts.add(new Contact(conId, conName, conEmail));
        }
        return allContacts;
    }

    /**Function responsible for returning a contact name from a given contact ID.
      * @param conId the contact ID to return a name from.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a contact Name associated with a provided contact ID. */
    public static String getConNameFromId(int conId) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String conName;
        String sqlStatement = ("SELECT Contact_Name FROM contacts WHERE Contact_ID=" + conId);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        rs.next();
        conName = rs.getString("Contact_Name");
        return conName;
    }

    /**Function to get a contact ID from a contact Name.
      * @param conName the name of the contact from which to retrieve the contact ID.
      * @throws SQLException When a DB exception is detected.
      * @return the contact ID of the entered contact. */
    public static int getConIdFromName(String conName) throws SQLException{
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("SELECT * FROM contacts WHERE Contact_Name='" + conName+"'");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int conId = rs.getInt("Contact_ID");
        return conId;
    }
}
