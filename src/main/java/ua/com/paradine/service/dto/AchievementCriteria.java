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

/**
 * Criteria class for the {@link ua.com.paradine.domain.Achievement} entity. This class is used
 * in {@link ua.com.paradine.web.rest.AchievementResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /achievements?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AchievementCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter slug;

    private StringFilter nameEn;

    private StringFilter nameRu;

    private StringFilter nameUa;

    private StringFilter descriptionEn;

    private StringFilter descriptionRu;

    private StringFilter descriptionUa;

    public AchievementCriteria() {
    }

    public AchievementCriteria(AchievementCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.slug = other.slug == null ? null : other.slug.copy();
        this.nameEn = other.nameEn == null ? null : other.nameEn.copy();
        this.nameRu = other.nameRu == null ? null : other.nameRu.copy();
        this.nameUa = other.nameUa == null ? null : other.nameUa.copy();
        this.descriptionEn = other.descriptionEn == null ? null : other.descriptionEn.copy();
        this.descriptionRu = other.descriptionRu == null ? null : other.descriptionRu.copy();
        this.descriptionUa = other.descriptionUa == null ? null : other.descriptionUa.copy();
    }

    @Override
    public AchievementCriteria copy() {
        return new AchievementCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getSlug() {
        return slug;
    }

    public void setSlug(StringFilter slug) {
        this.slug = slug;
    }

    public StringFilter getNameEn() {
        return nameEn;
    }

    public void setNameEn(StringFilter nameEn) {
        this.nameEn = nameEn;
    }

    public StringFilter getNameRu() {
        return nameRu;
    }

    public void setNameRu(StringFilter nameRu) {
        this.nameRu = nameRu;
    }

    public StringFilter getNameUa() {
        return nameUa;
    }

    public void setNameUa(StringFilter nameUa) {
        this.nameUa = nameUa;
    }

    public StringFilter getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(StringFilter descriptionEn) {
        this.descriptionEn = descriptionEn;
    }

    public StringFilter getDescriptionRu() {
        return descriptionRu;
    }

    public void setDescriptionRu(StringFilter descriptionRu) {
        this.descriptionRu = descriptionRu;
    }

    public StringFilter getDescriptionUa() {
        return descriptionUa;
    }

    public void setDescriptionUa(StringFilter descriptionUa) {
        this.descriptionUa = descriptionUa;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AchievementCriteria that = (AchievementCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(slug, that.slug) &&
            Objects.equals(nameEn, that.nameEn) &&
            Objects.equals(nameRu, that.nameRu) &&
            Objects.equals(nameUa, that.nameUa) &&
            Objects.equals(descriptionEn, that.descriptionEn) &&
            Objects.equals(descriptionRu, that.descriptionRu) &&
            Objects.equals(descriptionUa, that.descriptionUa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        slug,
        nameEn,
        nameRu,
        nameUa,
        descriptionEn,
        descriptionRu,
        descriptionUa
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AchievementCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (slug != null ? "slug=" + slug + ", " : "") +
                (nameEn != null ? "nameEn=" + nameEn + ", " : "") +
                (nameRu != null ? "nameRu=" + nameRu + ", " : "") +
                (nameUa != null ? "nameUa=" + nameUa + ", " : "") +
                (descriptionEn != null ? "descriptionEn=" + descriptionEn + ", " : "") +
                (descriptionRu != null ? "descriptionRu=" + descriptionRu + ", " : "") +
                (descriptionUa != null ? "descriptionUa=" + descriptionUa + ", " : "") +
            "}";
    }

}
