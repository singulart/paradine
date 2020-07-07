package ua.com.paradine.core.business.vo.commands;

import java.time.OffsetDateTime;

public class SubmitVisitIntentCommand {

    private String user;
    private OffsetDateTime when;
    private String restaurantId;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public OffsetDateTime getWhen() {
        return when;
    }

    public void setWhen(OffsetDateTime when) {
        this.when = when;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}
