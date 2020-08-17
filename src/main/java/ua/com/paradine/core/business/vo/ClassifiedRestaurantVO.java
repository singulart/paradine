package ua.com.paradine.core.business.vo;

import java.util.List;

public class ClassifiedRestaurantVO extends RestaurantVO {

    private List<HourlyClassifier> classifiersToday;
    private List<HourlyClassifier> classifiersTomorrow;

    public List<HourlyClassifier> getClassifiersToday() {
        return classifiersToday;
    }

    public void setClassifiersToday(List<HourlyClassifier> classifiersToday) {
        this.classifiersToday = classifiersToday;
    }

    public List<HourlyClassifier> getClassifiersTomorrow() {
        return classifiersTomorrow;
    }

    public void setClassifiersTomorrow(List<HourlyClassifier> classifiersTomorrow) {
        this.classifiersTomorrow = classifiersTomorrow;
    }
}
