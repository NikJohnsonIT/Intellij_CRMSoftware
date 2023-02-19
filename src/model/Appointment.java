package model;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**@author Nick Johnson Student_ID# 001354777
 * Model class for Appointment. */
public class Appointment {

    private int monthStart;
    /**Appointment ID. */
    private int apptId;

    private int totalAppts;

    private String month;

    private int countOfApptsByType;



    /**Title of appointment. */
    private String apptTitle;
    /**Description of appointment. */
    private String desc;
    /**Location of appointment. */
    private String loc;
    /**Contact ID of appointment. */
    private int conId;
    /**Contact name associated with appointment. */
    private String conName;
    /**Type of appointment. */
    private String type;
    /**Start date and time of appointment. */
    private LocalDateTime start;
    /**End date and time of appointment.*/
    private LocalDateTime end;
    //below not necessary. delete if possible.
    /**Date the appointment was created. */
    private LocalDateTime createDate;
    /**Who created the appointment. */
    private String createdBy;
    /**When the most recent update to the appointment was made. */
    private LocalDateTime mostRecentUpdate;
    /**Who most recently updated the appointment. */
    private String mostRecentUpdateBy;
//    /**User associated with appointment. */
//    private User user;
//    /**Contact associated with the appointment. */
//    private Contact contact;
//    /**Customer associated with appointment. */
//    private Customer customer;
    /**Customer ID assocaited with appointment. */
    private int cusId;
    /**User ID associated with appointment. */
    private int userId;

    /**Constructor for appointment.
     * @param apptId Appointment ID.
     * @param custId Customer ID.
     *  @param userId User ID.
     *  @param contactId Contact ID.
     *  @param contact Contact for appointment.
     *  @param apptTitle Title of appointment.
     *  @param desc Description of appointment.
     *  @param loc Location of appointment.
     *  @param type Type of appointment.
     *  @param startData Start date and time of appointment.
     *  @param endData End date and time of appointment. */
    public Appointment(int apptId, int custId, int userId, int contactId, String contact, String apptTitle, String desc, String loc, String type, LocalDateTime startData, LocalDateTime endData) {
        this.apptId = apptId;
        this.apptTitle = apptTitle;
        this.desc = desc;
        this.loc = loc;
        this.type = type;
        this.start = startData;
        this.end = endData;
        this.cusId = custId;
        this.userId = userId;
        this.conId = contactId;
        this.conName = contact;

    }
    /**Additional appointment constructor.
     * @param type Type of appointment to be counted.
     * @param countOfApptsByType Number of a given type of appointment. */
    public Appointment(String type, int countOfApptsByType){
        this.type = type;
        this.countOfApptsByType = countOfApptsByType;
    }

    public Appointment() {

    }

    public Appointment(int totalAppts, String month, String type){
        this.totalAppts = totalAppts;
        this.month = month;
        this.type = type;
    }

    public Appointment(String type, int countOfApptsByType, int monthStart){
        this.type = type;
        this.countOfApptsByType = countOfApptsByType;
        this.monthStart = monthStart;
    }
    public int getMonthStart(){return monthStart;}


    /**Getter for appointment ID.
     * @return Returns the appointment ID. */
    public int getApptId(){
        return apptId;
    }

    public int getCountOfApptsByType(){return countOfApptsByType;}


    /**Setter for the appointment ID.
      @param apptId appointment ID to set.  */
    public void setApptId(int apptId) {
        this.apptId = apptId;
    }

    /**Getter for appointment title.
     * @return Returns the Appointment Title. */
    public String getApptTitle() {
        return apptTitle;
    }

    /**Setter for the appointment title.
      * @param apptTitle Title of appointment to set. */
    public void setTitle(String apptTitle) {
        this.apptTitle = apptTitle;
    }

    /**Getter for the description of the appointment.
     * @return Returns the appointment description. */
    public String getDesc() {
        return desc;
    }

    /**Setter for the description of the appointment.
      * @param desc description of the appointment to set. */
    public void setDesc(String desc) {
        this.desc = desc;
    }



    /**Getter for the location of the appointment.
     * @return Returns the location of the appointment. */
    public String getLoc() {
        return loc;
    }

