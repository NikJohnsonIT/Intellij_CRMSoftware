package controller;

import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import DAO.UsersDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import model.Customer;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for the update customer form. */
public class UpdateCusController implements Initializable {


    /**Customer object selected from customer tableview, allows user to make modifications to customer records. */
    public Customer selectedCustomer;

    /**Text field for viewing Customer ID, this field is disabled for user interaction. */
    @FXML public Label updateCustomerId;
    /**Text field to update customer name. */
    @FXML public TextField updateCustomerName;
    /**Text field to update customer address. */
    @FXML public TextField updateCustomerAddress;
    /**Text field to update customer post code. */
    @FXML public TextField updateCustomerPostCode;
    /**Text field to update customer phone number. */
    @FXML public TextField updateCustomerPhoneNumber;
    /**ComboBox for updating the customer country. */
    @FXML public ComboBox<Country> updateCustomerSelectCountry;
    /**ComboBox for updating the customer state/province. */
    @FXML public ComboBox<FirstLevelDivision> updateCustomerSelectState;
    /**Event handler responsible for canceling updates to customer.
      @param event the cancel button is clicked.
     @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionCancelCustUpdate(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard updates and return to Customer window?");
        Optional<ButtonType> confirmCancellation = alert.showAndWait();
        if(confirmCancellation.isPresent() && confirmCancellation.get() == ButtonType.OK){
            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(getClass().getResource("/resources/Customers.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    Stage stage;
    Parent scene;
    /**Event handler responsible for saving updates to the customer.
      * @param event the save button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionSaveCust(ActionEvent event) throws IOException {
        try {
            boolean custUpdated = false;
            boolean custUpdateVerify = true;
            Customer cust = new Customer();
            int custId = 0;
            for (Customer customer : Scheduler.getAllCus()){
                if (customer.getCusId() > custId)
                    custId = (customer.getCusId());
                custId = ++custId;
            }
            cust.setCusName(updateCustomerName.getText());
            cust.setAddress(updateCustomerAddress.getText());
            cust.setPCode(updateCustomerPostCode.getText());
            cust.setPhoneNum(updateCustomerPhoneNumber.getText());
            cust.setLatestUpdateData(LocalDateTime.now());
            cust.setLastUpdatedBy(String.valueOf(UsersDAO.getCurrentUser()));
            cust.setDivisionID(updateCustomerSelectState.getValue().getDivId());
            int custIdForUpdate = Integer.parseInt(updateCustomerId.getText());
            for (Customer customer : CustomersDAO.getAllCust(custIdForUpdate)){
                if (updateCustomerName.getText().isEmpty() || updateCustomerAddress.getText().isEmpty() || updateCustomerPostCode.getText().isEmpty() || updateCustomerPhoneNumber.getText().isEmpty()){
                    alertMessage(1);
                    custUpdateVerify = false;
                }
            }
            if(custUpdateVerify){
                CustomersDAO.updateCust(cust.getCusName(), cust.getAddress(), cust.getPCode(), cust.getPhoneNum(), cust.getLastUpdatedBy(), cust.getDivisionId(), custIdForUpdate);
                custUpdated = true;
            }
            if(custUpdated){
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../resources/Customers.fxml")));
                stage.setScene(new Scene(scene));//error code null pointer exception?
                stage.show();
            }

        } catch (Exception e){
            alertMessage(1);
        }

    }
    /**Function containing alert messages to display.
      * @param alertNumber the appropriate alert to display. */
    public void alertMessage(int alertNumber) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        Alert alertInfo = new Alert(Alert.AlertType.INFORMATION);
        switch (alertNumber) {
            case 1:
                alert.setTitle("Error");
                alert.setHeaderText("Issue adding/updating Customer");
                alert.setContentText("Fields left blank and/or invalid entry detected.");
                alert.showAndWait();
                break;
        }
    }
    /**Method for retrieving the customer record for updates.
     * @param customerUpdate customer object for updates. */
    public void customerToUpdate(Customer customerUpdate){
        try {
            updateCustomerSelectCountry.setItems(CountriesDAO.getAllCountries());
            updateCustomerId.setText(String.valueOf(customerUpdate.getCusId()));
            updateCustomerName.setText(customerUpdate.getCusName());
            updateCustomerAddress.setText(customerUpdate.getAddress());
            updateCustomerPostCode.setText(customerUpdate.getPCode());
            updateCustomerPhoneNumber.setText(customerUpdate.getPhoneNum());
            updateCustomerSelectCountry.setValue(CountriesDAO.getCountry(customerUpdate.getCountryId()));
            updateCustomerSelectState.setItems(DivisionsDAO.getSingleCountryDivision(customerUpdate.getCountryId()));
            updateCustomerSelectState.setValue(DivisionsDAO.getDivisionById(customerUpdate.getDivisionId()));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    /**Method to intialize page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**Event handler responsible for getting user input on customer country.
      * This will limit the selections available for the state/province ComboBox.
      * @param actionEvent a country is selected.*/
    public void onActionSelectCountry(ActionEvent actionEvent) {
        Country country = updateCustomerSelectCountry.getValue();
        try {
            updateCustomerSelectState.setItems(DivisionsDAO.getSingleCountryDivision(country.getCtryId()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
