package sdapi;

import org.springframework.data.domain.Example;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(path = "/sdapi/users")
public record UserController(UserService userService) {

    @GetMapping
    public ResponseEntity<List<User>> findAll(@RequestParam(value="name", required = false) String name,
                                              @RequestParam(value="specialty", required = false) String specialty){
        return ResponseEntity.ok(userService.findAll(name, specialty));}

    @GetMapping(path = "/{id}")
    public ResponseEntity<User> findById(@PathVariable Integer id) {return ResponseEntity.ok(userService.findById(id));}

    @PostMapping
    public ResponseEntity<User> signUp(@RequestBody User user) {return ResponseEntity.ok(userService.signUp(user));}

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {userService.delete(id);
    return ResponseEntity.ok("User "+id.toString()+" successfully deleted!");}

    @PutMapping(path = "/{id}")
    public ResponseEntity<User> update (@PathVariable Integer id, @RequestBody User user) {
        return ResponseEntity.ok(userService.update(id, user));}

}
