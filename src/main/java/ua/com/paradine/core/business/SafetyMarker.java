package ua.com.paradine.core.business;

public enum SafetyMarker {
    CLOSED(-1), GREEN(10), YELLOW(20), RED(30);

    SafetyMarker(Integer indicator) {
    }
}
