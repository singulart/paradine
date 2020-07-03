package ua.com.paradine.core.business.vo;

import static java.util.Optional.ofNullable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RestaurantVO {

    private String uuid;
    private String name;
    private String addressEn;
    private String addressRu;
    private String addressUa;
    private Integer capacity;
    private Float geolat;
    private Float geolng;
    private String photoUrl;
    private String googlePlacesId;
    private Set<PopularTimeVO> popularTimes = new HashSet<>();
    private Set<WorkingHoursVO> workingHours = new HashSet<>();

    public String getUuid() {
        return uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public Float getGeolat() {
        return geolat;
    }

    public void setGeolat(Float geolat) {
        this.geolat = geolat;
    }

    public Float getGeolng() {
        return geolng;
    }

    public void setGeolng(Float geolng) {
        this.geolng = geolng;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getGooglePlacesId() {
        return googlePlacesId;
    }

    public void setGooglePlacesId(String googlePlacesId) {
        this.googlePlacesId = googlePlacesId;
    }

    public Set<PopularTimeVO> getPopularTimes() {
        return popularTimes;
    }

    public void setPopularTimes(Set<PopularTimeVO> popularTimes) {
        this.popularTimes = popularTimes;
    }

    public String getAddressUa() {
        return ofNullable(addressUa).orElse("");
    }

    public void setAddressUa(String addressUa) {
        this.addressUa = addressUa;
    }

    public String getAddressRu() {
        return ofNullable(addressRu).orElse("");
    }

    public void setAddressRu(String addressRu) {
        this.addressRu = addressRu;
    }

    public String getAddressEn() {
        return ofNullable(addressEn).orElse("");
    }

    public void setAddressEn(String addressEn) {
        this.addressEn = addressEn;
    }

    public Set<WorkingHoursVO> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Set<WorkingHoursVO> workingHours) {
        this.workingHours = workingHours;
    }
}
