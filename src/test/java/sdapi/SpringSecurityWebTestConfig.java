package sdapi;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import java.util.List;

@TestConfiguration
public class SpringSecurityWebTestConfig {

    @Bean
    @Primary
    public UserDetailsService userDetailsService() {

        User admin = new User("admin@test.com", //User email
                "password", //User password
                true, //Enabled
                true, //Account not expired
                true, //Credentials not expired
                true, //Account not locked
                List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));

        User user = new User("user@test.com", //User email
                "password", //User password
                true, //Enabled
                true, //Account not expired
                true, //Credentials not expired
                true, //Account not locked
                List.of(new SimpleGrantedAuthority("ROLE_USER")));

        return new InMemoryUserDetailsManager(List.of(admin, user));
    }
}
