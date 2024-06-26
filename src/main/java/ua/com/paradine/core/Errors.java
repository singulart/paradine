package ua.com.paradine.core;

public class Errors {

    // only possible with Postman, not possible from normal app usage
    public static final String BAD_GEOLOCATION_PARAMS
        = "paradine.api.errors.bad.geolocation.params";

    // only possible with Postman, not possible from normal app usage
    public static final String VISIT_IN_NON_BUSINESS_HOURS_NOT_ALLOWED
        = "paradine.api.errors.visit.in.non.business.hours";

    // only possible with Postman, not possible from normal app usage
    public static final String VISIT_DATE_OUT_OF_RANGE
        = "paradine.api.errors.visit.out.of.range";

    // possible from normal app usage
    public static final String TOO_MANY_INTENDED_VISITS = "paradine.api.errors.too.many.visits";

    // possible from normal app usage
    public static final String TOO_CLOSE_TO_EXISTING_VISIT = "paradine.api.errors.too.close.to.existing.visit";

    // possible from normal app usage
    public static final String NOT_FOUND = "paradine.api.errors.not.found";
}
