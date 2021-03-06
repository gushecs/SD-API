package sdapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sdapi.entities.UserRQ;
import sdapi.entities.UserRS;
import sdapi.services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/sdapi/users")
public record UserController(UserService userService) {

    @GetMapping
    public ResponseEntity<List<UserRS>> findAll(@RequestParam(value="name", required = false) String name,
                                                @RequestParam(value="specialty", required = false) String specialty){
        return ResponseEntity.ok(userService.findAll(name, specialty));}

    @GetMapping(path = "/{id}")
    public ResponseEntity<UserRS> findById(@PathVariable Integer id) {return ResponseEntity.ok(userService.findById(id));}

    @PostMapping
    public ResponseEntity<UserRS> register(@Valid @RequestBody UserRQ userRQ) {return ResponseEntity.ok(userService.signUp(userRQ));}

    @DeleteMapping(path="/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {userService.delete(id);
    return ResponseEntity.ok("Usuário "+id.toString()+" deletado com sucesso!");}

    @PutMapping(path = "/{id}")
    public ResponseEntity<UserRS> update (@PathVariable Integer id, @Valid @RequestBody UserRQ userRQ) {
        return ResponseEntity.ok(userService.update(id, userRQ));}

}
