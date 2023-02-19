package utility;

import java.time.ZoneId;
import java.time.ZonedDateTime;

/**@author Nick Johnson Student_ID# 001354777
 * Interface class for displaying user location. */
public interface LocationDisplay {
    ZoneId z = ZoneId.systemDefault();
    String s = z.getId();

}
