package ua.com.paradine.core.util;

import java.time.DayOfWeek;
import java.util.Map;

public class DaysOfWeek {

    public static final Map<DayOfWeek, String> DOW = Map.of(
        DayOfWeek.SUNDAY, "Su",
        DayOfWeek.MONDAY, "Mo",
        DayOfWeek.TUESDAY, "Tu",
        DayOfWeek.WEDNESDAY, "We",
        DayOfWeek.THURSDAY, "Th",
        DayOfWeek.FRIDAY, "Fr",
        DayOfWeek.SATURDAY, "Sa"
    );
}
