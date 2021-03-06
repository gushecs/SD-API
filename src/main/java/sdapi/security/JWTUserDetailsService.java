package sdapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sdapi.controllers.exceptions.ObjectNotFoundException;
import sdapi.entities.User;
import sdapi.services.UserService;

@Service
public class JWTUserDetailsService implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            User userToAuth = userService.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    userToAuth.getEmail(), //User email
                    userToAuth.getPassword(), //User password
                    true, //Enabled
                    true, //Account not expired
                    true, //Credentials not expired
                    true, //Account not locked
                    new JWTAuthentication(userToAuth).getAuthorities()); //User role
        } catch (Exception e) {
            throw new ObjectNotFoundException("Email não cadastrado!");
        }
    }
}
