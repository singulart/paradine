package ua.com.paradine.core.dao;

import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ua.com.paradine.repository.UserRepository;

public interface ExtendedUserRepository extends UserRepository {

    @Query(value =
        "SELECT u.id FROM User u where u.login = :login"
    )
    Optional<Long> findIdByLogin(@Param("login") String login);

}
