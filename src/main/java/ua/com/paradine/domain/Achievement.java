package ua.com.paradine.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A Achievement.
 */
@Entity
@Table(name = "achievement")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Achievement implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @NotNull
    @Column(name = "name_en", nullable = false)
    private String nameEn;

    @Column(name = "name_ru")
    private String nameRu;

    @Column(name = "name_ua")
    private String nameUa;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_ru")
    private String descriptionRu;

    @Column(name = "description_ua")
    private String descriptionUa;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public Achievement slug(String slug) {
        this.slug = slug;
        return this;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getNameEn() {
        return nameEn;
    }

    public Achievement nameEn(String nameEn) {
        this.nameEn = nameEn;
        return this;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public String getNameRu() {
        return nameRu;
    }

    public Achievement nameRu(String nameRu) {
        this.nameRu = nameRu;
        return this;
    }

    public void setNameRu(String nameRu) {
        this.nameRu = nameRu;
    }

    public String getNameUa() {
        return nameUa;
    }

    public Achievement nameUa(String nameUa) {
        this.nameUa = nameUa;
        return this;
    }

    public void setNameUa(String nameUa) {
        this.nameUa = nameUa;
    }

    public String getDescriptionEn() {
        return descriptionEn;
    }

    public Achievement descriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
        return this;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public String getDescriptionRu() {
        return descriptionRu;
    }

    public Achievement descriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
        return this;
    }

    public void setDescriptionRu(String descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public String getDescriptionUa() {
        return descriptionUa;
    }

    public Achievement descriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
        return this;
    }

    public void setDescriptionUa(String descriptionUa) {
        this.descriptionUa = descriptionUa;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Achievement)) {
            return false;
        }
        return id != null && id.equals(((Achievement) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Achievement{" +
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
