package sdapi;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class CreateDatabase implements CommandLineRunner {

    @Autowired
    Environment env;

    @Override
    public void run(String... args) {
        Flyway.configure().dataSource(env.getProperty("spring.datasource.url"),env.getProperty("spring.datasource.username"),env.getProperty("spring.datasource.password")).load().migrate();
    }
}
