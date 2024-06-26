package ua.com.paradine.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A IntendedVisit.
 */
@Entity
@Table(name = "intended_visit")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class IntendedVisit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 36, max = 36)
    @Pattern(regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}")
    @Column(name = "uuid", length = 36, nullable = false, unique = true)
    private String uuid;

    @NotNull
    @Column(name = "visit_start_date", nullable = false)
    private ZonedDateTime visitStartDate;

    @NotNull
    @Column(name = "visit_end_date", nullable = false)
    private ZonedDateTime visitEndDate;

    @NotNull
    @Column(name = "cancelled", nullable = false)
    private Boolean cancelled;

    @Column(name = "safety")
    private Integer safety;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private User visitingUser;

    @OneToOne(optional = false)
    @NotNull
    @JoinColumn(unique = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public IntendedVisit uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public ZonedDateTime getVisitStartDate() {
        return visitStartDate;
    }

    public IntendedVisit visitStartDate(ZonedDateTime visitStartDate) {
        this.visitStartDate = visitStartDate;
        return this;
    }

    public void setVisitStartDate(ZonedDateTime visitStartDate) {
        this.visitStartDate = visitStartDate;
    }

    public ZonedDateTime getVisitEndDate() {
        return visitEndDate;
    }

    public IntendedVisit visitEndDate(ZonedDateTime visitEndDate) {
        this.visitEndDate = visitEndDate;
        return this;
    }

    public void setVisitEndDate(ZonedDateTime visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    public Boolean isCancelled() {
        return cancelled;
    }

    public IntendedVisit cancelled(Boolean cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public Integer getSafety() {
        return safety;
    }

    public IntendedVisit safety(Integer safety) {
        this.safety = safety;
        return this;
    }

    public void setSafety(Integer safety) {
        this.safety = safety;
    }

    public User getVisitingUser() {
        return visitingUser;
    }

    public IntendedVisit visitingUser(User user) {
        this.visitingUser = user;
        return this;
    }

    public void setVisitingUser(User user) {
        this.visitingUser = user;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public IntendedVisit restaurant(Restaurant restaurant) {
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
        if (!(o instanceof IntendedVisit)) {
            return false;
        }
        return id != null && id.equals(((IntendedVisit) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntendedVisit{" +
            "id=" + getId() +
            ", uuid='" + getUuid() + "'" +
            ", visitStartDate='" + getVisitStartDate() + "'" +
            ", visitEndDate='" + getVisitEndDate() + "'" +
            ", cancelled='" + isCancelled() + "'" +
            ", safety=" + getSafety() +
            "}";
    }
}
