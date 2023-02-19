package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**@author Nick Johnson Student_ID# 001354777
 * Model class for Scheduler model. */
public class Scheduler {
    /**List of countries. */
    private static ObservableList<Country> allCtry = FXCollections.observableArrayList();
    /**List of first level divisions. */
    private static ObservableList<FirstLevelDivision> allFirstLevelDivision = FXCollections.observableArrayList();
    /**List of all customers. */
    private static ObservableList<Customer> allCus = FXCollections.observableArrayList();
    /**List of all users. */
    private static ObservableList<User> allUser = FXCollections.observableArrayList();
    /**List of all contacts. */
    private static ObservableList<Contact> allCon = FXCollections.observableArrayList();
    /**List of all appointments. */
    private static ObservableList<Appointment> allAppts = FXCollections.observableArrayList();
    /**The current user. */
    private static User currentUser;

    /**Getter for list of all countries.
     * @return Return a list of all countries. */
    public static ObservableList<Country> getAllCtry(){
        return allCtry;
    }

    /**Setter for list of all countries.
     * @param allCtry List of all countries. */
    public static void setAllCtry(ObservableList<Country> allCtry){
        Scheduler.allCtry = allCtry;
    }

    /**Function to set up list of countries and appropriately sort forst level divisions. */
    public static void setupCtrys(){
        for (Country ctry :
                allCtry) {
            for (FirstLevelDivision div :
                    allFirstLevelDivision) {
                if (div.getCtryId() == ctry.getCtryId() && !ctry.getLvl1Div().contains(div)) {
                    ctry.getLvl1Div().add(div);
                    div.setCtry(ctry);
                }
            }
        }
    }

    /**Getter for list of all first level divisions.
     * @return Returns a list of all first level divisions. */
    public static ObservableList<FirstLevelDivision> getAllLvl1Div(){
        return allFirstLevelDivision;
    }

    /**Setter for all first level divisions.
     * @param allFirstLevelDivision All first level divisions to include. */
    public static void setAllLvl1Div(ObservableList<FirstLevelDivision> allFirstLevelDivision){
        Scheduler.allFirstLevelDivision = allFirstLevelDivision;
    }

    /**Getter for list of all customers.
     * @return Returns a list of all customers. */
    public static ObservableList<Customer> getAllCus(){
        return allCus;
    }

    /**Setter for list of all Customers.
     * @param allCus list of all customers. */
    public static void setAllCus(ObservableList<Customer> allCus){
        Scheduler.allCus = allCus;
    }

    /**Getter for list of all users.
     * @return Returns a list of all users. */
    public static ObservableList<User> getAllUser(){
        return allUser;
    }

    /**Setter for list of all users.
     * @param allUser list of all users. */
    public static void setAllUser(ObservableList<User> allUser){
        Scheduler.allUser = allUser;
    }

    /**Getter for list of all contacts.
     * @return Returns a list of all contacts. */
    public static ObservableList<Contact> getAllCon(){
        return allCon;
    }

    /**Setter for list of all contacts.
     * @param allCon list of all contacts. */
    public static void setAllCon(ObservableList<Contact> allCon){
        Scheduler.allCon = allCon;
    }

    /**Getter for list of all appointments.
     * @return Returns a list of all appointments. */
    public static ObservableList<Appointment> getAllAppts(){
        return allAppts;
    }

    /**Setter for all appointments.
     * @param allAppts list of all appointments. */
    public static void setAllAppts(ObservableList<Appointment> allAppts){
        Scheduler.allAppts = allAppts;
    }

    /**Getter for obtaining the currently active user.
     * @return Returns a the currently logged in user. */
    public static User getCurrentUser(){
        return currentUser;
    }

    /**Setter for assigning the current user.
     * @param currentUser the user to be assigned. */
    public static void setCurrentUser(User currentUser){
        Scheduler.currentUser = currentUser;
    }
}
