package ua.com.paradine.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.IntendedVisit} entity.
 */
public class IntendedVisitDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Pattern(regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}")
    private String uuid;

    @NotNull
    private ZonedDateTime visitStartDate;

    @NotNull
    private ZonedDateTime visitEndDate;

    @NotNull
    private Boolean cancelled;

    private Integer safety;


    private Long visitingUserId;

    private String visitingUserLogin;

    private Long restaurantId;

    private String restaurantName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getVisitStartDate() {
        return visitStartDate;
    }

    public void setVisitStartDate(ZonedDateTime visitStartDate) {
        this.visitStartDate = visitStartDate;
    }

    public ZonedDateTime getVisitEndDate() {
        return visitEndDate;
    }

    public void setVisitEndDate(ZonedDateTime visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getSafety() {
        return safety;
    }

    public void setSafety(Integer safety) {
        this.safety = safety;
    }

    public Long getVisitingUserId() {
        return visitingUserId;
    }

    public void setVisitingUserId(Long userId) {
        this.visitingUserId = userId;
    }

    public String getVisitingUserLogin() {
        return visitingUserLogin;
    }

    public void setVisitingUserLogin(String userLogin) {
        this.visitingUserLogin = userLogin;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof IntendedVisitDTO)) {
            return false;
        }

        return id != null && id.equals(((IntendedVisitDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntendedVisitDTO{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", visitStartDate='" + getVisitStartDate() + "'" +
            ", visitEndDate='" + getVisitEndDate() + "'" +
            ", cancelled='" + isCancelled() + "'" +
            ", safety=" + getSafety() +
            ", visitingUserId=" + getVisitingUserId() +
            ", visitingUserLogin='" + getVisitingUserLogin() + "'" +
            ", restaurantId=" + getRestaurantId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            "}";
    }
}
