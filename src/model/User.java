package model;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**@author Nick Johnson Student_ID# 001354777
 * Model class for user. */
public class User {
    /**User ID. */
    private int userId;
    /**Username. */
    private String userName;
    /**Password. */
    private String pw;
    /**When the user was created. */
    private LocalDateTime createDate;
    /**Who created the record. */
    private String createdBy;
    /**When the most recent update took place. */
    private LocalDateTime mostRecentUpdate;
    /**Who performed the most recent update. */
    private String mostRecentUpdateBy;
    /**Is the user online. */
    private boolean online;

    /**User constructor.
     * @param userId User ID for the user.
     * @param userName Username associated with the user.
     * @param pw password associated with the user.
     * @param createDate when the record was created.
     * @param createdBy who created the record.
     * @param mostRecentUpdate The most recent update.
     * @param mostRecentUpdateBy who performed the most recent update. */
    public User(int userId, String userName, String pw, LocalDateTime createDate, String createdBy, LocalDateTime mostRecentUpdate, String mostRecentUpdateBy){
        this.userId = userId;
        this.userName = userName;
        this.pw = pw;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.mostRecentUpdate = mostRecentUpdate;
        this.mostRecentUpdateBy = mostRecentUpdateBy;
        this.online = false;
    }
    public User(){}

    /**Getter for user ID.
     * @return Returns the user ID. */
    public int getUserId(){
        return userId;
    }

    /**Setter for user ID.
     * @param userId User ID to set. */
    public void setCreatedBy(int userId){
        this.userId = userId;
    }

    /**Getter for username.
     * @return Returns the username. */
    public String getUserName() {
        return userName;
    }

    /**Setter for username.
     * @param userName the username to set.*/
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**Getter for password.
     * @return Returns the Password for the user. */
    public String getPw(){
        return pw;
    }

    /**Setter for password.
     * @param pw the password to set. */
    public void setPw(String pw){
        this.pw = pw;
    }

    /**Getter for the creation date of the user.
     * @return Returns the date the user record was created. */
    public LocalDateTime getCreateDate() {
        return createDate;
    }

    /**Setter for the creation date of the record.
     * @param createDate date the user was created. */
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    /**Getter for who made the most recent updates.
     * @return Returns who made the most recent update to the user record.  */
    public String getMostRecentUpdateBy() {
        return mostRecentUpdateBy;
    }

    /**Setter for the most recent update.
     * @param mostRecentUpdateBy who most recently update the user. */
    public void setMostRecentUpdateBy(String mostRecentUpdateBy) {
        this.mostRecentUpdateBy = mostRecentUpdateBy;
    }

    /**Boolean indicating whether the user is online.
     * @return Returns a boolean value that indicates whether the uer is online or not. */
    public boolean isOnline() {
        return online;
    }

    /**Setter for online boolean indicator.
     * @param online Boolean indicator of online status. */
    public void setOnline(boolean online) {
        this.online = online;
    }

    @Override
    public String toString(){
        return this.userName;
    }
}
