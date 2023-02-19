package utility;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**@author Nick Johnson Student_ID# 001354777
 * List manager utilty class. */
public class ListManager {
    /**List containing all appointment types. */
    public static ObservableList<String> apptTypes = FXCollections.observableArrayList("Planning", "De-Briefing", "Meeting", "Sales");
}
