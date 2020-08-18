package ua.com.paradine.domain;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * A Restaurant.
 */
@Entity
@Table(name = "restaurant")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "restaurant")
public class Restaurant implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 3, max = 128)
    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Size(max = 128)
    @Column(name = "alt_name_1", length = 128)
    private String altName1;

    @NotNull
    @Size(max = 200)
    @Column(name = "address_en", length = 200, nullable = false)
    private String addressEn;

    @Size(max = 200)
    @Column(name = "address_ru", length = 200)
    private String addressRu;

    @Size(max = 200)
    @Column(name = "address_ua", length = 200)
    private String addressUa;

    @Size(max = 255)
    @Column(name = "google_places_id", length = 255)
    private String googlePlacesId;

    @NotNull
    @Column(name = "geolat", nullable = false)
    private Float geolat;

    @NotNull
    @Column(name = "geolng", nullable = false)
    private Float geolng;

    @NotNull
    @Size(min = 2, max = 256)
    @Pattern(regexp = "[(http(s)?):\\/\\/(www\\.)?a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#?&//=]*)")
    @Column(name = "photo_url", length = 256, nullable = false)
    private String photoUrl;

    @Size(max = 128)
    @Column(name = "alt_name_2", length = 128)
    private String altName2;

    @Size(max = 128)
    @Column(name = "alt_name_3", length = 128)
    private String altName3;

    @NotNull
    @Min(value = 3)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @NotNull
    @Column(name = "updated_at", nullable = false)
    private ZonedDateTime updatedAt;

    @NotNull
    @Size(min = 36, max = 36)
    @Pattern(regexp = "[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}")
    @Column(name = "uuid", length = 36, nullable = false, unique = true)
    private String uuid;

    @OneToOne
    private City city;

    @Transient
    private List<PopularTime> popularTimes = new ArrayList<>();

    @Transient
    private List<WorkingHours> workingHours = new ArrayList<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Restaurant name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltName1() {
        return altName1;
    }

    public Restaurant altName1(String altName1) {
        this.altName1 = altName1;
        return this;
    }

    public void setAltName1(String altName1) {
        this.altName1 = altName1;
    }

    public String getAddressEn() {
        return addressEn;
    }

    public Restaurant addressEn(String addressEn) {
        this.addressEn = addressEn;
        return this;
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public String getAddressRu() {
        return addressRu;
    }

    public Restaurant addressRu(String addressRu) {
        this.addressRu = addressRu;
        return this;
    }

    public void setAddressRu(String addressRu) {
        this.addressRu = addressRu;
    }

    public String getAddressUa() {
        return addressUa;
    }

    public Restaurant addressUa(String addressUa) {
        this.addressUa = addressUa;
        return this;
    }

    public void setAddressUa(String addressUa) {
        this.addressUa = addressUa;
    }

    public String getGooglePlacesId() {
        return googlePlacesId;
    }

    public Restaurant googlePlacesId(String googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
        return this;
    }

    public void setGooglePlacesId(String googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
    }

    public Float getGeolat() {
        return geolat;
    }

    public Restaurant geolat(Float geolat) {
        this.geolat = geolat;
        return this;
    }

    public void setGeolat(Float geolat) {
        this.geolat = geolat;
    }

    public Float getGeolng() {
        return geolng;
    }

    public Restaurant geolng(Float geolng) {
        this.geolng = geolng;
        return this;
    }

    public void setGeolng(Float geolng) {
        this.geolng = geolng;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public Restaurant photoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
        return this;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getAltName2() {
        return altName2;
    }

    public Restaurant altName2(String altName2) {
        this.altName2 = altName2;
        return this;
    }

    public void setAltName2(String altName2) {
        this.altName2 = altName2;
    }

    public String getAltName3() {
        return altName3;
    }

    public Restaurant altName3(String altName3) {
        this.altName3 = altName3;
        return this;
    }

    public void setAltName3(String altName3) {
        this.altName3 = altName3;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public Restaurant capacity(Integer capacity) {
        this.capacity = capacity;
        return this;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public Restaurant createdAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public Restaurant updatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getUuid() {
        return uuid;
    }

    public Restaurant uuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public City getCity() {
        return city;
    }

    public Restaurant city(City city) {
        this.city = city;
        return this;
    }

    public void setCity(City city) {
        this.city = city;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    public Restaurant addPopularTimes(PopularTime popularTime) {
        this.popularTimes.add(popularTime);
        popularTime.setRestaurant(this);
        return this;
    }

    public Restaurant removePopularTimes(PopularTime popularTime) {
        this.popularTimes.remove(popularTime);
        popularTime.setRestaurant(null);
        return this;
    }

    public List<PopularTime> getPopularTimes() {
        return popularTimes;
    }

    public List<WorkingHours> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHours> workingHours) {
        this.workingHours = workingHours;
    }

    public void setPopularTimes(List<PopularTime> popularTimes) {
        this.popularTimes = popularTimes;
    }

    public Restaurant popularTimes(List<PopularTime> popularTimes) {
        this.popularTimes = popularTimes;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Restaurant)) {
            return false;
        }
        return id != null && id.equals(((Restaurant) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Restaurant{" +
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
            "}";
    }
}
