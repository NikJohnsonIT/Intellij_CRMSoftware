package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.CustomersDAO;
import DAO.UsersDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for the update appointment form. */
public class UpdateApptController implements Initializable {

    /**Text field that displays Appointment ID, this field is disabled for user interaction. */
    @FXML public Label UpdateApptID;

    ObservableList<LocalTime> startTimesList = FXCollections.observableArrayList();
    ObservableList<LocalTime> endTimesList = FXCollections.observableArrayList();
    ObservableList<Contact> contactList = FXCollections.observableArrayList();
    ObservableList<User> userIDList = FXCollections.observableArrayList();

    /**Text field for updating the title of an appointment. */
    @FXML public TextField UpdateApptTitle;
    /**Text field for updating the description of an appointment. */
    @FXML public TextField UpdateApptDesc;
    /**Text field for updating the location of an appointment. */
    @FXML public TextField UpdateApptLoc;
    /**Date picker for updating the start date of an appointment. */
    @FXML public DatePicker UpdateApptStartDatePicker;
    /**Date picker for updating the end date of an appointment. */
    @FXML public DatePicker UpdateApptEndDatePicker;
    /**ComboBox for updating the start time of an appointment. */
    @FXML public ComboBox<LocalTime> UpdateApptStartTimeCombo;
    /**ComboBox for updating the end time of an appointment. */
    @FXML public ComboBox<LocalTime> UpdateApptEndTimeCombo;
    /**ComboBox for updating the contact of an appointment. */
    @FXML public ComboBox UpdateApptContactCombo;
    /**ComboBox for updating the type of an appointment. */
    @FXML public ComboBox UpdateApptTypeCombo;
    /**ComboBox for updating the customer ID associated with an appointment. */
    @FXML public ComboBox updateApptCustIdCombo;
    /**Label that will display the currently active user. */
    @FXML public ComboBox<User> UpdateApptUserIdCombo;
    /**Event handler responsible for canceling updates to the appointment and returning the user the appointments window.
      @param event the cancel button is clicked.
     @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts.*/
    @FXML public void onActionCancelApptUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard update and return to Appointments window?");
        Optional<ButtonType> confirmCancellation = alert.showAndWait();
        if(confirmCancellation.isPresent() && confirmCancellation.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/resources/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    Stage stage;
    Parent scene;

    /**Method responsible for building an appointment object for making edits.
     * @param apptEdit the appointment object for edits. */
    public void apptToEdit(Appointment apptEdit){
        try {
            UpdateApptTitle.setText(apptEdit.getApptTitle());
            UpdateApptDesc.setText(apptEdit.getDesc());
            UpdateApptLoc.setText(apptEdit.getLoc());
            UpdateApptStartDatePicker.setValue(apptEdit.getStart().toLocalDate());
            UpdateApptEndDatePicker.setValue(apptEdit.getEnd().toLocalDate());
            UpdateApptUserIdCombo.setItems(UsersDAO.getAllUsers());
        }
        catch(SQLException throwables){
            throwables.printStackTrace();
        }
    }

    /**Event handler responsible for for saving updates to the appointment.
     * Appointment ID is checked against existing and incremented appropriately.
     * Checks are performed to ensure updated appointments do not overlap existing appointments.
     * Relevant error messages display for exceptions.
     * @param event the save button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionSaveAppt(ActionEvent event) throws IOException {
        try {
            boolean apptUpdated = false;
            boolean apptUpdateVerify = true;
            Appointment appt = new Appointment();
            LocalDateTime start = LocalDateTime.of(UpdateApptStartDatePicker.getValue(), UpdateApptStartTimeCombo.getValue());
            appt.setStart(start);
            LocalDateTime end = LocalDateTime.of(UpdateApptEndDatePicker.getValue(), UpdateApptEndTimeCombo.getValue());
            appt.setEnd(end);
            appt.setTitle(UpdateApptTitle.getText());
            appt.setDesc(UpdateApptDesc.getText());
            appt.setLoc(UpdateApptLoc.getText());
            appt.setType(String.valueOf(UpdateApptTypeCombo.getValue()));
            appt.setConName(String.valueOf(UpdateApptContactCombo.getValue())); //works better for objects
            appt.setCusId(Integer.parseInt(String.valueOf(updateApptCustIdCombo.getValue())));
            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID()); //Local ZoneID
            ZonedDateTime zdtLocalStart = ZonedDateTime.of(start,localZoneId); //changed localTime (example) to start (from ln 83?)
            ZonedDateTime zdtOther = zdtLocalStart.withZoneSameInstant(ZoneId.of("US/Eastern")); //zdtOther should be eastern time for project requirements?
            Instant gmtInstant = zdtLocalStart.toInstant(); //local time converted to UTC
            ZonedDateTime zdtLocalEnd = ZonedDateTime.of(end,localZoneId);//added for end times
            int apptIdForUpdate = Integer.parseInt(UpdateApptID.getText());

            LocalDateTime updateApptStart = appt.getStart();
            LocalDateTime updateApptEnd = appt.getEnd();

            for (Appointment appointment : AppointmentsDAO.getAllAppts(apptIdForUpdate)) {
                for (Appointment existingStart : AppointmentsDAO.getAllAppts(apptIdForUpdate)) {
//                    if (updateApptStart.isBefore(existingStart.getStart().toLocalTime())) {
//                        if (updateApptEnd.isBefore(existingStart.getEnd().toLocalTime()) || updateApptEnd.equals(existingStart.getEnd().toLocalTime()) || updateApptEnd.isAfter(existingStart.getEnd().toLocalTime()))
                    if ((updateApptStart.isAfter(existingStart.getStart()) || updateApptStart.isEqual(existingStart.getStart())) && updateApptStart.isBefore(existingStart.getEnd())){
                            Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                            alertOverlapStart.setTitle("Warning!");
                            alertOverlapStart.setHeaderText("Overlapping Appointment Start Times");
                            alertOverlapStart.setContentText("Updated appointment interferes with existing appointment start time.");
                            alertOverlapStart.showAndWait();
                            apptUpdateVerify = false;
                            break;
                    }

//                    else if (updateApptStart.equals(existingStart.getStart().toLocalTime())) {
//                        if (updateApptEnd.isBefore(existingStart.getEnd().toLocalTime()) || updateApptEnd.equals(existingStart.getEnd().toLocalTime()) || updateApptEnd.isAfter(existingStart.getEnd().toLocalTime()))
                    if ((updateApptStart.isAfter(existingStart.getStart())) && (updateApptEnd.isBefore(existingStart.getEnd()) || updateApptEnd.isEqual(existingStart.getEnd()))){
                            Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                            alertOverlapStart.setTitle("Warning!");
                            alertOverlapStart.setHeaderText("Overlapping Appointment Times");
                            alertOverlapStart.setContentText("Updated appointment begins at the same time as another appointment.");
                            alertOverlapStart.showAndWait();
                            apptUpdateVerify = false;
                            break;
                    }
//                    else if (updateApptStart.isAfter(existingStart.getStart().toLocalTime()) && updateApptStart.isBefore(existingStart.getEnd().toLocalTime())) {
//                        if (updateApptEnd.isBefore(existingStart.getEnd().toLocalTime()) || updateApptEnd.equals(existingStart.getEnd().toLocalTime()) || updateApptEnd.isAfter(existingStart.getEnd().toLocalTime()))
                    if ((updateApptStart.isBefore(existingStart.getStart()) || updateApptStart.isEqual(existingStart.getStart())) && (updateApptEnd.isAfter(existingStart.getEnd()) || updateApptEnd.isEqual(existingStart.getEnd()))) {
                            Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                            alertOverlapStart.setTitle("Warning!");
                            alertOverlapStart.setHeaderText("Overlapping Appointment Start/End Times");
                            alertOverlapStart.setContentText("Updated appointment begins before existing appointment ends.");
                            alertOverlapStart.showAndWait();
                            apptUpdateVerify = false;
                            break;
                    }
                }
            }
                    if(apptUpdateVerify){
                        User user = UpdateApptUserIdCombo.getValue();
                        AppointmentsDAO.updateAppt(appt.getApptTitle(), appt.getDesc(), appt.getLoc(), appt.getType(), zdtLocalStart, zdtLocalEnd, appt.getCusId(), user.getUserId(), ContactsDAO.getConIdFromName(UpdateApptContactCombo.getValue().toString()), apptIdForUpdate);
                        apptUpdated = true;
                    }
                    if(apptUpdated) {
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../resources/Appointments.fxml")));
                        stage.setScene(new Scene(scene));//error code null pointer exception?
                        stage.show();
                    }


        } catch (Exception e){
            alertMessage(1);
        }
    }
    /**Function containing alert message.
      * @param alertNumber the alert number corresponding to the alert that should be displayed. */
    public void alertMessage(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);

        switch (alertNumber) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Issue adding Appointment");
                alert.setContentText("Fields left blank and/or invalid entry detected.");
                alert.showAndWait();
                break;
        }
    }
    /**Method to initialize the page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /**Lambda Statement below used to set items for the Appointment Type combo box. Example from W3 schools followed. Lambda expression is used to concisely add values to list for use in a ComboBox.*/
        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Sales");
        types.add("Planning Session");
        types.add("De-Briefing");
        types.add("Post-Sales Follow Up");
        types.forEach((n) -> {UpdateApptTypeCombo.setItems(types);});

        LocalDate now = LocalDate.now();
        LocalTime estStartTime = LocalTime.of(8,0);
        ZoneId estZID = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = ZonedDateTime.of(now, estStartTime, estZID);
        ZoneId myZID = ZoneId.systemDefault();
        ZonedDateTime startTimeLocal = ZonedDateTime.ofInstant(estZDT.toInstant(), myZID);
        System.out.println(startTimeLocal);
        int startingHour = startTimeLocal.getHour();
        for(int i = startingHour; i < startingHour + 14; i++){
            startTimesList.add(LocalTime.of(i, 0));
        }
        for(int i = (startingHour + 1); i <= (startingHour + 14); i++){
            if(i < 24)
                endTimesList.add(LocalTime.of(i, 0));
            if(i == 24)
                endTimesList.add(LocalTime.of(0,0));
        }
        UpdateApptStartTimeCombo.setItems(startTimesList);
        UpdateApptEndTimeCombo.setItems(endTimesList);
        //how to populate the list with all contacts?

        try {
            UpdateApptContactCombo.setItems(ContactsDAO.getAllContacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            updateApptCustIdCombo.setItems(CustomersDAO.getAllCustId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            UpdateApptUserIdCombo.setItems(UsersDAO.getAllUsers());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Appointment a = AppointmentController.getAppointmentToEdit();
        UpdateApptID.setText(String.valueOf(a.getApptId()));
        UpdateApptTitle.setText(a.getApptTitle());
        UpdateApptDesc.setText(a.getDesc());
        UpdateApptLoc.setText(a.getLoc());
        UpdateApptStartDatePicker.setValue(a.getStart().toLocalDate());
        UpdateApptEndDatePicker.setValue(a.getEnd().toLocalDate());
        UpdateApptStartTimeCombo.setValue(a.getStart().toLocalTime());
        UpdateApptEndTimeCombo.setValue(a.getEnd().toLocalTime());
        UpdateApptContactCombo.setValue(a.getConName());
        UpdateApptTypeCombo.setValue(a.getType());
        updateApptCustIdCombo.setValue(a.getCusId());
        try {
            UpdateApptUserIdCombo.setValue(UsersDAO.getAllUsersById(a.getUserId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
