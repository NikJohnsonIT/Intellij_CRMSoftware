package utility;

import java.sql.Connection;
import java.sql.DriverManager;

/**@author Nick Johnson Student_ID# 001354777
 * Java DataBase Connection class. */
public abstract class JDBC {
    /**Protocol of DB connection. */
    private static final String protocol = "jdbc";
    /**Vendor of database. */
    private static final String vendor = ":mysql:";
    /**Location of database. */
    private static final String location = "//localhost/";
    /**Name of database. */
    private static final String databaseName = "client_schedule";
    /**URL for database. */
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    /**Driver for database. */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**Database username. */
    private static final String userName = "sqlUser";
    /**Database password. */
    private static String password = "Passw0rd!";
    /**Connection interface. */
    public static Connection connection;

    /**Function to open connection to database, displays relevant error message in console if connection fails. */
    public static void openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }

    /**Function to handle closing the connection to the database. */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}
