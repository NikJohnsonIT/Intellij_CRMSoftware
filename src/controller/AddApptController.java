package controller;
import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import DAO.UsersDAO;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.net.URL;
import java.sql.SQLException;
import java.time.chrono.ChronoLocalDateTime;
import java.util.*;
import java.time.*;
import java.io.IOException;

/**@author Nick Johnson Student_ID# 001354777
 * The controller class for adding new appointments. */
public class AddApptController implements Initializable{


    /**The text field used to store appointment IDs, which are auto-incremented. */
    @FXML public Label AddApptId;

    /**Text field to store appointment contact. */
    @FXML public ComboBox<Contact> AddApptContactCombo;

    /**Date picker for the start date of an appointment. */
    @FXML public DatePicker AddApptStartDatePicker;

    /**Date picker for the end date of an appointment. */
    @FXML public DatePicker AddApptEndDatePicker;

    /**Text field allowing the user to manually enter appointment type. */
    @FXML public ComboBox AddApptTypeCombo;

    /**Combo box containing start times for appointments. These are automatically converted to the user's local time. */
    @FXML public ComboBox<LocalTime> AddApptStartTimeCombo;

    /**Combo box containing end times for appointments. These are automatically converted to the user's local time. */
    @FXML public ComboBox<LocalTime> AddApptEndTimeCombo;

    /**Text field allowing the user to manually enter a customer ID to associate with the appointment. */
    @FXML public TextField AddApptCustIdText;

    /**Label that displays the current active user's ID. */
    @FXML public Label AddApptUserIdTxt;

    /**Text field for holding the title of the appointment. */
    @FXML public TextField AddApptTitle;

    /**Text field for a brief text description of the appointment. */
    @FXML public TextField AddApptDesc;

    /**Text field for holding the location of the appointment. */
    @FXML public TextField AddApptLoc;


    /** Action Event that handles saving new appointments.
     * An appointment ID is assigned to new appointments and auto-incremented.
     * Relevant checks are performed to ensure no appointments overlap with relevant error messages displaying for exceptions.
     * @param event is the action of pressing the save button.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionSaveAppt(ActionEvent event) throws IOException {
        try {
            //boolean value indicates whether to return to appointments.fxml or remain on current page.
            boolean apptAdded = false;
            boolean apptValidation = true;
            System.out.println("saving appt");
            Appointment appt = new Appointment();
            appt.setTitle(AddApptTitle.getText());
            appt.setDesc(AddApptDesc.getText());
            appt.setLoc(AddApptLoc.getText());
            appt.setType(String.valueOf(AddApptTypeCombo.getValue()));
            LocalDateTime start = LocalDateTime.of(AddApptStartDatePicker.getValue(), AddApptStartTimeCombo.getValue());
            appt.setStart(start);
            LocalDateTime end = LocalDateTime.of(AddApptEndDatePicker.getValue(), AddApptEndTimeCombo.getValue());
            appt.setEnd(end);
            appt.setCusId(Integer.parseInt(AddApptCustIdText.getText()));
            appt.setUserId(Integer.parseInt(AddApptUserIdTxt.getText()));
            appt.setConName(AddApptContactCombo.getValue().getConName());

            ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());

            ZonedDateTime zdtLocalStart = ZonedDateTime.of(start,localZoneId);
            ZonedDateTime zdtOther = zdtLocalStart.withZoneSameInstant(ZoneId.of("US/Eastern"));
            Instant gmtInstant = zdtLocalStart.toInstant();
            ZonedDateTime zdtLocalEnd = ZonedDateTime.of(end,localZoneId);

            int conID = AddApptContactCombo.getValue().getConId();

                LocalDateTime addApptStart = appt.getStart();
                LocalDateTime addApptEnd = appt.getEnd();
                for (Appointment existingStart : AppointmentsDAO.getAllAppts()) {
//                    if (addApptStart.isBefore(existingStart.getStart())) {
//                        if (addApptEnd.equals(existingStart.getEnd()) || addApptEnd.isAfter(existingStart.getEnd())) {
                    if ((addApptStart.isAfter(existingStart.getStart()) || addApptStart.isEqual(existingStart.getStart())) && addApptStart.isBefore(existingStart.getEnd())) {
                        Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                        alertOverlapStart.setTitle("Warning!");
                        alertOverlapStart.setHeaderText("Overlapping Appointment Start Times");
                        alertOverlapStart.setContentText("New appointment interferes with existing appointment start time.");
                        alertOverlapStart.showAndWait();
                        apptValidation = false;
                    }
//                     else if (addApptStart.equals(existingStart.getStart())) {
//                        if (addApptEnd.isBefore(existingStart.getEnd()) || addApptEnd.equals(existingStart.getEnd().toLocalTime()) || addApptEnd.isAfter(existingStart.getEnd()))
                    if ((addApptStart.isAfter(existingStart.getStart())) && (addApptEnd.isBefore(existingStart.getEnd()) || addApptEnd.isEqual(existingStart.getEnd()))) {
                        Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                        alertOverlapStart.setTitle("Warning!");
                        alertOverlapStart.setHeaderText("Overlapping Appointment Times");
                        alertOverlapStart.setContentText("New appointment begins at the same time as another appointment.");
                        alertOverlapStart.showAndWait();
                        apptValidation = false;
                    }

//                else if (addApptStart.isAfter(existingStart.getStart()) && addApptStart.isBefore(existingStart.getEnd())) {
//                        if (addApptEnd.isBefore(existingStart.getEnd()) || addApptEnd.equals(existingStart.getEnd().toLocalTime()) || addApptEnd.isAfter(existingStart.getEnd()))
                    if ((addApptStart.isBefore(existingStart.getStart()) || addApptStart.isEqual(existingStart.getStart())) && (addApptEnd.isAfter(existingStart.getEnd()) || addApptEnd.isEqual(existingStart.getEnd()))) {
                        Alert alertOverlapStart = new Alert(Alert.AlertType.WARNING);
                        alertOverlapStart.setTitle("Warning!");
                        alertOverlapStart.setHeaderText("Overlapping Appointment Start/End Times");
                        alertOverlapStart.setContentText("New appointment begins before existing appointment ends.");
                        alertOverlapStart.showAndWait();
                        apptValidation = false;
                    }
                 if(AddApptTitle.getText().isEmpty() || AddApptDesc.getText().isEmpty() || AddApptLoc.getText().isEmpty() || AddApptContactCombo.getSelectionModel().isEmpty() || AddApptTypeCombo.getSelectionModel().isEmpty() ||
                            AddApptStartDatePicker.getValue() == null || AddApptStartDatePicker.getValue().toString().isEmpty() || AddApptStartTimeCombo.getSelectionModel().isEmpty() || AddApptEndDatePicker.getValue().toString().isEmpty() || AddApptEndDatePicker.getValue() == null || AddApptEndTimeCombo.getSelectionModel().isEmpty() ||
                            AddApptCustIdText.getText().isEmpty()) {
                        alertMessage(1);
                        apptValidation = false;
                    }
                }
        if (apptValidation){
                        AppointmentsDAO.createAppt(appt.getApptTitle(), appt.getDesc(), appt.getLoc(), appt.getType(), zdtLocalStart, zdtLocalEnd, appt.getCusId(), appt.getUserId(), conID);
                        apptAdded = true;
                    }
                    if(apptAdded){
                        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../resources/Appointments.fxml")));
                        stage.setScene(new Scene(scene));
                        stage.show();
                    }
            } catch (SQLException throwables) {
            alertMessage(1);
        }
        catch (NullPointerException e){
            alertMessage(1);
        }
    }



    /** Action Event that handles cancelling new appointment addition.
      @param event is the action of pressing the cancel button.
      @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionCancelApptAddition(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard appointment and return to Appointments window?");
        Optional<ButtonType> confirmCancellation = alert.showAndWait();
        if(confirmCancellation.isPresent() && confirmCancellation.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/resources/Appointments.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    //Use loops to populate list
    ObservableList<LocalTime> startTimesList = FXCollections.observableArrayList();
    ObservableList<LocalTime> endTimeList = FXCollections.observableArrayList();


    Stage stage;
    Parent scene;

    public void populateCustomer(){

    }

    /** Method containing an alert message informing the user of missing information.
       @param alertNumber currently only a single alert number and message are used. */
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

