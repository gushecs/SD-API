package sdapi;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findAllByNameAndCrms_Specialty (String name, String specialty);

    List<User> findAllByCrms_Specialty (String specialty);

}
