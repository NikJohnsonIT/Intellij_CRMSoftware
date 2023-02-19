package model;

/**@author Nick Johnson Student_ID# 001354777
 * Model class for Contacts. */
public class Contact {
    /**Contact ID. */
    private int conId;
    /**Contact Name. */
    private String conName;
    /**Contact Email address. */
    private String conEmail;

    /**Constructor for contact.
     * @param conId the contact ID.
     * @param conName the contact name.
     * @param conEmail the contact email address. */
    public Contact(int conId, String conName, String conEmail) {
        this.conId = conId;
        this.conName = conName;
        this.conEmail = conEmail;
    }

    /**Getter for contact ID.
     * @return Returns the Contact ID. */
    public int getConId(){
        return conId;
    }

    /**Setter for contact ID.
     * @param conId the contact ID. */
    public void setConId(int conId){
        this.conId = conId;
    }

    /**Getter for contact name.
     * @return Returns the Contact name.*/
    public String getConName(){
        return conName;
    }

    /**Setter for contact Name.
     * @param conName the name of the contact. */
    public void setConName(String conName){
        this.conName = conName;
    }

    /**Getter for the contact email address.
     * @return Returns the contact email address. */
    public String getConEmail(){
        return conEmail;
    }

    /**Setter for contact email address.
     * @param conEmail the email address of the contact. */
    public void setConEmail(String conEmail){
        this.conEmail = conEmail;
    }

    @Override
    public String toString() {
        return this.getConName();
    }
}
