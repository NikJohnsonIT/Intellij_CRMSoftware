package controller;


import DAO.AppointmentsDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for page viewing existing appointments. */
public class AppointmentController implements Initializable {

    /**Appointment selected by user to edit from corresponding tableview. */
    public static Appointment apptToEdit;

    /**Tableview that contains appointments. */
    @FXML
    public TableView<Appointment> apptsTableView;

    /**Table column storing appointment ID. */
    @FXML
    public TableColumn apptsApptIdCol;

    /**Table column storing appointment title. */
    @FXML
    public TableColumn apptsTitleCol;

    /**Table column storing appointment description. */
    @FXML
    public TableColumn apptsDescCol;

    /**Table column storing appointment location. */
    @FXML
    public TableColumn apptsLocCol;

    /**Table column storing appointment contact. */
    @FXML
    public TableColumn apptsContactCol;

    /**Table column storing appointment type. */
    @FXML
    public TableColumn apptsTypeCol;

    /**Table column storing appointment start date. */
    @FXML
    public TableColumn apptsStartDTCol;

    /**Table column storing appointment end date. */
    @FXML
    public TableColumn apptsEndDTCol;

    /**Table column storing customer ID associated with appointment. */
    @FXML
    public TableColumn apptsCustIdCol;

    /**Table column storing User ID associated with appointment. */
    @FXML
    public TableColumn apptsUserIdCol;

    /**Button for adding new appointment. */
    @FXML
    public Button newApptBtn;

    /**Button for updating the currently highlighted appointment. */
    @FXML
    public Button updateApptBtn;

    /**Button used for deleting the highlighted appointment, necessary confirmation window will appear. */
    @FXML
    public Button deleteApptBtn;

    /**Radio button used for displaying all appointments. */
    @FXML
    public RadioButton allApptsRadio;

    /**Group used for functionality of radio buttons. */
    @FXML
    public ToggleGroup ApptsViewBtn;

    /**Radio button used to display appointments upcoming in the next 7 days. */
    @FXML
    public RadioButton apptsCurrWeek;

    /**Radio button used to display appointments upcoming in the next 30 days. */
    @FXML
    public RadioButton apptsCurrMonth;

    /**User-Selected appointment for making edits.
     * @return returns teh appointment for edits. */
    public static Appointment getAppointmentToEdit(){return apptToEdit;}
    Stage stage;
    Parent scene;

    /**Event handler responsible for returning the user to the main screen in the event they push the home button.
      * @param event Home button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionReturnToMain(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**Event handler responsible for loading add appointment screen.
      * @param event Add button clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionAddAppt(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/AddAppt.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**Event handler responsible for deleting selected appointment, appropriate confirmation dialogue is displayed.
      * @param event Delete button clicked.
     * @throws SQLException Error thrown if unhandled database exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionDeleteAppt(ActionEvent event) throws SQLException {
        Appointment selectedAppointment = apptsTableView.getSelectionModel().getSelectedItem();
        if (selectedAppointment == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No appointment selected!");
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Delete Appointment record: Appt. ID: "+ selectedAppointment.getApptId() + " And type: "+ selectedAppointment.getType());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                int apptIdForDelete = selectedAppointment.getApptId();
                System.out.println(apptIdForDelete);
                AppointmentsDAO.deleteAppt(apptIdForDelete);

            }

        } apptsTableView.getItems().clear();
        apptsTableView.setItems(AppointmentsDAO.getAllAppts());
    }


    /**Event handler responsible for updating the currently selected appointment.
      * If no appointment is selected relevant error message displays.
      * @param event Update button clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML
    public void onActionEditAppt(ActionEvent event) throws IOException {
        apptToEdit = apptsTableView.getSelectionModel().getSelectedItem();
        if (apptToEdit == null) {
            Alert noApptSelectedError = new Alert(Alert.AlertType.ERROR);
            noApptSelectedError.setTitle("Error");
            noApptSelectedError.setHeaderText("No appointment selected for edit.");
            noApptSelectedError.showAndWait();
        } else {
            apptToEdit = apptsTableView.getSelectionModel().getSelectedItem();
            System.out.println("entering onActionUpdateCustomer.");
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/resources/UpdateAppt.fxml"));
            Parent parent = loader.load();
            UpdateApptController controller=loader.getController();
            System.out.println("about to call apptToEdit.");


            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }


    /**Event handler responsible for displaying all appointments.
      * @param event All Appts radio button is clicked. */
    @FXML
    public void onActionDisplayAllAppts(ActionEvent event) {
        try {
            apptsTableView.setItems(AppointmentsDAO.getAllAppts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**Event handler responsible for displaying appointments upcoming in the next 30 days.
      * @param event This month radio button is clicked. */
    @FXML
    public void onActionDisplayCurrMonthAppts(ActionEvent event) {
        try {
            apptsTableView.setItems(AppointmentsDAO.getAppointmentsForNextMonth());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**Event handler responsible for displaying appointments upcoming in the next 7 days.
      * @param event This week button is clicked. */
    @FXML
    public void onActionDisplayCurrWeekAppts(ActionEvent event) {
        try {
            apptsTableView.setItems(AppointmentsDAO.getApptsForNext7Days());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**Method to intialize the page. Tableview is populated. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            apptsTableView.setItems(AppointmentsDAO.getAllAppts());
            apptsApptIdCol.setCellValueFactory(new PropertyValueFactory<>("ApptId"));
            apptsTitleCol.setCellValueFactory(new PropertyValueFactory<>("ApptTitle"));
            apptsDescCol.setCellValueFactory(new PropertyValueFactory<>("Desc"));
            apptsLocCol.setCellValueFactory(new PropertyValueFactory<>("Loc"));
            apptsContactCol.setCellValueFactory(new PropertyValueFactory<>("ConName"));
            apptsTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
            apptsStartDTCol.setCellValueFactory(new PropertyValueFactory<>("Start"));
            apptsEndDTCol.setCellValueFactory(new PropertyValueFactory<>("End"));
            apptsCustIdCol.setCellValueFactory(new PropertyValueFactory<>("CusId"));
            apptsUserIdCol.setCellValueFactory(new PropertyValueFactory<>("UserId"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
