package main;

import DAO.ContactsDAO;
import DAO.CountriesDAO;
import DAO.DivisionsDAO;
import DAO.UsersDAO;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.User;
import utility.DBConnection;
import utility.JDBC;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
/**@author Nick Johnson Student_ID# 001354777
 * This class creates a scheduling app meeting the project requirements of QAM2 Task 1.
  */
public class ScheduleApp extends Application {
    @Override
    /** This method loads the login page of the application when the application starts.  */
    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(
                (getClass().getResource("/resources/LoginScreen.fxml")));

        //Scene scene = new Scene(root);

        stage.setTitle("Login Screen");
        stage.setScene(new Scene(root, 800, 600));
        stage.show();


    }
    /** This is the main method.
     * This is the first method called when the program is ran. Various lists are filled with data from the corresponding DB.
     * @param args array of command line arguments for the application.
     * @throws SQLException When a DB exception is detected.
     */
    public static void main(String[] args) throws SQLException {
        JDBC.openConnection();
        ContactsDAO.getAllContacts();
        CountriesDAO.getAllCountries();
        DivisionsDAO.getAllDivisions();
        UsersDAO.getAllUsers();
        launch(args);
        JDBC.closeConnection();

    }
}

