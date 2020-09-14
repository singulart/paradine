package ua.com.paradine.core.business;

import static java.util.Optional.ofNullable;

public class ViewRestaurantsListCriteria {

    private String id;
    private String query;
    private String citySlug;
    private Double lat;
    private Double lng;
    private Integer page;

    private ViewRestaurantsListCriteria() {}

    public String getId() {
        return id;
    }

    public String getQuery() {
        return query;
    }

    public String getCitySlug() {
        return citySlug;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public Integer getPage() {
        return page;
    }

    public static final class Builder {

        private String id;
        private String query;
        private String citySlug;
        private Double lat;
        private Double lng;
        private Integer page;

        private Builder() {
        }

        public static Builder init() {
            return new Builder();
        }

        public Builder withPage(Integer page) {
            this.page = page;
            return this;
        }

        public Builder withId(String id) {
            this.id = id;
            return this;
        }

        public Builder withQuery(String query) {
            ofNullable(query).ifPresent((q) -> this.query = q);
            return this;
        }

        public Builder withCitySlug(String citySlug) {
            this.citySlug = citySlug;
            return this;
        }

        public Builder withLat(Float lat) {
            ofNullable(lat).ifPresent((l) -> this.lat = l.doubleValue());
            return this;
        }

        public Builder withLng(Float lng) {
            ofNullable(lng).ifPresent((l) -> this.lng = l.doubleValue());
            return this;
        }

        public ViewRestaurantsListCriteria build() {
            ViewRestaurantsListCriteria viewRestaurantsListCriteria = new ViewRestaurantsListCriteria();
            viewRestaurantsListCriteria.page = this.page;
            viewRestaurantsListCriteria.query = this.query;
            viewRestaurantsListCriteria.citySlug = this.citySlug;
            viewRestaurantsListCriteria.lng = this.lng;
            viewRestaurantsListCriteria.lat = this.lat;
            viewRestaurantsListCriteria.id = this.id;
            return viewRestaurantsListCriteria;
        }
    }
}
