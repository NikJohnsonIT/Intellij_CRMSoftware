package utility;

import java.sql.Timestamp;

/**@author Nick Johnson Student_ID# 001354777
 * Infolog interface to harvest username and time of login attempt. */
public interface InfoLog {

    String getInfo(String uName, Timestamp t);
}
