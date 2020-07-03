package ua.com.paradine.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.WorkingHours} entity.
 */
public class WorkingHoursDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    private String dayOfWeek;

    @NotNull
    private Boolean closed;

    @Min(value = 0)
    @Max(value = 24)
    private Integer openingHour;

    @Min(value = 0)
    @Max(value = 24)
    private Integer closingHour;


    private Long restaurantId;

    private String restaurantName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean isClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Integer getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(Integer openingHour) {
        this.openingHour = openingHour;
    }

    public Integer getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
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
        if (!(o instanceof WorkingHoursDTO)) {
            return false;
        }

        return id != null && id.equals(((WorkingHoursDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingHoursDTO{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", closed='" + isClosed() + "'" +
            ", openingHour=" + getOpeningHour() +
            ", closingHour=" + getClosingHour() +
            ", restaurantId=" + getRestaurantId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            "}";
    }
}
