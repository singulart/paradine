package ua.com.paradine.core.business;

public enum SafetyMarker {
    CLOSED(-1), GREEN(10), YELLOW(20), RED(30);

    private Integer indicator;

    SafetyMarker(Integer indicator) {
        this.indicator = indicator;
    }

    public Integer getIndicator() {
        return indicator;
    }
}
