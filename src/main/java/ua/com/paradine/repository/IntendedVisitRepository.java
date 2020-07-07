package ua.com.paradine.repository;

import ua.com.paradine.domain.IntendedVisit;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the IntendedVisit entity.
 */
@SuppressWarnings("unused")
@Repository
public interface IntendedVisitRepository extends JpaRepository<IntendedVisit, Long>, JpaSpecificationExecutor<IntendedVisit> {
}
