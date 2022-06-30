package sdapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findAllInAllScenarios() {
        //Test all findAll methods considering FlyWay running only V1__configure_database.sql.
        //In other contexts the method may need update.

        int expectedAllSize = 5;
        int expectedSpecialtySize = 2;
        int expectedNameSize = 3;
        int expectedNameAndSpecialtySize = 1;

        List<User> all = userRepository.findAll((String) null, null);
        List<User> specialty = userRepository.findAll(null, "Cardiologia");
        List<User> name = userRepository.findAll("Usuario", null);
        List<User> nameAndSpecialty= userRepository.findAll("Usuario", "Cardiologia");

        boolean allSizesMatch = true;

        if (all.size() != expectedAllSize)
            allSizesMatch = false;
        else if (specialty.size() != expectedSpecialtySize)
            allSizesMatch = false;
        else if (name.size() != expectedNameSize)
            allSizesMatch = false;
        else if (nameAndSpecialty.size() != expectedNameAndSpecialtySize)
            allSizesMatch = false;

        assertTrue(allSizesMatch);

    }

}