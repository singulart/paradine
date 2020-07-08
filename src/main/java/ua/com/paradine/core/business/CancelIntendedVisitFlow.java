package ua.com.paradine.core.business;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import ua.com.paradine.core.business.vo.commands.CancelIntendedVisitCommand;
import ua.com.paradine.core.business.vo.outcomes.CancelIntendedVisitOutcome;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;

@Component
public class CancelIntendedVisitFlow {

    private final ExtendedVisitIntentionRepository visitIntentionRepository;

    public CancelIntendedVisitFlow(ExtendedVisitIntentionRepository visitIntentionRepository) {
        this.visitIntentionRepository = visitIntentionRepository;
    }

    @Transactional
    public CancelIntendedVisitOutcome cancelVisit(CancelIntendedVisitCommand command) {
        if(visitIntentionRepository.cancelVisit(command.getVisit(), command.getUserLogin()) > 0) {
            return new CancelIntendedVisitOutcome(Boolean.TRUE);
        } else {
            return new CancelIntendedVisitOutcome(Problem.valueOf(Status.NOT_FOUND));
        }
    }
}
