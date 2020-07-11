package ua.com.paradine.core;

import static ua.com.paradine.core.ParadineConstants.DEFAULT_ZONE;

import java.time.OffsetDateTime;
import java.time.ZonedDateTime;

public class Nowness {

    public static OffsetDateTime getNow() {
        return ZonedDateTime.now(DEFAULT_ZONE).toOffsetDateTime();
    }

    public static ZonedDateTime getNowZoned() {
        return ZonedDateTime.now(DEFAULT_ZONE);
    }

}
