package controller;

import DAO.CountriesDAO;
import DAO.CustomersDAO;
import DAO.DivisionsDAO;
import DAO.UsersDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.FirstLevelDivision;
import model.Scheduler;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for adding new customer records. */
public class AddCusController implements Initializable {

    /**Text field for holding Customer Id. Customer ID are disabled and automatically incremented. */
    @FXML public Label AddCustomerId;

    /**Text field for entering customer name. */
    @FXML public TextField AddCustName;

    /**Text field for entering customer street address. */
    @FXML public TextField AddCustAddress;

    /**Text field for entering customer post code. */
    @FXML public TextField AddCustPCode;

    /**Text field for entering customer phone number. */
    @FXML public TextField AddCustPhoneNum;

    /**Combo box containing country options for the user to select. */
    @FXML public ComboBox<Country> AddCustCountryCombo;

    //What to replace <?> with?
    /**Combo box containing state/province information to select. */
    @FXML public ComboBox<FirstLevelDivision> AddCustStateCombo;

    /**Event handler for when a user presses the cancel button. A Confirmation dialogue window appears with a relevant message.
       * @param event Cancel button is clicked.
      @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionCancelCustAddition(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Discard Customer and return to Customer window?");
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

    /**Event handler for when a user clicks the save button. If missing information is detected a relevant error message is displayed.
       * @param event save button clicked.
     * @throws IOException Error thrown if unhandled data input/output exception, such exceptions are handled using data checks and alerts. */
    @FXML public void onActionSaveCust(ActionEvent event) throws IOException {
        try {
            boolean custAdded = false;
            boolean custValidation = true;
            Customer cust = new Customer();

            cust.setCusName(AddCustName.getText());
            cust.setAddress(AddCustAddress.getText());
            cust.setPCode(AddCustPCode.getText());
            cust.setPhoneNum(AddCustPhoneNum.getText());
            cust.setCreateDate(LocalDateTime.now());
            cust.setCreatedBy(String.valueOf(UsersDAO.getCurrentUser()));
            cust.setLatestUpdateData(LocalDateTime.now());
            cust.setLastUpdatedBy(String.valueOf(UsersDAO.getCurrentUser()));

            if (AddCustName.getText().isEmpty() || AddCustAddress.getText().isEmpty() || AddCustPCode.getText().isEmpty() || AddCustPhoneNum.getText().isEmpty() || AddCustCountryCombo.getValue()==null || AddCustStateCombo.getValue()==null){
                alertMessage(1);
                custValidation = false;
                return;
            }
            cust.setDivisionID(AddCustStateCombo.getValue().getDivId());
                CustomersDAO.createNewCust(cust.getCusName(), cust.getAddress(), cust.getPCode(), cust.getPhoneNum(), cust.getDivisionId());

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("../resources/Customers.fxml")));
                stage.setScene(new Scene(scene));//error code null pointer exception?
                stage.show();

        } catch (SQLException e){
            e.printStackTrace();
            alertMessage(1);
        }


    }

    /**Method containing error message that triggers when a field is left empty on the form. The current iteration requires only a single message.
     * @param alertNumber integer value used to assign varying alert messages, current version contains a single message but allows easy expansion. */
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
/**Method to initialize page. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int custId = 0;
        try {
            for (Customer customer : CustomersDAO.getAllCustomers()){
                if (customer.getCusId() > custId)
                    custId = (customer.getCusId());
                custId = ++custId;}
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        AddCustomerId.setText(String.valueOf(custId));

        //typically display message to specify what the issue is. "Problem adding..." alert message similar to errors earlier.
        try {
            AddCustCountryCombo.setItems(CountriesDAO.getAllCountries());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    /**Method to update first level divisions combo box when a country is selected.
     * @param actionEvent The action of a user selecting a different country. */
    public void onCountryComboChange(ActionEvent actionEvent) {
        int countryId = AddCustCountryCombo.getValue().getCtryId();
        try {
            AddCustStateCombo.setItems(DivisionsDAO.getSingleCountryDivision(countryId));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
