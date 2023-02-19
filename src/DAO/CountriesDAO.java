package DAO;

import com.mysql.cj.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;
import utility.DBConnection;

import java.sql.*;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object for Countries. */
public class CountriesDAO {

    /**List containing all countries. */
    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();

    /**Function to get all countries.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a list of all countries. */
    public static ObservableList<Country> getAllCountries() throws SQLException {
        if (!allCountries.isEmpty()) {
            return allCountries;
        }
        System.out.println("Getting all countries");
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("SELECT * FROM countries");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            int countryId = rs.getInt("Country_ID");
            String countryName = rs.getString("Country");
            allCountries.add(new Country(countryId, countryName));
        }
        System.out.println("there are " + allCountries.size() + " countries.");
        return allCountries;
    }

    /**Function to return a country from a provided country ID.
      * @param countryId the country ID of the country.
      * @throws SQLException When a DB exception is detected.
      * @return Returns a country. */
    public static Country getCountry(int countryId) throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        String sqlStatement = ("SELECT * FROM countries WHERE Country_Id = ?");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String countryName = rs.getString("Country");
            Country country = new Country(countryId, countryName);
            return country;
        }
        return null;
    }
}
