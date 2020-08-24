package ua.com.paradine.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.Restaurant} entity.
 */
public class RestaurantDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 3, max = 128)
    private String name;

    @Size(max = 128)
    private String altName1;

    @NotNull
    @Size(max = 200)
    private String addressEn;

    @Size(max = 200)
    private String addressRu;

    @Size(max = 200)
    private String addressUa;

    @Size(max = 255)
    private String googlePlacesId;

    @NotNull
    private Double geolat;

    @NotNull
    private Double geolng;

    @NotNull
    @Size(min = 2, max = 256)
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    private String photoUrl;

    @Size(max = 128)
    private String altName2;

    @Size(max = 128)
    private String altName3;

    @NotNull
    @Min(value = 3)
    private Integer capacity;

    @NotNull
    private ZonedDateTime createdAt;

    @NotNull
    private ZonedDateTime updatedAt;

    @NotNull
    @Size(min = 36, max = 36)
    @Pattern(regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}")
    private String uuid;


    private Long cityId;

    private String cityName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltName1() {
        return altName1;
    }

    public void setAltName1(String altName1) {
        this.altName1 = altName1;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getAddressRu() {
        return addressRu;
    }

    public void setAddressRu(String addressRu) {
        this.addressRu = addressRu;
    }

    public String getAddressUa() {
        return addressUa;
    }

    public void setAddressUa(String addressUa) {
        this.addressUa = addressUa;
    }

    public String getGooglePlacesId() {
        return googlePlacesId;
    }

    public void setGooglePlacesId(String googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
    }

    public Double getGeolat() {
        return geolat;
    }

    public void setGeolat(Double geolat) {
        this.geolat = geolat;
    }

    public Double getGeolng() {
        return geolng;
    }

    public void setGeolng(Double geolng) {
        this.geolng = geolng;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAltName2() {
        return altName2;
    }

    public void setAltName2(String altName2) {
        this.altName2 = altName2;
    }

    public String getAltName3() {
        return altName3;
    }

    public void setAltName3(String altName3) {
        this.altName3 = altName3;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RestaurantDTO)) {
            return false;
        }

        return id != null && id.equals(((RestaurantDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RestaurantDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", altName1='" + getAltName1() + "'" +
            ", addressEn='" + getAddressEn() + "'" +
            ", addressRu='" + getAddressRu() + "'" +
            ", addressUa='" + getAddressUa() + "'" +
            ", googlePlacesId='" + getGooglePlacesId() + "'" +
            ", geolat=" + getGeolat() +
            ", geolng=" + getGeolng() +
            ", photoUrl='" + getPhotoUrl() + "'" +
            ", altName2='" + getAltName2() + "'" +
            ", altName3='" + getAltName3() + "'" +
            ", capacity=" + getCapacity() +
            ", createdAt='" + getCreatedAt() + "'" +
            ", updatedAt='" + getUpdatedAt() + "'" +
            ", uuid='" + getUuid() + "'" +
            ", cityId=" + getCityId() +
            ", cityName='" + getCityName() + "'" +
            "}";
    }
}
