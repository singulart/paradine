package ua.com.paradine.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A PopularTime.
 */
@Entity
@Table(name = "popular_time")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class PopularTime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    @Column(name = "day_of_week", length = 2, nullable = false)
    private String dayOfWeek;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_01", nullable = false)
    private Integer occ01;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_02", nullable = false)
    private Integer occ02;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_03", nullable = false)
    private Integer occ03;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_04", nullable = false)
    private Integer occ04;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_05", nullable = false)
    private Integer occ05;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_06", nullable = false)
    private Integer occ06;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_07", nullable = false)
    private Integer occ07;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_08", nullable = false)
    private Integer occ08;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_09", nullable = false)
    private Integer occ09;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_10", nullable = false)
    private Integer occ10;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_11", nullable = false)
    private Integer occ11;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_12", nullable = false)
    private Integer occ12;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_13", nullable = false)
    private Integer occ13;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_14", nullable = false)
    private Integer occ14;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_15", nullable = false)
    private Integer occ15;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_16", nullable = false)
    private Integer occ16;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_17", nullable = false)
    private Integer occ17;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_18", nullable = false)
    private Integer occ18;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_19", nullable = false)
    private Integer occ19;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_20", nullable = false)
    private Integer occ20;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_21", nullable = false)
    private Integer occ21;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_22", nullable = false)
    private Integer occ22;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_23", nullable = false)
    private Integer occ23;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    @Column(name = "occ_24", nullable = false)
    private Integer occ24;

    @ManyToOne
    @JsonIgnoreProperties(value = "popularTimes", allowSetters = true)
    private Restaurant restaurant;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public PopularTime dayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
        return this;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getOcc01() {
        return occ01;
    }

    public PopularTime occ01(Integer occ01) {
        this.occ01 = occ01;
        return this;
    }

    public void setOcc01(Integer occ01) {
        this.occ01 = occ01;
    }

    public Integer getOcc02() {
        return occ02;
    }

    public PopularTime occ02(Integer occ02) {
        this.occ02 = occ02;
        return this;
    }

    public void setOcc02(Integer occ02) {
        this.occ02 = occ02;
    }

    public Integer getOcc03() {
        return occ03;
    }

    public PopularTime occ03(Integer occ03) {
        this.occ03 = occ03;
        return this;
    }

    public void setOcc03(Integer occ03) {
        this.occ03 = occ03;
    }

    public Integer getOcc04() {
        return occ04;
    }

    public PopularTime occ04(Integer occ04) {
        this.occ04 = occ04;
        return this;
    }

    public void setOcc04(Integer occ04) {
        this.occ04 = occ04;
    }

    public Integer getOcc05() {
        return occ05;
    }

    public PopularTime occ05(Integer occ05) {
        this.occ05 = occ05;
        return this;
    }

    public void setOcc05(Integer occ05) {
        this.occ05 = occ05;
    }

    public Integer getOcc06() {
        return occ06;
    }

    public PopularTime occ06(Integer occ06) {
        this.occ06 = occ06;
        return this;
    }

    public void setOcc06(Integer occ06) {
        this.occ06 = occ06;
    }

    public Integer getOcc07() {
        return occ07;
    }

    public PopularTime occ07(Integer occ07) {
        this.occ07 = occ07;
        return this;
    }

    public void setOcc07(Integer occ07) {
        this.occ07 = occ07;
    }

    public Integer getOcc08() {
        return occ08;
    }

    public PopularTime occ08(Integer occ08) {
        this.occ08 = occ08;
        return this;
    }

    public void setOcc08(Integer occ08) {
        this.occ08 = occ08;
    }

    public Integer getOcc09() {
        return occ09;
    }

    public PopularTime occ09(Integer occ09) {
        this.occ09 = occ09;
        return this;
    }

    public void setOcc09(Integer occ09) {
        this.occ09 = occ09;
    }

    public Integer getOcc10() {
        return occ10;
    }

    public PopularTime occ10(Integer occ10) {
        this.occ10 = occ10;
        return this;
    }

    public void setOcc10(Integer occ10) {
        this.occ10 = occ10;
    }

    public Integer getOcc11() {
        return occ11;
    }

    public PopularTime occ11(Integer occ11) {
        this.occ11 = occ11;
        return this;
    }

    public void setOcc11(Integer occ11) {
        this.occ11 = occ11;
    }

    public Integer getOcc12() {
        return occ12;
    }

    public PopularTime occ12(Integer occ12) {
        this.occ12 = occ12;
        return this;
    }

    public void setOcc12(Integer occ12) {
        this.occ12 = occ12;
    }

    public Integer getOcc13() {
        return occ13;
    }

    public PopularTime occ13(Integer occ13) {
        this.occ13 = occ13;
        return this;
    }

    public void setOcc13(Integer occ13) {
        this.occ13 = occ13;
    }

    public Integer getOcc14() {
        return occ14;
    }

    public PopularTime occ14(Integer occ14) {
        this.occ14 = occ14;
        return this;
    }

    public void setOcc14(Integer occ14) {
        this.occ14 = occ14;
    }

    public Integer getOcc15() {
        return occ15;
    }

    public PopularTime occ15(Integer occ15) {
        this.occ15 = occ15;
        return this;
    }

    public void setOcc15(Integer occ15) {
        this.occ15 = occ15;
    }

    public Integer getOcc16() {
        return occ16;
    }

    public PopularTime occ16(Integer occ16) {
        this.occ16 = occ16;
        return this;
    }

    public void setOcc16(Integer occ16) {
        this.occ16 = occ16;
    }

    public Integer getOcc17() {
        return occ17;
    }

    public PopularTime occ17(Integer occ17) {
        this.occ17 = occ17;
        return this;
    }

    public void setOcc17(Integer occ17) {
        this.occ17 = occ17;
    }

    public Integer getOcc18() {
        return occ18;
    }

    public PopularTime occ18(Integer occ18) {
        this.occ18 = occ18;
        return this;
    }

    public void setOcc18(Integer occ18) {
        this.occ18 = occ18;
    }

    public Integer getOcc19() {
        return occ19;
    }

    public PopularTime occ19(Integer occ19) {
        this.occ19 = occ19;
        return this;
    }

    public void setOcc19(Integer occ19) {
        this.occ19 = occ19;
    }

    public Integer getOcc20() {
        return occ20;
    }

    public PopularTime occ20(Integer occ20) {
        this.occ20 = occ20;
        return this;
    }

    public void setOcc20(Integer occ20) {
        this.occ20 = occ20;
    }

    public Integer getOcc21() {
        return occ21;
    }

    public PopularTime occ21(Integer occ21) {
        this.occ21 = occ21;
        return this;
    }

    public void setOcc21(Integer occ21) {
        this.occ21 = occ21;
    }

    public Integer getOcc22() {
        return occ22;
    }

    public PopularTime occ22(Integer occ22) {
        this.occ22 = occ22;
        return this;
    }

    public void setOcc22(Integer occ22) {
        this.occ22 = occ22;
    }

    public Integer getOcc23() {
        return occ23;
    }

    public PopularTime occ23(Integer occ23) {
        this.occ23 = occ23;
        return this;
    }

    public void setOcc23(Integer occ23) {
        this.occ23 = occ23;
    }

    public Integer getOcc24() {
        return occ24;
    }

    public PopularTime occ24(Integer occ24) {
        this.occ24 = occ24;
        return this;
    }

    public void setOcc24(Integer occ24) {
        this.occ24 = occ24;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public PopularTime restaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        return this;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PopularTime)) {
            return false;
        }
        return id != null && id.equals(((PopularTime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PopularTime{" +
            "id=" + getId() +
            ", dayOfWeek='" + getDayOfWeek() + "'" +
            ", occ01=" + getOcc01() +
            ", occ02=" + getOcc02() +
            ", occ03=" + getOcc03() +
            ", occ04=" + getOcc04() +
            ", occ05=" + getOcc05() +
            ", occ06=" + getOcc06() +
            ", occ07=" + getOcc07() +
            ", occ08=" + getOcc08() +
            ", occ09=" + getOcc09() +
            ", occ10=" + getOcc10() +
            ", occ11=" + getOcc11() +
            ", occ12=" + getOcc12() +
            ", occ13=" + getOcc13() +
            ", occ14=" + getOcc14() +
            ", occ15=" + getOcc15() +
            ", occ16=" + getOcc16() +
            ", occ17=" + getOcc17() +
            ", occ18=" + getOcc18() +
            ", occ19=" + getOcc19() +
            ", occ20=" + getOcc20() +
            ", occ21=" + getOcc21() +
            ", occ22=" + getOcc22() +
            ", occ23=" + getOcc23() +
            ", occ24=" + getOcc24() +
            "}";
    }
}
