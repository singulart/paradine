package ua.com.paradine.core.business;

import java.util.Set;

public class ClassifiedRestaurantVO extends RestaurantVO {

    private Set<HourlyClassifier> classifiersToday;
    private Set<HourlyClassifier> classifiersTomorrow;

    public Set<HourlyClassifier> getClassifiersToday() {
        return classifiersToday;
    }

    public void setClassifiersToday(Set<HourlyClassifier> classifiersToday) {
        this.classifiersToday = classifiersToday;
    }

    public Set<HourlyClassifier> getClassifiersTomorrow() {
        return classifiersTomorrow;
    }

    public void setClassifiersTomorrow(Set<HourlyClassifier> classifiersTomorrow) {
        this.classifiersTomorrow = classifiersTomorrow;
    }
}
