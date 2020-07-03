package ua.com.paradine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A WorkingHours.
 */
@Entity
@Table(name = "working_hours")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "workinghours")
public class WorkingHours implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "day_of_week", length = 2, nullable = false)
    private String dayOfWeek;

    @NotNull
    @Column(name = "closed", nullable = false)
    private Boolean closed;

    @Min(value = 0)
    @Max(value = 24)
    @Column(name = "opening_hour")
    private Integer openingHour;

    @Min(value = 0)
    @Max(value = 24)
    @Column(name = "closing_hour")
    private Integer closingHour;

    @ManyToOne
    @JsonIgnoreProperties(value = "workingHours", allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public WorkingHours dayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean isClosed() {
        return closed;
    }

    public WorkingHours closed(Boolean closed) {
        this.closed = closed;
        return this;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Integer getOpeningHour() {
        return openingHour;
    }

    public WorkingHours openingHour(Integer openingHour) {
        this.openingHour = openingHour;
        return this;
    }

    public void setOpeningHour(Integer openingHour) {
        this.openingHour = openingHour;
    }

    public Integer getClosingHour() {
        return closingHour;
    }

    public WorkingHours closingHour(Integer closingHour) {
        this.closingHour = closingHour;
        return this;
    }

    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public WorkingHours restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WorkingHours)) {
            return false;
        }
        return id != null && id.equals(((WorkingHours) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WorkingHours{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", closed='" + isClosed() + "'" +
            ", openingHour=" + getOpeningHour() +
            ", closingHour=" + getClosingHour() +
            "}";
    }
}
