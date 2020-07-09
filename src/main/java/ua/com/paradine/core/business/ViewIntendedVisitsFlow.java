package ua.com.paradine.core.business;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import ua.com.paradine.core.business.vo.IntendedVisitVO;
import ua.com.paradine.core.dao.ExtendedVisitIntentionRepository;

@Component
public class ViewIntendedVisitsFlow {

    static final String TODAY = "TODAY";
    static final String TOMORROW = "TOMORROW";

    private final ExtendedVisitIntentionRepository visitIntentionRepository;
    private final RestaurantMapperBusiness mapper = Mappers.getMapper(RestaurantMapperBusiness.class);


    public ViewIntendedVisitsFlow(ExtendedVisitIntentionRepository visitIntentionRepository) {
        this.visitIntentionRepository = visitIntentionRepository;
    }

    public List<IntendedVisitVO> viewMyIntendedVisits(String user) {
        return visitIntentionRepository.findActiveVisitsByUser(user)
            .stream()
            .map(mapper::dbEntityToValueObject)
            .map(this::setDayKind)
            .filter(iv -> iv.getKindOfDay() != null)
            .collect(Collectors.toList());
    }

    private IntendedVisitVO setDayKind(IntendedVisitVO intendedVisitVO) {
        OffsetDateTime now = OffsetDateTime.now();
        OffsetDateTime startOfToday = now.truncatedTo(ChronoUnit.DAYS);
        OffsetDateTime endOfToday = startOfToday.plusDays(1);
        OffsetDateTime startOfTomorrow = endOfToday; //weird
        OffsetDateTime endOfTomorrow = startOfTomorrow.plusDays(1);
        if(intendedVisitVO.getVisitTime().isAfter(startOfToday) && intendedVisitVO.getVisitTime().isBefore(endOfToday)
                || intendedVisitVO.getVisitTime().equals(endOfToday)) {
            intendedVisitVO.setKindOfDay(TODAY);
        } else if(intendedVisitVO.getVisitTime().isAfter(startOfTomorrow) &&
            intendedVisitVO.getVisitTime().isBefore(endOfTomorrow)) {
            intendedVisitVO.setKindOfDay(TOMORROW);
        }
        return intendedVisitVO;
    }
}
