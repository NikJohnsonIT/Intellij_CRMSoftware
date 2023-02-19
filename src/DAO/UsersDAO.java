package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.User;
import utility.DBConnection;
import utility.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object for Users. */
public class UsersDAO {

    /**Variable for the current user ID. */
    public static User currentUser;
    public static int currentUserId;

    /**List of all users. */
    public static ObservableList<User> allUsers;

    /**Function responsible for getting all users.
     * @throws SQLException When a DB exception is detected.
     * @return Returns a list of all users. */
    public static ObservableList<User> getAllUsers() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allUsers = FXCollections.observableArrayList();
        String sqlStatement =
                "SELECT * FROM USERS";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String pw = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime mostRecentUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String mostRecentUpdateBy = rs.getString("Last_Updated_By");
            User user = new User(userId, userName, pw, createDate, createdBy, mostRecentUpdate, mostRecentUpdateBy);
            allUsers.add(user);
        }
        return allUsers;
    }

    /**Function to get all users, by User ID.
     * @param userId2 the User ID with which to filter the list of results.
     * @throws SQLException When a DB exception is detected.
     * @return returns a user with matching user ID. */
    public static User getAllUsersById(int userId2) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allUsers = FXCollections.observableArrayList();
        String sqlStatement =
                "SELECT * FROM USERS WHERE USER_ID = " +userId2;
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        User user = null;
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String pw = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime mostRecentUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String mostRecentUpdateBy = rs.getString("Last_Updated_By");
            user = new User(userId, userName, pw, createDate, createdBy, mostRecentUpdate, mostRecentUpdateBy);

        }
        return user;

    }

    public static ObservableList<Integer> allUserId;
    /**Function responsible for getting all User ID.
     * @throws SQLException When a DB exception is detected.
     *  @return Returns a list of all User IDs. */
    public static ObservableList<Integer> getAllUserId() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allUserId = FXCollections.observableArrayList();
        String sqlStatement = "SELECT User_ID FROM USERS";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            allUserId.add(userId);
        }
        return allUserId;
    }

    /**Function to get a user ID from a username.
      * @param username the username from which to get user ID.
      * @throws SQLException When a DB exception is detected. */
    public static void getUserId(String username) throws SQLException {
        User currentUser;
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = "SELECT User_ID FROM users WHERE User_Name LIKE ?";
        PreparedStatement ps = JDBC.connection.prepareStatement(sqlStatement);
        ps.setString(1, username);

    }

    /**Function to get all users matching a given username and password.
      * @param username the username used to filter results.
      * @param password the password used to filter results.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of all users matching provided credentials. */
    public static ObservableList<User> getAllUsers(String username, String password) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        allUsers = FXCollections.observableArrayList();
        String sqlStatement =
                "SELECT * FROM users WHERE User_Name = '" + username + "' AND Password = '" + password + "'";
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String pw = rs.getString("Password");
            LocalDateTime createDate = rs.getTimestamp("Create_Date").toLocalDateTime();
            String createdBy = rs.getString("Created_By");
            LocalDateTime mostRecentUpdate = rs.getTimestamp("Last_Update").toLocalDateTime();
            String mostRecentUpdateBy = rs.getString("Last_Updated_By");
            User user = new User(userId, userName, pw, createDate, createdBy, mostRecentUpdate, mostRecentUpdateBy);
            allUsers.add(user);
            currentUser = user;
            currentUserId = userId;
        }
        return allUsers;
    }

    /**Function to get the current User.
      * @return Returns the current User. */
    public static User getCurrentUser() {
        return currentUser;
    }

    /**Function for setting the current user.
     * @param currentUser the current user. */
    public static void setCurrentUser(User currentUser) {
        UsersDAO.currentUser = currentUser;

    }
}




