package sdapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CRMRepository extends JpaRepository<CRM, Integer> {

    Optional<CRM> findByCrmAndUf(String crm, String uf);

}