    /**Setter for the location of the appointment.
      @param loc Location of the appointment. */
    public void setLoc(String loc) {
        this.loc = loc;
    }

    /**Getter for the type of the appointment.
     * @return Returns the Type of appointment. */
    public String getType() {
        return type;
    }

    /**Setter for the appointment type.
      @param type the type of appointment to set.  */
    public void setType(String type) {
        this.type = type;
    }

    /**Getter for the start time and date of the appointment.
     * @return Returns the Start Date/Time of appointment. */
    public LocalDateTime getStart() {
        return start;
    }

    /**Getter for the start Time only of appointment.
     * @return Returns only the start time of appointment. */
    public LocalTime getStartTime() {
        return start.toLocalTime();
    }

    /**Getter for the start Date only of appointment.
     * @return Returns only the start Date of appointment. */
    public LocalDate getStartDate(){return start.toLocalDate();}
    /**Setter for the start time and date of the appointment.
      @param start the time and date to set. */
    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    /**Getter for the end time and date of the appointment.
     * @return Returns the end date/time of the appointment. */
    public LocalDateTime getEnd() {
        return end;
    }

    /**Getter for the end time only of appointment.
     * @return Returns the end Time onlu of the appointment. */
    public LocalTime getEndTime(){return end.toLocalTime();}

    /**Getter for the end date only of appointment.
     * @return Returns only the end date of the appointment. */
    public LocalDate getEndDate(){return end.toLocalDate();}

    /**Setter for the end time and date of the appointment.
     @param end the time and date to set. */
    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    /**Getter for the time and date of when the appointment was created.
     * @return Returns the date the appointment was created. */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**Setter for the time and date of when the appointment was created.
      @param createDate the date and time when the appointment was created. */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**Getter for who created the appointment.
     * @return Returns who created the appointment. */
    public String getCreatedBy() {
        return createdBy;
    }

    /**Setter for who created the appointment.
      @param createdBy Who created the appointment. */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**Getter for the time and date of the most recent update to the appointment.
     * @return Returns the date of the most recent update to the appointment record. */
    public LocalDateTime getMostRecentUpdate() {
        return mostRecentUpdate;
    }

    /**Setter for the date and time of the most recent update.
      @param mostRecentUpdate date and time of when the most recent update was performed. */
    public void setMostRecentUpdate(LocalDateTime mostRecentUpdate) {
        this.mostRecentUpdate = mostRecentUpdate;
    }

    /**Getter for who performed the most recent update to the appointment.
     * @return returns the user who most recently updated the appointment record. */
    public String getMostRecentUpdateBy() {
        return mostRecentUpdateBy;
    }

    /**Setter for who performed the most recent update on the appointment.
      @param mostRecentUpdateBy who performed the most recent update. */
    public void setMostRecentUpdateBy(String mostRecentUpdateBy) {
        this.mostRecentUpdateBy = mostRecentUpdateBy;
    }

    /**Getter for the customer ID associated with the appointment.
     * @return Returns the customer ID associated with the appointment. */
    public int getCusId(){
        return cusId;
    }

    /**Setter for the customer ID associated with the appointment.
      @param cusId the customer ID to associate with the appointment. */
    public void setCusId(int cusId) {
        this.cusId = cusId;
    }

    /**Getter for the user ID associated with the appointment.
     * @return Returns the user ID associated with the appointment. */
    public int getUserId() {
        return userId;
    }

    /**Setter for the user ID associated with the appointment.
      @param userId the user ID to associate with the appointment. */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**Getter for the contact ID to associate with the appointment.
     * @return Returns the contact ID associated with the appointment. */
    public int getConId() {
        return conId;
    }

    /**Setter for the contact ID to associate with the appointment.
     * @param conId Contact ID associated with appointment. */
    public void setConId(int conId) {
        this.conId = conId;
    }

    /**Getter for the name of the contact associated with the appointment.
     * @return Returns the name of the contact associated with the appointment. */
    public String getConName() {
        return conName;
    }

    /**Setter for the contact name to associate with the appointment.
      @param conName name of the contact to associate with the appointment. */
    public void setConName(String conName) {
        this.conName = conName;
    }

}