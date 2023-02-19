package controller;

import DAO.AppointmentsDAO;
import DAO.ContactsDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import utility.DBConnection;
import utility.ListManager;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for the reports form. */
public class ReportsController implements Initializable {



    /**Label for displaying the total number of appointments matching the selected criteria. */
    @FXML
    public Label reportsTotalApptsLabel;
    /**ComboBox for selecting the Contact. */
    @FXML
    public ComboBox<Contact> reportsContactComboBox;
    /**TableView for displaying the appointments associated with the selected Contact. */
    @FXML
    public TableView reportsApptsByContactTableView;
    /**Column for storing appointment IDs. */
    @FXML public TableColumn reportsApptIdCol;
    /**Column for storing appointment titles. */
    @FXML public TableColumn reportsTitleCol;
    /**Column for storing appointment type. */
    @FXML public TableColumn reportsTypeCol;
    /**Column for storing appointment description. */
    @FXML public TableColumn reportsDescCol;
    /**Column for storing appointment start date. */
    @FXML public TableColumn reportsStartDateCol;
    /**Column for storing appointment start time. */
    @FXML public TableColumn reportsStartTimeCol;
    /**Column for storing appointment end date. */
    @FXML public TableColumn reportsEndDateCol;
    /**Column for storing appointment end time. */
    @FXML public TableColumn reportsEndTimeCol;
    /**Column for storing appointment customer ID. */
    @FXML public TableColumn reportsCustIdCol;
    /**Label for displaying the total number of customers. */
    @FXML
    public Label reportsTotalCustomersLabel;
    /**TableView for displaying total number of customer appointments by month and type. */
    @FXML public  TableView reportsApptsByMonthTypeTblView;
    /**Column for displaying the different appointment types. */
    @FXML public TableColumn reportsApptTypeColB;
    /**Column for displaying the total number of appointments for January that match each of the 4 (current version) appointment types. */
    @FXML public TableColumn reportsCountOfApptsCol;
    /**Column for displaying the total number of appointments for February that match each of the 4 (current version) appointment types. */
    @FXML public TableColumn reportsMonthCol;


    /**Function that sets the label text to the total number of customers.
     * @throws SQLException when total customers is null. */
    public void setReportsTotalCustomersLabel() throws SQLException {
        reportsTotalCustomersLabel.setText(String.valueOf(AppointmentsDAO.totalCustomersLabelTxt()));
    }
    Stage stage;
    Parent scene;

    /**Event handler responsible for returning the user to the main screen.
      * @param event the home button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionReturnToMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    /**Event Handler responsible for updating the appointments by contact tableview. Contents of the table update based off user selection of contact.
      * @param actionEvent user selects a contact using the combo box.
      * @throws SQLException when contact value is null. */
    @FXML public void contactComboAction(javafx.event.ActionEvent actionEvent) throws SQLException {
        System.out.println(reportsContactComboBox.getValue().getConId());
        reportsApptsByContactTableView.setItems(AppointmentsDAO.getApptsByCon(reportsContactComboBox.getValue()));
        reportsApptIdCol.setCellValueFactory(new PropertyValueFactory<>("ApptId"));
        reportsTitleCol.setCellValueFactory(new PropertyValueFactory<>("ApptTitle"));
        reportsDescCol.setCellValueFactory(new PropertyValueFactory<>("Desc"));
        reportsTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        reportsCustIdCol.setCellValueFactory(new PropertyValueFactory<>("CusId"));
        reportsStartDateCol.setCellValueFactory(new PropertyValueFactory<>("StartDate"));
        reportsStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("StartTime"));
        reportsEndDateCol.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
        reportsEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("EndTime"));
    }
    /**Method to initialize the page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        final ObservableList<Appointment> apptsMonthTypeTblData = FXCollections.observableArrayList(
                new Appointment()
        );
        ObservableList<Appointment> listB = FXCollections.observableArrayList();
        try {
            listB = AppointmentsDAO.totalApptsByMonthAndType();
            System.out.println(listB.get(1).getType());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        reportsApptsByMonthTypeTblView.setItems(listB);
        reportsApptTypeColB.setCellValueFactory(new PropertyValueFactory<>("Type"));
        reportsCountOfApptsCol.setCellValueFactory(new PropertyValueFactory<>("CountOfApptsByType"));
        reportsMonthCol.setCellValueFactory(new PropertyValueFactory<>("MonthStart"));
        try {
            reportsTotalCustomersLabel.setText(String.valueOf(AppointmentsDAO.totalCustomersLabelTxt()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
     reportsContactComboBox.setItems(ContactsDAO.allContacts);
    }

}
