package sdapi;

import org.springframework.stereotype.Service;

@Service
public record UserService (UserRepository userRepository){
}
