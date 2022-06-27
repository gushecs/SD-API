package sdapi;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "sdapi/user/")
public record UserController(UserService userService) {
}
