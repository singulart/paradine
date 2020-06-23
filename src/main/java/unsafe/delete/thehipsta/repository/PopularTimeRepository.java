package unsafe.delete.thehipsta.repository;

import unsafe.delete.thehipsta.domain.PopularTime;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PopularTime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PopularTimeRepository extends JpaRepository<PopularTime, Long>, JpaSpecificationExecutor<PopularTime> {
}
