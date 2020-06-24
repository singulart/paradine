package ua.com.paradine.repository;

import ua.com.paradine.domain.PopularTime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PopularTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PopularTimeRepository extends JpaRepository<PopularTime, Long>, JpaSpecificationExecutor<PopularTime> {
}
