package sdapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Transactional(readOnly = true)
    @Query("SELECT DISTINCT user FROM User user LEFT OUTER JOIN CRM crm ON user=crm.user " +
            "WHERE (:name IS NULL OR user.name = :name) AND " +
            "(:specialty IS NULL OR crm.specialty = :specialty)")
    List<User> findAll (@Param("name") String name,@Param("specialty") String specialty);

}
