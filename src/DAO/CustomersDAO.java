package DAO;
import model.Appointment;
import model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;
import utility.JDBC;

import java.sql.*;
import java.time.LocalDateTime;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object for customers. */
public class CustomersDAO {

    /**Function responsible for getting all customers.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of all customers. */
    public static ObservableList<Customer> getAllCustomers() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Customer> allCust = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, c.Create_Date, c.Created_By, c.Last_Update, c.Last_Updated_By, first_level_divisions.Division_ID, first_level_divisions.Country_ID " +
                "FROM customers AS c JOIN first_level_divisions ON c.Division_ID = first_level_divisions.Division_ID");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int custId = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String pCode = rs.getString("Postal_Code");
            String phoneNum = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime latestUpdateData = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divId = rs.getInt("Division_ID");
            int countryId = rs.getInt("Country_ID");

            allCust.add(new Customer(custId, custName, address, pCode, phoneNum, createDate, createdBy, latestUpdateData, lastUpdatedBy, divId, countryId));
        }
        return allCust;
    }

    /**Function to get all customer data using a customer ID to specify record.
      * @param custIdForUpdate the customer ID of the record being updated.
      * @throws SQLException When a DB exception is detected.
      * @return returns an updated list of all customer records, including the updated record. */
    public static ObservableList<Customer> getAllCust(int custIdForUpdate) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<Customer> allCust = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID FROM customers WHERE Customer_ID <> "+ custIdForUpdate);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int custId = rs.getInt("Customer_ID");
            String custName = rs.getString("Customer_Name");
            String address = rs.getString("Address");
            String pCode = rs.getString("Postal_Code");
            String phoneNum = rs.getString("Phone");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime latestUpdateData = rs.getTimestamp("Last_Update").toLocalDateTime();
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            int divId = rs.getInt("Division_ID");

            allCust.add(new Customer(custId, custName, address, pCode, phoneNum, createDate, createdBy, latestUpdateData, lastUpdatedBy, divId));

        }
        return allCust;
    }

    /**Function responsible for updating a customer.
      * @param custName name of the customer.
      * @param address address of the customer.
      * @param pCode post code of the customer.
      * @param phone phone number of the customer.
      * @param userId user ID of the customer.
      * @param divId division ID of the customer.
      * @param custId customer ID number.
      * @throws SQLException When a DB exception is detected. */

    public static void updateCust(String custName, String address, String pCode, String phone, String userId, int divId, int custId) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("UPDATE customers SET Customer_Name='" + custName + "',Address='" + address +
                "',Postal_Code='" + pCode + "',Phone='" + phone + "', Last_Update=NOW(), Last_Updated_By='" +
                userId + "', Division_ID=" + Integer.toString(divId) + " WHERE Customer_ID=" + custId);
        PreparedStatement ps = connection.prepareStatement(sqlStatement);

        ps.execute();
    }

    /**Function responsible for creating a new customer.
     * @param custName the name of the new customer record.
     * @param address the address of the new customer record.
     * @param pCode the post code of the new customer record.
     * @param phoneNum the phone number of the new customer record.
     * @param divId the division ID of the new customer record.
     * @throws SQLException When a DB exception is detected. */

    public static void createNewCust(String custName, String address, String pCode, String phoneNum, int divId) throws SQLException {
        String sqlStatement = "INSERT INTO customers (Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, now(), ?, now(), ?, ?)";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, custName);
        ps.setString(2, address);
        ps.setString(3, pCode);
        ps.setString(4, phoneNum);
        ps.setString(5, String.valueOf(UsersDAO.getCurrentUser()));
        ps.setString(6, String.valueOf(UsersDAO.getCurrentUser()));
        ps.setInt(7, divId);
        ps.execute();
    }

    /**Function responsible for deleting a customer.
      @param custId the customer ID of the customer to be deleted. */
    public static void deleteCust(int custId) {
    String sqlStatement = ("DELETE FROM customers WHERE Customer_ID=" + Integer.toString(custId));
    }

    /**Function responsible for deleting Appointments before customers.
      * @throws SQLException When a DB exception is detected.
      * @param custID customer ID of the customer to be deleted. */
    public static void deleteApptsThenCust(int custID) throws SQLException{
        Connection connection = DBConnection.getConnection();
        String sqlStatement1 = ("DELETE FROM appointments WHERE Customer_ID=" + Integer.toString(custID));
        String sqlStatement2 = ("DELETE FROM customers WHERE Customer_ID=" + Integer.toString(custID));
        PreparedStatement ps = connection.prepareStatement(sqlStatement1);
        PreparedStatement ps2 = connection.prepareStatement(sqlStatement2);
        ps.execute();
        ps2.execute();
    }

    public static ObservableList<Integer> allCustId;
    /**Funtion responsible for retrieving all customer IDs.
     * @throws SQLException When a DB exception is detected.
     * @return Returns a list of all customer IDs. */
    public static ObservableList<Integer> getAllCustId() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allCustId = FXCollections.observableArrayList();
        String sqlStatement = "SELECT Customer_ID FROM CUSTOMERS";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int custId = rs.getInt("Customer_ID");
            allCustId.add(custId);
        }
        return allCustId;
    }
}
