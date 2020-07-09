package ua.com.paradine.core.business.vo.outcomes;

import java.util.UUID;
import org.zalando.problem.ThrowableProblem;

public class SubmitVisitIntentOutcome {

    private UUID uuid;
    private ThrowableProblem error;

    public SubmitVisitIntentOutcome(ThrowableProblem error) {
        this.error = error;
    }

    public SubmitVisitIntentOutcome(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public ThrowableProblem getError() {
        return error;
    }

}
