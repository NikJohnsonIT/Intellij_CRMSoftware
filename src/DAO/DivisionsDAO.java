package DAO;
import model.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utility.DBConnection;

import javax.management.Query;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**@author Nick Johnson Student_ID# 001354777
 * Data Access Object for first level divisions. */
public class DivisionsDAO {

    /**List of all first level divisions. */
    public static ObservableList<FirstLevelDivision> allFirstLevelDivisions = FXCollections.observableArrayList();

    /**Function responsible for getting all first level divisions.
     * @throws SQLException When a DB exception is detected.
     * @return Returns a list of all first level divisions. */
    public static ObservableList<FirstLevelDivision> getAllDivisions() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();

            String sqlStatement = ("SELECT * FROM first_level_divisions");
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int divId = rs.getInt("Division_ID");
                int countryId = rs.getInt("Country_ID");
                String division = rs.getString("Division");
                allFirstLevelDivisions.add(new FirstLevelDivision(divId, countryId, division));
            }
            return allFirstLevelDivisions;
        }

        /**Function responsible for getting all country names.
         * @throws SQLException When a DB exception is detected.
         * @return Returns a list of all division names. */
        public static ObservableList<String> getAllCountryDivisionNames() throws SQLException {
        String queryResult = "";
        Connection connection = DBConnection.getConnection();
        ObservableList<String> allCountryDivisions = FXCollections.observableArrayList();
        String sqlStatement = ("SELECT * FROM first_level_divisions");
        PreparedStatement ps = connection.prepareStatement(sqlStatement);
        ResultSet rs = ps.executeQuery();
        while (rs.next()){
            String div = rs.getString("Division");
            allCountryDivisions.add(div);
        }
        return allCountryDivisions;
        }

        /**Function responsible for getting first level divisions for a specific country.
          * @param countryId the country from which to get first level divisions.
          * @throws SQLException When a DB exception is detected.
          * @return Returns a list of 1st level divisions filtered by a provided country ID. */
        public static ObservableList<FirstLevelDivision> getSingleCountryDivision(int countryId) throws SQLException {
            String queryResult = "";
            Connection connection = DBConnection.getConnection();
        ObservableList<FirstLevelDivision> singleCountryDivisions = FXCollections.observableArrayList();
            String sqlStatement = ("SELECT * FROM first_level_divisions WHERE Country_ID=?");
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setInt(1, countryId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int divId = rs.getInt("Division_ID");
                String div = rs.getString("Division");
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divId, countryId, div);
                singleCountryDivisions.add(firstLevelDivision);
            }
            return singleCountryDivisions;
        }

        /**Function responsible for providing a list of first level divisions filtered by a provided Division ID.
          * @param divId the division ID to be used for filtering list results.
          * @throws SQLException When a DB exception is detected.
          * @return Returns a list of filtered first level divisions based on a provided division ID. */
        public static FirstLevelDivision getDivisionById (int divId) throws SQLException {
            Connection connection = DBConnection.getConnection();
            String sqlStatement = ("SELECT * FROM first_level_divisions WHERE Division_Id=?");
            PreparedStatement ps = connection.prepareStatement(sqlStatement);
            ps.setInt(1, divId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                int countryId = rs.getInt("Country_Id");
                String div = rs.getString("Division");
                FirstLevelDivision firstLevelDivision = new FirstLevelDivision(divId, countryId, div);
                return firstLevelDivision;
            }
            return null;
        }
    }

