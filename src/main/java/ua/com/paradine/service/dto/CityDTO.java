package ua.com.paradine.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.City} entity.
 */
public class CityDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String slug;

    @NotNull
    private String name;

    @NotNull
    private String image;

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CityDTO)) {
            return false;
        }

        return id != null && id.equals(((CityDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "CityDTO{" +
            "id=" + getId() +
            ", slug='" + getSlug() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            "}";
    }
}
