package ua.com.paradine.core.business.vo;

import java.time.OffsetDateTime;

public class IntendedVisitVO {

    private String id;
    private OffsetDateTime visitTime;
    private RestaurantVO restaurant;
    private String kindOfDay; // today or tomorrow

    public OffsetDateTime getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(OffsetDateTime visitTime) {
        this.visitTime = visitTime;
    }

    public RestaurantVO getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(RestaurantVO restaurant) {
        this.restaurant = restaurant;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKindOfDay() {
        return kindOfDay;
    }

    public void setKindOfDay(String kindOfDay) {
        this.kindOfDay = kindOfDay;
    }
}
