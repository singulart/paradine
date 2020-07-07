package ua.com.paradine.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link ua.com.paradine.domain.IntendedVisit} entity. This class is used
 * in {@link ua.com.paradine.web.rest.IntendedVisitResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /intended-visits?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class IntendedVisitCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private ZonedDateTimeFilter visitStartDate;

    private ZonedDateTimeFilter visitEndDate;

    private BooleanFilter cancelled;

    private LongFilter visitingUserId;

    private LongFilter restaurantId;

    public IntendedVisitCriteria() {
    }

    public IntendedVisitCriteria(IntendedVisitCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.visitStartDate = other.visitStartDate == null ? null : other.visitStartDate.copy();
        this.visitEndDate = other.visitEndDate == null ? null : other.visitEndDate.copy();
        this.cancelled = other.cancelled == null ? null : other.cancelled.copy();
        this.visitingUserId = other.visitingUserId == null ? null : other.visitingUserId.copy();
        this.restaurantId = other.restaurantId == null ? null : other.restaurantId.copy();
    }

    @Override
    public IntendedVisitCriteria copy() {
        return new IntendedVisitCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public ZonedDateTimeFilter getVisitStartDate() {
        return visitStartDate;
    }

    public void setVisitStartDate(ZonedDateTimeFilter visitStartDate) {
        this.visitStartDate = visitStartDate;
    }

    public ZonedDateTimeFilter getVisitEndDate() {
        return visitEndDate;
    }

    public void setVisitEndDate(ZonedDateTimeFilter visitEndDate) {
        this.visitEndDate = visitEndDate;
    }

    public BooleanFilter getCancelled() {
        return cancelled;
    }

    public void setCancelled(BooleanFilter cancelled) {
        this.cancelled = cancelled;
    }

    public LongFilter getVisitingUserId() {
        return visitingUserId;
    }

    public void setVisitingUserId(LongFilter visitingUserId) {
        this.visitingUserId = visitingUserId;
    }

    public LongFilter getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(LongFilter restaurantId) {
        this.restaurantId = restaurantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final IntendedVisitCriteria that = (IntendedVisitCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(visitStartDate, that.visitStartDate) &&
            Objects.equals(visitEndDate, that.visitEndDate) &&
            Objects.equals(cancelled, that.cancelled) &&
            Objects.equals(visitingUserId, that.visitingUserId) &&
            Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        visitStartDate,
        visitEndDate,
        cancelled,
        visitingUserId,
        restaurantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "IntendedVisitCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (visitStartDate != null ? "visitStartDate=" + visitStartDate + ", " : "") +
                (visitEndDate != null ? "visitEndDate=" + visitEndDate + ", " : "") +
                (cancelled != null ? "cancelled=" + cancelled + ", " : "") +
                (visitingUserId != null ? "visitingUserId=" + visitingUserId + ", " : "") +
                (restaurantId != null ? "restaurantId=" + restaurantId + ", " : "") +
            "}";
    }

}
