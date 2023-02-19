package model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.ZonedDateTime;
/**@author Nick Johnson Student_ID# 001354777
 * Model class for country. */
public class Country {
    /**Country ID. */
    private int ctryId;
    /**Country name. */
    private String ctryName;

    /**List containing first level divisions (States or Provinces). */
    private ObservableList<FirstLevelDivision> firstLevelDivision = FXCollections.observableArrayList();

    /**Country constructor.
     * @param ctryId Country ID.
     * @param ctryName Country Name. */
    public Country(int ctryId, String ctryName){
        this.ctryId = ctryId;
        this.ctryName = ctryName;

    }

    /**Getter for country ID.
     * @return Returns the country ID. */
    public int getCtryId(){
        return ctryId;
    }

    /**Setter for country ID.
     * @param ctryId Country ID to set. */
    public void setCtryId(int ctryId){
        this.ctryId = ctryId;
    }

    /**Getter for country name.
     * @return Returns the name of the Country. */
    public String getCtryName(){
        return ctryName;
    }

    /**Setter for the country name.
     * @param ctryName name of the country to set. */
    public void setCtryName(String ctryName){
        this.ctryName = ctryName;
    }

    /**Getter for first level divisions of country.
     * @return Returns a list of first level divisoins. */
    public ObservableList<FirstLevelDivision> getLvl1Div(){
        return firstLevelDivision;
    }

    /**Setter for first level divisions of country.
     * @param firstLevelDivision first level divisions to set. */
    public void setLvl1Div(ObservableList<FirstLevelDivision> firstLevelDivision) {
        this.firstLevelDivision = firstLevelDivision;
    }

    @Override
    public String toString(){
        return this.ctryName;
    }
}
