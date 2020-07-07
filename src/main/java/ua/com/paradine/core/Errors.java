package ua.com.paradine.core;

public class Errors {

    // only possible with Postman, not possible from normal app usage
    public static final String BAD_GEOLOCATION_PARAMS
        = "paradine.api.errors.bad.geolocation.params";

    // only possible with Postman, not possible from normal app usage
    public static final String VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED
        = "paradine.api.errors.visit.in.non.business.hours";

    // possible from normal app usage
    public static final String TOO_MANY_INTENDED_VISITS = "paradine.api.errors.too.many.visits";

    // possible from normal app usage
    public static final String NOT_FOUND = "paradine.api.errors.not.found";
}
