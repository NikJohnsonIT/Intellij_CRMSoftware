package controller;

import DAO.CustomersDAO;
import DAO.UsersDAO;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Customer;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**@author Nick Johnson Student_ID# 001354777
 * Controller for page viewing current existing customers. */
public class CustomerController implements Initializable {

    /**Home button. */
    @FXML public Button onActionReturnToMain;
    /**Add customer button. */
    @FXML public Button onActionAddCustomer;
    /**Update customer button. */
    @FXML public Button onActionUpdateCustomer;
    /**Delete customer button. */
    @FXML public Button onActionDeleteCustomer;
    Stage stage;
    Parent scene;


    /**Customer selected by user for updates. */
    public static Customer customerToUpdate;
    /**Tableview containing customers. */
    @FXML public TableView<Customer> customersTableView;
    /**Table column to store customer ID. */
    @FXML public TableColumn customersCusIdCol;
    /**Table column to store customer names. */
    @FXML public TableColumn customersNameCol;
    /**Table column to store customer address. */
    @FXML public TableColumn customersAddressCol;
    /**Table column to store customer post codes. */
    @FXML public TableColumn customersPCodeCol;
    /**Table column to store customer phone numbers. */
    @FXML public TableColumn customersPhoneNumCol;
    /**Table column to store customer country data. */
    @FXML public TableColumn customersCountryCol;
    /**Table column to store customer first level division data. */
    @FXML public TableColumn customersLvl1DivCol;

    /**Event handler responsible for returning the user to the main screen.
      * @param event Home button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionReturnToMain(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/MainScreen.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**Event handler responsible for loading Add Customer screen.
      * @param event Add button is clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts.
     * @throws SQLException Error thrown if unhandled database exception, such exceptions are handled using checks and alerts. */
    @FXML public void onActionAddCustomer(ActionEvent event) throws IOException, SQLException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/AddCustomer.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        customersTableView.getItems().clear();
        customersTableView.setItems(CustomersDAO.getAllCustomers());
    }


    public static Customer getCustomerToUpdate() {return customerToUpdate;}
    /**Event handler responsible for loading Update Customer screen.
      * @param event Update button is clicked.
     *  @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts.
     *  @throws SQLException Error thrown if unhandled database exception, such exceptions are handled using checks and alerts. */
    @FXML public void onActionUpdateCustomer(ActionEvent event) throws IOException, SQLException {
        customerToUpdate = customersTableView.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/resources/UpdateCustomer.fxml"));
        loader.load();
        UpdateCusController controller=loader.getController();
        System.out.println("About to call customerToUpdate");
        controller.customerToUpdate(customerToUpdate);
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        Parent root = loader.getRoot();

        stage.setScene(new Scene(root));
        stage.show();
        customersTableView.getItems().clear();
        customersTableView.setItems(CustomersDAO.getAllCustomers());
    }

    /**Event handler responsible for deleting Customer.
      * Appointments are deleted before the customer record is deleted.
      * @param event Delete button is clicked.
      * @throws SQLException Error thrown if unhandled database exception, such exceptions are handled using checks and alerts. */
    @FXML public void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        Customer selectedCustomer = customersTableView.getSelectionModel().getSelectedItem();
        if (selectedCustomer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("No customer selected.");
            Optional<ButtonType> result = alert.showAndWait();

        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setContentText("Delete Customer record?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                CustomersDAO.deleteApptsThenCust(selectedCustomer.getCusId());
                customersTableView.getItems().clear();
                customersTableView.setItems(CustomersDAO.getAllCustomers());

            }
        }
    }


/**Method to initialize the page and ready the table. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
           customersTableView.setItems(CustomersDAO.getAllCustomers());
            customersCusIdCol.setCellValueFactory(new PropertyValueFactory<>("CusId"));//name of getter function, remove the get
            customersNameCol.setCellValueFactory(new PropertyValueFactory<>("CusName"));
            customersAddressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
            customersPCodeCol.setCellValueFactory(new PropertyValueFactory<>("PCode")); //changed get/set pCode to PCode, lowercase may have been causing problems with get/set
            customersPhoneNumCol.setCellValueFactory(new PropertyValueFactory<>("PhoneNum"));
            customersCountryCol.setCellValueFactory(new PropertyValueFactory<>("CountryId")); //changed to country ID, seems to work.
            customersLvl1DivCol.setCellValueFactory(new PropertyValueFactory<>("DivisionId"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
       }



    }

}