    @Override
    /** Method that initializes the page. All relevant conversions take place during initialization.
     * <p>Lambda statement is used to concisely set items for the Type combo box.
     *      * One of the main benefits of Lambda expressions is reducing the number of lines of code necessary to accomplish a task, the use of the lambda expression below is justified by this reduction in lines of code.</p>
     *  */
    public void initialize(URL url, ResourceBundle resourceBundle) {

        User u = new User();
        System.out.println(UsersDAO.currentUser);
        AddApptUserIdTxt.setText(String.valueOf(UsersDAO.currentUserId));

        int apptId = 0;
        try {
            ObservableList<Appointment> allAppts = AppointmentsDAO.getAllAppts();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        try {
            for (Appointment appointment : AppointmentsDAO.getAllAppts()){
                if (appointment.getApptId() > apptId)
                    apptId = (appointment.getApptId());
                 ++apptId;

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        AddApptId.setText(String.valueOf(apptId));

        ObservableList<String> types = FXCollections.observableArrayList();
        types.add("Sales");
        types.add("Planning Session");
        types.add("De-Briefing");
        types.add("Post-Sales Follow Up");
        types.forEach((n) -> {AddApptTypeCombo.setItems(types);});


        //ComboBox choices need to be limited to UTC12:00-02:00 displayed in users local time. how to apply offset to comboBox options?
        AddApptStartTimeCombo.setItems(startTimesList);
        //this is going to make 23 hour slots for start times, end times go through midnight.
        LocalDate now = LocalDate.now();
        LocalTime estStartTime = LocalTime.of(8,0);
        ZoneId estZID = ZoneId.of("America/New_York");
        ZonedDateTime estZDT = ZonedDateTime.of(now, estStartTime, estZID);
        //Convert to user
        ZoneId myZID = ZoneId.systemDefault();
        //conversion
        ZonedDateTime startTimeLocal = ZonedDateTime.ofInstant(estZDT.toInstant(), myZID);
        System.out.println(startTimeLocal);

        int startingHour = startTimeLocal.getHour();
        for(int i = startingHour; i < startingHour + 14; i++){
            startTimesList.add(LocalTime.of(i, 0));
        }
        try {
            AddApptContactCombo.setItems(ContactsDAO.getAllContacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();}
        AddApptEndTimeCombo.setItems(endTimeList);
        for(int i = (startingHour + 1); i <= (startingHour + 14); i++){
            if(i < 24)
                endTimeList.add(LocalTime.of(i, 0));
            if(i == 24)
                endTimeList.add(LocalTime.of(0,0));
        }

    }
}


