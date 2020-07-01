package ua.com.paradine.core.business;

public class ViewRestaurantsListCriteria {

    private String query;
    private String citySlug;
    private Float lat;
    private Float lng;
    private Integer page;

    private ViewRestaurantsListCriteria() {}

    public String getQuery() {
        return query;
    }

    public String getCitySlug() {
        return citySlug;
    }

    public Float getLat() {
        return lat;
    }

    public Float getLng() {
        return lng;
    }

    public Integer getPage() {
        return page;
    }

    public static final class Builder {

        private String query;
        private String citySlug;
        private Float lat;
        private Float lng;
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

        public Builder withQuery(String query) {
            this.query = query;
            return this;
        }

        public Builder withCitySlug(String citySlug) {
            this.citySlug = citySlug;
            return this;
        }

        public Builder withLat(Float lat) {
            this.lat = lat;
            return this;
        }

        public Builder withLng(Float lng) {
            this.lng = lng;
            return this;
        }

        public ViewRestaurantsListCriteria build() {
            ViewRestaurantsListCriteria viewRestaurantsListCriteria = new ViewRestaurantsListCriteria();
            viewRestaurantsListCriteria.page = this.page;
            viewRestaurantsListCriteria.query = this.query;
            viewRestaurantsListCriteria.citySlug = this.citySlug;
            viewRestaurantsListCriteria.lng = this.lng;
            viewRestaurantsListCriteria.lat = this.lat;
            return viewRestaurantsListCriteria;
        }
    }
}
