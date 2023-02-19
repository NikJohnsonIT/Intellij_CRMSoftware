package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**@author Nick Johnson Student_ID# 001354777
 * Model Class for customer. */
public class Customer {

    /**Customer ID. */
    private int cusId;
    /**Customer name. */
    private String cusName;
    /**Customer address. */
    private String address;
    /**Customer post code. */
    private String pCode;
    /**Customer phone number. */
    private String phoneNum;
    /**Division ID for customer. */
    private int divisionId;
    /**Customer division name information. */
    private String division;
    /**Customer country. */
    private int countryId;
    /**List of appointments. */
    private ObservableList<Appointment> appts = FXCollections.observableArrayList();
    /**Date the customer record was created. */
    private LocalDateTime createDate;
    /**Who created the customer record. */
    private String createdBy;
    /**When the last update was made to the record. */
    private LocalDateTime latestUpdateData;
    /**Who made the most recent updates to the record. */
    private String lastUpdatedBy;


    /**Customer constructor.
     * @param cusId Customer ID. */
    public Customer(int cusId){
        this.cusId = cusId;
    }

    public Customer(){}

    /**Customer constructor.
     * @param cusId Customer ID.
     * @param cusName Customer name.
     * @param address Customer address.
     * @param pCode Customer postal code.
     * @param phoneNum Customer phone number.
     * @param createDate When the customer record was created.
     * @param createdBy Who created the customer record.
     * @param latestUpdateData When the most recent update to the record was made.
     * @param lastUpdatedBy User who most recently updated the record.
     * @param divisionId Customer division ID.
     * @param countryId Customer Country ID. */
    public Customer(int cusId, String cusName, String address, String pCode, String phoneNum, LocalDateTime createDate, String createdBy, LocalDateTime latestUpdateData, String lastUpdatedBy, int divisionId, int countryId){
        this.cusId = cusId;
        this.cusName = cusName;
        this.address = address;
        this.pCode = pCode;
        this.phoneNum = phoneNum;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.latestUpdateData = latestUpdateData;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId= divisionId;
        this.countryId = countryId;
    }

    /**Customer constructor.
     * @param custId Customer ID.
     * @param custName Customer name.
     * @param address Customer address.
     * @param pCode Customer postal code.
     * @param phoneNum Customer phone number.
     * @param createDate When the customer record was created.
     * @param createdBy Who created the customer record.
     * @param latestUpdateData When the most recent update to the record was made.
     * @param lastUpdatedBy User who most recently updated the record.
     * @param divId Customer division ID. */
    public Customer(int custId, String custName, String address, String pCode, String phoneNum, LocalDateTime createDate, String createdBy, LocalDateTime latestUpdateData, String lastUpdatedBy, int divId) {
        this.cusId = custId;
        this.cusName = custName;
        this.address = address;
        this.pCode = pCode;
        this.phoneNum = phoneNum;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.latestUpdateData = latestUpdateData;
        this.lastUpdatedBy = lastUpdatedBy;
        this.divisionId= divId;
    }

    /**Getter for when the record was created.
     * @return returns the date the record was created. */
    public LocalDateTime getCreateDate(){return createDate;}

    /**Setter for the create date of the record.
     * @param createDate the date to set. */
    public void setCreateDate(LocalDateTime createDate){this.createDate = createDate;}

    /**Getter for who created the record.
     * @return returns who created the record. */
    public String getCreatedBy(){return createdBy;}

    /**Setter for who created the record.
     * @param createdBy who created the record. */
    public void setCreatedBy(String createdBy){this.createdBy = createdBy;}

    /**Getter for when the record was last updated.
     * @return the date of the most recent update. */
    public LocalDateTime getLatestUpdateData(){return latestUpdateData;}

    /**Setter for when the record was most recently updated.
     * @param latestUpdateData when the record was most recently updated. */
    public void setLatestUpdateData(LocalDateTime latestUpdateData){this.latestUpdateData = latestUpdateData;}

    /**Getter for who most recently updated the record.
     * @return who most recently updated the record. */
    public String getLastUpdatedBy(){return lastUpdatedBy;}

    /**Setter for who most recently updated the record.
     * @param lastUpdatedBy who most recently updated the record. */
    public void setLastUpdatedBy(String lastUpdatedBy){this.lastUpdatedBy = lastUpdatedBy;}

    /**Getter for customer ID.
     * @return Returns the customer ID. */
    public int getCusId(){
        return cusId;
    }

    /**Setter for customer ID.
     * @param cusId Customer ID to set. */
    public void setCusId(int cusId){
        this.cusId = cusId;
    }

    /**Getter for customer name.
     * @return Returns the name of the customer. */
    public String getCusName(){
        return cusName;
    }

    /**Setter for customer name.
     * @param cusName Name of customer to set. */
    public void setCusName(String cusName){
        this.cusName = cusName;
    }

    /**Getter for customer address.
     * @return Returns the customers address. */
    public String getAddress(){
        return address;
    }

    /**Setter for customer address.
     * @param address Customer address to set. */
    public void setAddress(String address){
        this.address = address;
    }

    /**Getter for customer post code.
     * @return Returns the customer post code. */
    public String getPCode(){
        return pCode;
    }

    /**Setter for customer post code.
     * @param pCode Customer post code to set. */
    public void setPCode(String pCode){
        this.pCode = pCode;
    }

    /**Getter for customer phone number.
     * @return Returns the customer Phone number. */
    public String getPhoneNum(){
        return phoneNum;
    }

    /**Setter for customer phone number.
     * @param phoneNum Customer phone number to set. */
    public void setPhoneNum(String phoneNum){
        this.phoneNum = phoneNum;
    }


    /**Getter for division information.
     * @return Returns the first level division data for the customer. */
    public String getDivision(){ return division; }
    /**Setter for division information.
     * @param division Division data to set. */
    public void setDivision(String division) { this.division = division; }

    /**Getter for list of appointments.
     * @return Returns a list of appointments for the customer. */
    public ObservableList<Appointment> getAppts(){
        return appts;
    }

    /**Setter for list of appointments.
     * @param appts the list of appts to set. */
    public void setAppts(ObservableList<Appointment> appts) {
        this.appts = appts;
    }

    /**Getter for country ID.
     * @return Returns the customers Country ID. */
    public int getCountryId() {return countryId;}
    /**Getter for division ID.
     * @return Returns the customers first level division ID. */
    public int getDivisionId() {return divisionId;}

    /**Setter for the Division ID.
     * @param divisionId Division ID to set. */
    public void setDivisionID(int divisionId){this.divisionId = divisionId;}

    @Override
    public String toString(){
        return this.cusName;
    }
}
