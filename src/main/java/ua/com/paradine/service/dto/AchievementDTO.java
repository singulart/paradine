package ua.com.paradine.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.Achievement} entity.
 */
public class AchievementDTO implements Serializable {
    
    private Long id;

    @NotNull
    private String slug;

    @NotNull
    private String nameEn;

    private String nameRu;

    private String nameUa;

    private String descriptionEn;

    private String descriptionRu;

    private String descriptionUa;

    
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

    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameUa() {
        return nameUa;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AchievementDTO)) {
            return false;
        }

        return id != null && id.equals(((AchievementDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AchievementDTO{" +
            "id=" + getId() +
            ", slug='" + getSlug() + "'" +
            ", nameEn='" + getNameEn() + "'" +
            ", nameRu='" + getNameRu() + "'" +
            ", nameUa='" + getNameUa() + "'" +
            ", descriptionEn='" + getDescriptionEn() + "'" +
            ", descriptionRu='" + getDescriptionRu() + "'" +
            ", descriptionUa='" + getDescriptionUa() + "'" +
            "}";
    }
}
