package ua.com.paradine.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.ZonedDateTimeFilter;

/**
 * Criteria class for the {@link ua.com.paradine.domain.Restaurant} entity. This class is used
 * in {@link ua.com.paradine.web.rest.RestaurantResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /restaurants?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RestaurantCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter uuid;

    private IntegerFilter capacity;

    private FloatFilter geolat;

    private FloatFilter geolng;

    private StringFilter photoUrl;

    private StringFilter altName1;

    private StringFilter altName2;

    private StringFilter altName3;

    private StringFilter googlePlacesId;

    private ZonedDateTimeFilter createdAt;

    private ZonedDateTimeFilter updatedAt;

    private StringFilter name;

    public RestaurantCriteria() {
    }

    public RestaurantCriteria(RestaurantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.capacity = other.capacity == null ? null : other.capacity.copy();
        this.geolat = other.geolat == null ? null : other.geolat.copy();
        this.geolng = other.geolng == null ? null : other.geolng.copy();
        this.photoUrl = other.photoUrl == null ? null : other.photoUrl.copy();
        this.altName1 = other.altName1 == null ? null : other.altName1.copy();
        this.altName2 = other.altName2 == null ? null : other.altName2.copy();
        this.altName3 = other.altName3 == null ? null : other.altName3.copy();
        this.googlePlacesId = other.googlePlacesId == null ? null : other.googlePlacesId.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.name = other.name == null ? null : other.name.copy();
    }

    @Override
    public RestaurantCriteria copy() {
        return new RestaurantCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getUuid() {
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public IntegerFilter getCapacity() {
        return capacity;
    }

    public void setCapacity(IntegerFilter capacity) {
        this.capacity = capacity;
    }

    public FloatFilter getGeolat() {
        return geolat;
    }

    public void setGeolat(FloatFilter geolat) {
        this.geolat = geolat;
    }

    public FloatFilter getGeolng() {
        return geolng;
    }

    public void setGeolng(FloatFilter geolng) {
        this.geolng = geolng;
    }

    public StringFilter getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(StringFilter photoUrl) {
        this.photoUrl = photoUrl;
    }

    public StringFilter getAltName1() {
        return altName1;
    }

    public void setAltName1(StringFilter altName1) {
        this.altName1 = altName1;
    }

    public StringFilter getAltName2() {
        return altName2;
    }

    public void setAltName2(StringFilter altName2) {
        this.altName2 = altName2;
    }

    public StringFilter getAltName3() {
        return altName3;
    }

    public void setAltName3(StringFilter altName3) {
        this.altName3 = altName3;
    }

    public StringFilter getGooglePlacesId() {
        return googlePlacesId;
    }

    public void setGooglePlacesId(StringFilter googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
    }

    public ZonedDateTimeFilter getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTimeFilter createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTimeFilter getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTimeFilter updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RestaurantCriteria that = (RestaurantCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(capacity, that.capacity) &&
            Objects.equals(geolat, that.geolat) &&
            Objects.equals(geolng, that.geolng) &&
            Objects.equals(photoUrl, that.photoUrl) &&
            Objects.equals(altName1, that.altName1) &&
            Objects.equals(altName2, that.altName2) &&
            Objects.equals(altName3, that.altName3) &&
            Objects.equals(googlePlacesId, that.googlePlacesId) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        uuid,
        capacity,
        geolat,
        geolng,
        photoUrl,
        altName1,
        altName2,
        altName3,
        googlePlacesId,
        createdAt,
        updatedAt,
        name
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (capacity != null ? "capacity=" + capacity + ", " : "") +
                (geolat != null ? "geolat=" + geolat + ", " : "") +
                (geolng != null ? "geolng=" + geolng + ", " : "") +
                (photoUrl != null ? "photoUrl=" + photoUrl + ", " : "") +
                (altName1 != null ? "altName1=" + altName1 + ", " : "") +
                (altName2 != null ? "altName2=" + altName2 + ", " : "") +
                (altName3 != null ? "altName3=" + altName3 + ", " : "") +
                (googlePlacesId != null ? "googlePlacesId=" + googlePlacesId + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
            "}";
    }

}
