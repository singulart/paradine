package ua.com.paradine.core.business.vo;

public class WorkingHoursVO {

    private String dayOfWeek;
    private Integer openingHour = 0;
    private Integer closingHour = 24;
    private Boolean closed = Boolean.FALSE;

    public WorkingHoursVO() {}

    public WorkingHoursVO(String dayOfWeek, Integer openingHour, int closingHour) {
        this.dayOfWeek = dayOfWeek;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
        this.closed = Boolean.FALSE;
    }

    public Integer getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Integer closingHour) {
        this.closingHour = closingHour;
    }

    public Integer getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(Integer openingHour) {
        this.openingHour = openingHour;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }
}
