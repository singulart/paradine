package ua.com.paradine.core.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.paradine.core.business.vo.commands.SubmitVisitIntentCommand;
import ua.com.paradine.core.business.vo.outcomes.SubmitVisitIntentOutcome;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;
import ua.com.paradine.domain.IntendedVisit;

@Service
@Transactional
public class SubmitVisitIntentFlow {

    private final ExtendedVisitIntentionRepository visitIntentionRepository;

    @Autowired
    public SubmitVisitIntentFlow(ExtendedVisitIntentionRepository visitIntentionRepository) {
        this.visitIntentionRepository = visitIntentionRepository;
    }

    public SubmitVisitIntentOutcome submitVisitIntent(SubmitVisitIntentCommand command) {
        IntendedVisit visit = new IntendedVisit();
//        visit.se
        return new SubmitVisitIntentOutcome("");
    }
}
