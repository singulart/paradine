package ua.com.paradine.core.business;

import org.springframework.stereotype.Component;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;

@Component
public class ViewIntendedVisitsFlow {

    private final ExtendedVisitIntentionRepository visitIntentionRepository;

    public ViewIntendedVisitsFlow(ExtendedVisitIntentionRepository visitIntentionRepository) {
        this.visitIntentionRepository = visitIntentionRepository;
    }

//    public List<> viewMyIntendedVisits(String user) {
//
//    }
}
