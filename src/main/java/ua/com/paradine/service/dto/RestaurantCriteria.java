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

    private StringFilter name;

    private StringFilter altName1;

    private StringFilter addressEn;

    private StringFilter addressRu;

    private StringFilter addressUa;

    private StringFilter googlePlacesId;

    private FloatFilter geolat;

    private FloatFilter geolng;

    private StringFilter photoUrl;

    private StringFilter altName2;

    private StringFilter altName3;

    private IntegerFilter capacity;

    private ZonedDateTimeFilter createdAt;

    private ZonedDateTimeFilter updatedAt;

    private StringFilter uuid;

    private LongFilter cityId;

    public RestaurantCriteria() {
    }

    public RestaurantCriteria(RestaurantCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.name = other.name == null ? null : other.name.copy();
        this.altName1 = other.altName1 == null ? null : other.altName1.copy();
        this.addressEn = other.addressEn == null ? null : other.addressEn.copy();
        this.addressRu = other.addressRu == null ? null : other.addressRu.copy();
        this.addressUa = other.addressUa == null ? null : other.addressUa.copy();
        this.googlePlacesId = other.googlePlacesId == null ? null : other.googlePlacesId.copy();
        this.geolat = other.geolat == null ? null : other.geolat.copy();
        this.geolng = other.geolng == null ? null : other.geolng.copy();
        this.photoUrl = other.photoUrl == null ? null : other.photoUrl.copy();
        this.altName2 = other.altName2 == null ? null : other.altName2.copy();
        this.altName3 = other.altName3 == null ? null : other.altName3.copy();
        this.capacity = other.capacity == null ? null : other.capacity.copy();
        this.createdAt = other.createdAt == null ? null : other.createdAt.copy();
        this.updatedAt = other.updatedAt == null ? null : other.updatedAt.copy();
        this.uuid = other.uuid == null ? null : other.uuid.copy();
        this.cityId = other.cityId == null ? null : other.cityId.copy();
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

    public StringFilter getName() {
        return name;
    }

    public void setName(StringFilter name) {
        this.name = name;
    }

    public StringFilter getAltName1() {
        return altName1;
    }

    public void setAltName1(StringFilter altName1) {
        this.altName1 = altName1;
    }

    public StringFilter getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(StringFilter addressEn) {
        this.addressEn = addressEn;
    }

    public StringFilter getAddressRu() {
        return addressRu;
    }

    public void setAddressRu(StringFilter addressRu) {
        this.addressRu = addressRu;
    }

    public StringFilter getAddressUa() {
        return addressUa;
    }

    public void setAddressUa(StringFilter addressUa) {
        this.addressUa = addressUa;
    }

    public StringFilter getGooglePlacesId() {
        return googlePlacesId;
    }

    public void setGooglePlacesId(StringFilter googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
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

    public IntegerFilter getCapacity() {
        return capacity;
    }

    public void setCapacity(IntegerFilter capacity) {
        this.capacity = capacity;
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

    public StringFilter getUuid() {
        return uuid;
    }

    public void setUuid(StringFilter uuid) {
        this.uuid = uuid;
    }

    public LongFilter getCityId() {
        return cityId;
    }

    public void setCityId(LongFilter cityId) {
        this.cityId = cityId;
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
            Objects.equals(name, that.name) &&
            Objects.equals(altName1, that.altName1) &&
            Objects.equals(addressEn, that.addressEn) &&
            Objects.equals(addressRu, that.addressRu) &&
            Objects.equals(addressUa, that.addressUa) &&
            Objects.equals(googlePlacesId, that.googlePlacesId) &&
            Objects.equals(geolat, that.geolat) &&
            Objects.equals(geolng, that.geolng) &&
            Objects.equals(photoUrl, that.photoUrl) &&
            Objects.equals(altName2, that.altName2) &&
            Objects.equals(altName3, that.altName3) &&
            Objects.equals(capacity, that.capacity) &&
            Objects.equals(createdAt, that.createdAt) &&
            Objects.equals(updatedAt, that.updatedAt) &&
            Objects.equals(uuid, that.uuid) &&
            Objects.equals(cityId, that.cityId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        name,
        altName1,
        addressEn,
        addressRu,
        addressUa,
        googlePlacesId,
        geolat,
        geolng,
        photoUrl,
        altName2,
        altName3,
        capacity,
        createdAt,
        updatedAt,
        uuid,
        cityId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (name != null ? "name=" + name + ", " : "") +
                (altName1 != null ? "altName1=" + altName1 + ", " : "") +
                (addressEn != null ? "addressEn=" + addressEn + ", " : "") +
                (addressRu != null ? "addressRu=" + addressRu + ", " : "") +
                (addressUa != null ? "addressUa=" + addressUa + ", " : "") +
                (googlePlacesId != null ? "googlePlacesId=" + googlePlacesId + ", " : "") +
                (geolat != null ? "geolat=" + geolat + ", " : "") +
                (geolng != null ? "geolng=" + geolng + ", " : "") +
                (photoUrl != null ? "photoUrl=" + photoUrl + ", " : "") +
                (altName2 != null ? "altName2=" + altName2 + ", " : "") +
                (altName3 != null ? "altName3=" + altName3 + ", " : "") +
                (capacity != null ? "capacity=" + capacity + ", " : "") +
                (createdAt != null ? "createdAt=" + createdAt + ", " : "") +
                (updatedAt != null ? "updatedAt=" + updatedAt + ", " : "") +
                (uuid != null ? "uuid=" + uuid + ", " : "") +
                (cityId != null ? "cityId=" + cityId + ", " : "") +
            "}";
    }

}
