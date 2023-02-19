package utility;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**@author Nick Johnson Student_ID# 001354777
 * Database connection Utiility class. */
public class DBConnection {
    /**Database IP address. */
    private static final String ipAddress = "jdbc:mysql://localhost:3306/client_schedule";

    /**Database username. */
    private static final String username = "sqlUser";
    /**Database password matching username. */
    private static final String password ="Passw0rd!";
    /**Database connection. */
    private static Connection conn = null;


    /**Function to establish connection to database.
     * @return Returns the connection. */
    public static Connection startConnection() {
        try {
            conn = DriverManager.getConnection(ipAddress, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Connection Successful");
        return conn;
    }

    /**Getter for database connection, returns connection.
     * @return Returns Connection. */
    public static Connection getConnection(){
        return conn;
    }

}
