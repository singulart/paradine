package ua.com.paradine.core.business;

public class SafetyVO extends RestaurantVO {

    private SafetyMarker safety;

    public SafetyMarker getSafety() {
        return safety;
    }

    public void setSafety(SafetyMarker safety) {
        this.safety = safety;
    }
}
