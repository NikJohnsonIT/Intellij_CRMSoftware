package controller;

import DAO.AppointmentsDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for the Main screen of the program. */
public class MainController implements Initializable {

    private static User currUser;
    Stage stage;
    Parent scene;
    /**Appointments button. */
    @FXML public Button mainScreenApptsBtn;
    /**Customers button. */
    @FXML public Button mainScreenCustBtn;
    /**Reports button. */
    @FXML public Button mainScreenReportsBtn;
    /**Event handler responsible for opening the appointments screen.
      * @param event the Appointments button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts.*/
    @FXML public void onActionOpenApptsScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/Appointments.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Event handler responsible for opening the customers screen.
      * @param event the Customers button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts.*/
    @FXML public void onActionOpenCustScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/Customers.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Event handler responsible for opening the reports screen.
      * @param event the Reports button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionOpenReportsScreen(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/Reports.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Function that gets the currently active user.
     * @return returns the current active user. */
    public static User getCurrUser() {
        return currUser;
    }

    /**Function that sets the current user to the actively logged-in user.
      * @param currUser the current user. */
    public static void setCurrUser(User currUser) {
        MainController.currUser = currUser;
    }

    /**Method to intialize the page, time is checked against upcoming appointments and an alert message is displayed. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       LocalDateTime currentTimeForComparison = LocalDateTime.now();
        int upcomingApptId = 0;
        try {
            upcomingApptId = AppointmentsDAO.upcomingAppointment();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Notice!");
        if (upcomingApptId > 0) {alert.setContentText("Appointment with ID: "+ upcomingApptId + " is coming up today, " + LocalDate.now()+ " within 15 minutes!");}
        else{alert.setContentText("No Appointments in the next 15 min.");}
        Optional<ButtonType> result = alert.showAndWait();
    }

}
