package ua.com.paradine.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link ua.com.paradine.domain.PopularTime} entity. This class is used
 * in {@link ua.com.paradine.web.rest.PopularTimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /popular-times?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PopularTimeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter dayOfWeek;

    private IntegerFilter occ06;

    private IntegerFilter occ07;

    private IntegerFilter occ08;

    private IntegerFilter occ09;

    private IntegerFilter occ10;

    private IntegerFilter occ11;

    private IntegerFilter occ12;

    private IntegerFilter occ13;

    private IntegerFilter occ14;

    private IntegerFilter occ15;

    private IntegerFilter occ16;

    private IntegerFilter occ17;

    private IntegerFilter occ18;

    private IntegerFilter occ19;

    private IntegerFilter occ20;

    private IntegerFilter occ21;

    private IntegerFilter occ22;

    private IntegerFilter occ23;

    private LongFilter restaurantId;

    public PopularTimeCriteria() {
    }

    public PopularTimeCriteria(PopularTimeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.dayOfWeek = other.dayOfWeek == null ? null : other.dayOfWeek.copy();
        this.occ06 = other.occ06 == null ? null : other.occ06.copy();
        this.occ07 = other.occ07 == null ? null : other.occ07.copy();
        this.occ08 = other.occ08 == null ? null : other.occ08.copy();
        this.occ09 = other.occ09 == null ? null : other.occ09.copy();
        this.occ10 = other.occ10 == null ? null : other.occ10.copy();
        this.occ11 = other.occ11 == null ? null : other.occ11.copy();
        this.occ12 = other.occ12 == null ? null : other.occ12.copy();
        this.occ13 = other.occ13 == null ? null : other.occ13.copy();
        this.occ14 = other.occ14 == null ? null : other.occ14.copy();
        this.occ15 = other.occ15 == null ? null : other.occ15.copy();
        this.occ16 = other.occ16 == null ? null : other.occ16.copy();
        this.occ17 = other.occ17 == null ? null : other.occ17.copy();
        this.occ18 = other.occ18 == null ? null : other.occ18.copy();
        this.occ19 = other.occ19 == null ? null : other.occ19.copy();
        this.occ20 = other.occ20 == null ? null : other.occ20.copy();
        this.occ21 = other.occ21 == null ? null : other.occ21.copy();
        this.occ22 = other.occ22 == null ? null : other.occ22.copy();
        this.occ23 = other.occ23 == null ? null : other.occ23.copy();
        this.restaurantId = other.restaurantId == null ? null : other.restaurantId.copy();
    }

    @Override
    public PopularTimeCriteria copy() {
        return new PopularTimeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(StringFilter dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public IntegerFilter getOcc06() {
        return occ06;
    }

    public void setOcc06(IntegerFilter occ06) {
        this.occ06 = occ06;
    }

    public IntegerFilter getOcc07() {
        return occ07;
    }

    public void setOcc07(IntegerFilter occ07) {
        this.occ07 = occ07;
    }

    public IntegerFilter getOcc08() {
        return occ08;
    }

    public void setOcc08(IntegerFilter occ08) {
        this.occ08 = occ08;
    }

    public IntegerFilter getOcc09() {
        return occ09;
    }

    public void setOcc09(IntegerFilter occ09) {
        this.occ09 = occ09;
    }

    public IntegerFilter getOcc10() {
        return occ10;
    }

    public void setOcc10(IntegerFilter occ10) {
        this.occ10 = occ10;
    }

    public IntegerFilter getOcc11() {
        return occ11;
    }

    public void setOcc11(IntegerFilter occ11) {
        this.occ11 = occ11;
    }

    public IntegerFilter getOcc12() {
        return occ12;
    }

    public void setOcc12(IntegerFilter occ12) {
        this.occ12 = occ12;
    }

    public IntegerFilter getOcc13() {
        return occ13;
    }

    public void setOcc13(IntegerFilter occ13) {
        this.occ13 = occ13;
    }

    public IntegerFilter getOcc14() {
        return occ14;
    }

    public void setOcc14(IntegerFilter occ14) {
        this.occ14 = occ14;
    }

    public IntegerFilter getOcc15() {
        return occ15;
    }

    public void setOcc15(IntegerFilter occ15) {
        this.occ15 = occ15;
    }

    public IntegerFilter getOcc16() {
        return occ16;
    }

    public void setOcc16(IntegerFilter occ16) {
        this.occ16 = occ16;
    }

    public IntegerFilter getOcc17() {
        return occ17;
    }

    public void setOcc17(IntegerFilter occ17) {
        this.occ17 = occ17;
    }

    public IntegerFilter getOcc18() {
        return occ18;
    }

    public void setOcc18(IntegerFilter occ18) {
        this.occ18 = occ18;
    }

    public IntegerFilter getOcc19() {
        return occ19;
    }

    public void setOcc19(IntegerFilter occ19) {
        this.occ19 = occ19;
    }

    public IntegerFilter getOcc20() {
        return occ20;
    }

    public void setOcc20(IntegerFilter occ20) {
        this.occ20 = occ20;
    }

    public IntegerFilter getOcc21() {
        return occ21;
    }

    public void setOcc21(IntegerFilter occ21) {
        this.occ21 = occ21;
    }

    public IntegerFilter getOcc22() {
        return occ22;
    }

    public void setOcc22(IntegerFilter occ22) {
        this.occ22 = occ22;
    }

    public IntegerFilter getOcc23() {
        return occ23;
    }

    public void setOcc23(IntegerFilter occ23) {
        this.occ23 = occ23;
    }

    public LongFilter getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(LongFilter restaurantId) {
        this.restaurantId = restaurantId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PopularTimeCriteria that = (PopularTimeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(dayOfWeek, that.dayOfWeek) &&
            Objects.equals(occ06, that.occ06) &&
            Objects.equals(occ07, that.occ07) &&
            Objects.equals(occ08, that.occ08) &&
            Objects.equals(occ09, that.occ09) &&
            Objects.equals(occ10, that.occ10) &&
            Objects.equals(occ11, that.occ11) &&
            Objects.equals(occ12, that.occ12) &&
            Objects.equals(occ13, that.occ13) &&
            Objects.equals(occ14, that.occ14) &&
            Objects.equals(occ15, that.occ15) &&
            Objects.equals(occ16, that.occ16) &&
            Objects.equals(occ17, that.occ17) &&
            Objects.equals(occ18, that.occ18) &&
            Objects.equals(occ19, that.occ19) &&
            Objects.equals(occ20, that.occ20) &&
            Objects.equals(occ21, that.occ21) &&
            Objects.equals(occ22, that.occ22) &&
            Objects.equals(occ23, that.occ23) &&
            Objects.equals(restaurantId, that.restaurantId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        dayOfWeek,
        occ06,
        occ07,
        occ08,
        occ09,
        occ10,
        occ11,
        occ12,
        occ13,
        occ14,
        occ15,
        occ16,
        occ17,
        occ18,
        occ19,
        occ20,
        occ21,
        occ22,
        occ23,
        restaurantId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PopularTimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (dayOfWeek != null ? "dayOfWeek=" + dayOfWeek + ", " : "") +
                (occ06 != null ? "occ06=" + occ06 + ", " : "") +
                (occ07 != null ? "occ07=" + occ07 + ", " : "") +
                (occ08 != null ? "occ08=" + occ08 + ", " : "") +
                (occ09 != null ? "occ09=" + occ09 + ", " : "") +
                (occ10 != null ? "occ10=" + occ10 + ", " : "") +
                (occ11 != null ? "occ11=" + occ11 + ", " : "") +
                (occ12 != null ? "occ12=" + occ12 + ", " : "") +
                (occ13 != null ? "occ13=" + occ13 + ", " : "") +
                (occ14 != null ? "occ14=" + occ14 + ", " : "") +
                (occ15 != null ? "occ15=" + occ15 + ", " : "") +
                (occ16 != null ? "occ16=" + occ16 + ", " : "") +
                (occ17 != null ? "occ17=" + occ17 + ", " : "") +
                (occ18 != null ? "occ18=" + occ18 + ", " : "") +
                (occ19 != null ? "occ19=" + occ19 + ", " : "") +
                (occ20 != null ? "occ20=" + occ20 + ", " : "") +
                (occ21 != null ? "occ21=" + occ21 + ", " : "") +
                (occ22 != null ? "occ22=" + occ22 + ", " : "") +
                (occ23 != null ? "occ23=" + occ23 + ", " : "") +
                (restaurantId != null ? "restaurantId=" + restaurantId + ", " : "") +
            "}";
    }

}
