package ua.com.paradine.core.business.vo.outcomes;

import org.zalando.problem.ThrowableProblem;

public class CancelIntendedVisitOutcome {

    private Boolean cancelled;
    private ThrowableProblem error;

    public CancelIntendedVisitOutcome(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public CancelIntendedVisitOutcome(ThrowableProblem error) {
        this.error = error;
        this.cancelled = Boolean.FALSE;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public ThrowableProblem getError() {
        return error;
    }
}
