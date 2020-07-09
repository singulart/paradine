package ua.com.paradine.core.dao;

import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.paradine.domain.IntendedVisit;
import ua.com.paradine.repository.IntendedVisitRepository;

public interface ExtendedVisitIntentionRepository extends IntendedVisitRepository {

    @Query(value = "FROM IntendedVisit iv "
        + "INNER JOIN FETCH iv.restaurant "
        + "WHERE iv.visitingUser.id = (SELECT u.id from User u WHERE u.login = :userLogin)"
        + " AND iv.cancelled = FALSE ")
    List<IntendedVisit> findActiveVisitsByUser(@Param("userLogin")String userLogin);

    @Query(value = "FROM IntendedVisit iv WHERE iv.visitingUser.id = :userId "
        + "AND iv.cancelled = FALSE "
        + "AND iv.visitStartDate BETWEEN :startDate AND :endDate")
    List<IntendedVisit> findActiveVisitsByUserAndDay(
                        @Param("userId") Long userId,
                        @Param("startDate") ZonedDateTime startDate,
                        @Param("endDate") ZonedDateTime endDate
    );

    @Query(value = "UPDATE IntendedVisit SET cancelled = TRUE "
        + "WHERE visitingUser.id = (SELECT u.id from User u WHERE u.login = :userLogin) "
        + "AND uuid = :uuid ")
    @Modifying
    Integer cancelVisit(@Param("uuid") String uuid, @Param("userLogin") String userLogin);
}
