package ua.com.paradine.core.business;

public class HourlyClassifier {

    private Integer hour;
    private SafetyMarker marker;

    public HourlyClassifier(Integer hour, SafetyMarker marker) {
        this.hour = hour;
        this.marker = marker;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public SafetyMarker getMarker() {
        return marker;
    }

    public void setMarker(SafetyMarker marker) {
        this.marker = marker;
    }
}
