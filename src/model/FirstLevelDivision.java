package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.ZonedDateTime;
/**@author Nick Johnson Student_ID# 001354777
 * Model class for first level division data. */
public class FirstLevelDivision {
    /**Division ID. */
    private int divId;
    /**Name of the division. */
    private String divName;
    /**Country ID. */
    private int ctryId;
    /**Country parent of division. */
    private Country ctry;
    /**List of customers. */
    private ObservableList<Customer> cusList = FXCollections.observableArrayList();

    /**Constructor for first level division.
     * @param divId division ID.
     * @param countryId country ID.
     * @param division the division. */
    public FirstLevelDivision(int divId, int countryId, String division) {
        this.divId = divId;
        this.ctryId = countryId;
        this.divName = division;
    }

    /**Getter for the division ID.
     * @return Returns the Division ID. */
    public int getDivId(){
        return divId;
    }

    /**Setter for the division ID.
     * @param divId the division ID to set. */
    public void setDivId(int divId){
        this.divId = divId;
    }

    /**Getter for the name of the division.
     * @return Returns the name of the first level division. */
    public String getDivName(){
        return divName;
    }

    /**Setter for the name of the division.
     * @param divName Name of the division to set. */
    public void setDivName(String divName){
        this.divName = divName;
    }

    /**Getter for country ID.
     * @return Returns the country ID. */
    public int getCtryId() {
        return ctryId;
    }

    /**Setter for the country ID.
     * @param ctryId the country ID to set. */
    public void setCtryId(int ctryId) {
        this.ctryId = ctryId;
    }

    /**Getter for country.
     * @return Returns the country that the first level division belongs to. */
    public Country getCtry() {
        return ctry;
    }

    /**Setter for country.
     * @param ctry the country to set. */
    public void setCtry(Country ctry) {
        this.ctry = ctry;
    }

    /**Getter for list of customers.
     * @return Returns a lsit of customers. */
    public ObservableList<Customer> getCusList() {
        return cusList;
    }

    /**Setter for the list of Customers.
     * @param cusList list of customers to set. */
    public void setCusList(ObservableList<Customer> cusList) {
        this.cusList = cusList;
    }

    @Override
    public String toString(){
        return this.divName;
    }
}
