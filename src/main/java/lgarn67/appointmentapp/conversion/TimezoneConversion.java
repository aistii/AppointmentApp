package lgarn67.appointmentapp.conversion;

import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TimezoneConversion {
    public static ZoneId getUtcZoneId() {
        return utcZoneId;
    }

    public static ZoneId getLocalZoneId() {
        return localZoneId;
    }

    public static ZoneId getEastZoneId() {
        return eastZoneId;
    }

    // Time Zones in Play
    static ZoneId utcZoneId = ZoneId.of("UTC");
    static ZoneId localZoneId = ZoneId.systemDefault();
    static ZoneId eastZoneId = ZoneId.of("US/Eastern");


    public boolean checkStartingTime(ZonedDateTime userInput) {
        // The logic here
        LocalTime startBizTime = LocalTime.of(8,0);
        int startTimeH = userInput.getHour();
        int startTimeM = userInput.getMinute();
        LocalTime timeToTest = LocalTime.of(startTimeH, startTimeM);
        if (timeToTest.isAfter(startBizTime) || timeToTest.equals(startBizTime)){
            return true;
        } else {
            return false;
        }
    }
}
