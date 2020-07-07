package ua.com.paradine.core.business;

import ua.com.paradine.core.business.vo.WorkingHoursVO;

public class WorkingHoursChecker {

    public boolean isDuringWorkingTime(Integer hour, WorkingHoursVO workingHoursVO) {
        if(workingHoursVO.getClosed()) {
            return false;
        }
        if(workingHoursVO.getClosingHour() < workingHoursVO.getOpeningHour()) {
            return hour <= workingHoursVO.getClosingHour() || hour >= workingHoursVO.getOpeningHour();
        } else {
            return hour <= workingHoursVO.getClosingHour() && hour >= workingHoursVO.getOpeningHour();
        }
    }

}
