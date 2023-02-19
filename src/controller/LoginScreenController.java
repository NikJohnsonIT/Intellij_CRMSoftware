package controller;

import DAO.UsersDAO;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;
import utility.InfoLog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

/**@author Nick Johnson Student_ID# 001354777
 * Controller class for Login form. */
public class LoginScreenController implements Initializable {
    Stage stage;
    Parent scene;
    /**Label for displaying user Location. */
    @FXML private Label loginLocaleLabel;
    /**Text field for entering Username. */
    @FXML private TextField loginUsernameTxt;
    /**Text field for entering Password. */
    @FXML private TextField loginPwTxt;
    /**Label for indicating where user location is displayed. */
    @FXML private Label loginLocationLabel;
    /**Label indicating where a user should enter a Username. */
    @FXML private Label loginUsernameLabel;
    /**Label indicating where a user should enter a Password. */
    @FXML private Label loginPasswordLabel;

    @FXML private Button loginCancel;

    ResourceBundle rb;



    /**Login button. */
    @FXML private Button loginLoginBtn;

    /**Event handler responsible for attempting to log a user in with provided credentials.
      * Necessary checks are performed.
      * Page is translated to French when necessary.
      * Login attempts are recorded.
      * @param event Login button clicked. */
    @FXML void onActionLoginButton(ActionEvent event) throws IOException, SQLException {
        String currentUsername = loginUsernameTxt.getText();
        String currentPassword = loginPwTxt.getText();
        String alertMessageToTranslate = "Incorrect Username or Password!";
        if(Locale.getDefault().equals(Locale.FRENCH)){
            alertMessageToTranslate = "Nom d'utilisateur ou mot de passe incorrect!";
        }

        ObservableList<User> usersList = UsersDAO.getAllUsers(currentUsername, currentPassword);
        if (usersList.size() > 0) {
            activityLog(currentUsername, true);
            System.out.println("Matching User");

            stage = (Stage)((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/resources/MainScreen.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{
            activityLog(currentUsername, false);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(alertMessageToTranslate);
            Optional<ButtonType> result = alert.showAndWait();
        }
    }

    /**Cancel button. This will close the program. */
    @FXML private Button loginCancelBtn;

    /**Event handler responsible for closing the program if a User clicks the cancel button.
      * @param event the cancel button is clicked. */
    @FXML void onActionCancelButton(ActionEvent event) throws IOException{
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("WARNING!");
        alert.setContentText("This will close the program! Do you wish to close the program?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    /**Function responsible for setting label text to the location of the user's machine. */
    public void setLoginLocationLabel() {
        TimeZone tz = TimeZone.getDefault();
        Locale currLocale = new Locale(Locale.getDefault().getLanguage(),Locale.getDefault().getDisplayCountry());
        String display = tz.getDisplayName(currLocale);
        loginLocaleLabel.setText(display);
    }

    /**
     * Function responsible for tracking login attempts. The information is written to login_activity.txt.
      * @param success Records whether a login attempt succeeded or failed.
      * @param userName Records the username used with the associated login attempt.
      * Lambda expressions are utilised here to more efficiently access variables and DB information.
      * A key benefit of Lambda expressions is the reduction in lines of code. The Lambda expression used here is justified by this reduction in code.
      * The activity log is updated and the required information is efficiently gathered using fewer lines of code.
      */
    public void activityLog(String userName, boolean success){
        try{
            String activityFile = "login_activity.txt";
            File file = new File(activityFile);
            FileWriter fw = new FileWriter(file,true);
            PrintWriter results = new PrintWriter(fw);
            LocalDateTime loginAttemptDT = LocalDateTime.now();

            if (success) {
                InfoLog infoToLog = ((uName, t) -> "User: "+ uName + "Login PASS at: "+ t);
                results.println(infoToLog.getInfo(userName, Timestamp.valueOf(loginAttemptDT)));
                results.close();

            } else {
                InfoLog infoToLog = ((uName, t) -> "User: "+ uName + "Login FAIL at: "+ t);
                results.println(infoToLog.getInfo(userName, Timestamp.valueOf(loginAttemptDT)));
                results.close();
            }
            System.out.println("Text file created");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    /**Alert message and translation if a user enters incorrect credentials.
      * @param alertMsgToTranslate the message to be translated. */
    public void alertMessage(String alertMsgToTranslate) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention!");
        alert.setContentText(alertMsgToTranslate);
    }

    /**Method to initialize the page. Locale is checked and form translated as necessary. */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Locale.setDefault(Locale.FRENCH);
        setLoginLocationLabel();
       Locale french = new Locale("fr");
        rb = ResourceBundle.getBundle("main/Lang_fr", Locale.getDefault());
      if(Locale.getDefault().equals(Locale.FRENCH)){
            loginLocationLabel.setText(rb.getString("userLoc"));
            loginUsernameLabel.setText(rb.getString("userName"));
            loginPasswordLabel.setText(rb.getString("password"));
            loginLoginBtn.setText(rb.getString("login"));
            loginCancel.setText(rb.getString("cancel"));


        }






    }



}
