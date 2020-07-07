package ua.com.paradine.core.business.vo.outcomes;

import org.zalando.problem.Problem;
import org.zalando.problem.ThrowableProblem;

public class SubmitVisitIntentOutcome {

    private String uuid;
    private ThrowableProblem error;

    public SubmitVisitIntentOutcome(ThrowableProblem error) {
        this.error = error;
    }

    public SubmitVisitIntentOutcome(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public ThrowableProblem getError() {
        return error;
    }

}
