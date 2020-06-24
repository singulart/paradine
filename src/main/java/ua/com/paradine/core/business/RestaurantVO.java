package ua.com.paradine.core.business;

public class RestaurantVO {

    private String uuid;
    private String name;
    private Integer capacity;
    private Float geolat;
    private Float geolng;
    private String photoUrl;
    private String googlePlacesId;

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
}
