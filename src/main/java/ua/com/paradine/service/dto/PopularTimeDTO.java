package ua.com.paradine.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * A DTO for the {@link ua.com.paradine.domain.PopularTime} entity.
 */
public class PopularTimeDTO implements Serializable {
    
    private Long id;

    @NotNull
    @Size(min = 2, max = 2)
    private String dayOfWeek;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ01;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ02;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ03;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ04;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ05;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ06;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ07;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ08;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ09;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ10;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ11;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ12;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ13;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ14;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ15;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ16;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ17;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ18;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ19;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ20;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ21;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ22;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ23;

    @NotNull
    @Min(value = 0)
    @Max(value = 100)
    private Integer occ24;


    private Long restaurantId;

    private String restaurantName;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Integer getOcc01() {
        return occ01;
    }

    public void setOcc01(Integer occ01) {
        this.occ01 = occ01;
    }

    public Integer getOcc02() {
        return occ02;
    }

    public void setOcc02(Integer occ02) {
        this.occ02 = occ02;
    }

    public Integer getOcc03() {
        return occ03;
    }

    public void setOcc03(Integer occ03) {
        this.occ03 = occ03;
    }

    public Integer getOcc04() {
        return occ04;
    }

    public void setOcc04(Integer occ04) {
        this.occ04 = occ04;
    }

    public Integer getOcc05() {
        return occ05;
    }

    public void setOcc05(Integer occ05) {
        this.occ05 = occ05;
    }

    public Integer getOcc06() {
        return occ06;
    }

    public void setOcc06(Integer occ06) {
        this.occ06 = occ06;
    }

    public Integer getOcc07() {
        return occ07;
    }

    public void setOcc07(Integer occ07) {
        this.occ07 = occ07;
    }

    public Integer getOcc08() {
        return occ08;
    }

    public void setOcc08(Integer occ08) {
        this.occ08 = occ08;
    }

    public Integer getOcc09() {
        return occ09;
    }

    public void setOcc09(Integer occ09) {
        this.occ09 = occ09;
    }

    public Integer getOcc10() {
        return occ10;
    }

    public void setOcc10(Integer occ10) {
        this.occ10 = occ10;
    }

    public Integer getOcc11() {
        return occ11;
    }

    public void setOcc11(Integer occ11) {
        this.occ11 = occ11;
    }

    public Integer getOcc12() {
        return occ12;
    }

    public void setOcc12(Integer occ12) {
        this.occ12 = occ12;
    }

    public Integer getOcc13() {
        return occ13;
    }

    public void setOcc13(Integer occ13) {
        this.occ13 = occ13;
    }

    public Integer getOcc14() {
        return occ14;
    }

    public void setOcc14(Integer occ14) {
        this.occ14 = occ14;
    }

    public Integer getOcc15() {
        return occ15;
    }

    public void setOcc15(Integer occ15) {
        this.occ15 = occ15;
    }

    public Integer getOcc16() {
        return occ16;
    }

    public void setOcc16(Integer occ16) {
        this.occ16 = occ16;
    }

    public Integer getOcc17() {
        return occ17;
    }

    public void setOcc17(Integer occ17) {
        this.occ17 = occ17;
    }

    public Integer getOcc18() {
        return occ18;
    }

    public void setOcc18(Integer occ18) {
        this.occ18 = occ18;
    }

    public Integer getOcc19() {
        return occ19;
    }

    public void setOcc19(Integer occ19) {
        this.occ19 = occ19;
    }

    public Integer getOcc20() {
        return occ20;
    }

    public void setOcc20(Integer occ20) {
        this.occ20 = occ20;
    }

    public Integer getOcc21() {
        return occ21;
    }

    public void setOcc21(Integer occ21) {
        this.occ21 = occ21;
    }

    public Integer getOcc22() {
        return occ22;
    }

    public void setOcc22(Integer occ22) {
        this.occ22 = occ22;
    }

    public Integer getOcc23() {
        return occ23;
    }

    public void setOcc23(Integer occ23) {
        this.occ23 = occ23;
    }

    public Integer getOcc24() {
        return occ24;
    }

    public void setOcc24(Integer occ24) {
        this.occ24 = occ24;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PopularTimeDTO)) {
            return false;
        }

        return id != null && id.equals(((PopularTimeDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PopularTimeDTO{" +
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
            ", restaurantId=" + getRestaurantId() +
            ", restaurantName='" + getRestaurantName() + "'" +
            "}";
    }
}
