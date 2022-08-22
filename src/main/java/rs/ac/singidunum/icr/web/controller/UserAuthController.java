package rs.ac.singidunum.icr.web.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rs.ac.singidunum.icr.persistence.dao.UserRepository;
import rs.ac.singidunum.icr.persistence.model.User;
import rs.ac.singidunum.icr.service.UserService;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserAuthController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    public UserAuthController(UserRepository userRepository) {
        this.setUserRepository(userRepository);
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable int id) {
        return userRepository.findByIdEquals(id);
    }

    @GetMapping("/users/{email}/username")
    public User getUserByUsername(@PathVariable String email) {
        return userRepository.findByEmailAndStatusIdEquals(email, 1);
    }

    @GetMapping("users/{id}/full_name")
    public String getUserFullName(@PathVariable int id) {
        return userRepository.findByIdEquals(id).getFirstName() + ' ' + userRepository.findByIdEquals(id).getLastName();
    }

    @PostMapping("/users/login")
    public User loginUser(@RequestBody ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        String password = objectNode.get("password").asText();
        return userService.loginUser(username, password);
    }

    @PostMapping("/users/register")
    public User registerUser(@RequestBody ObjectNode objectNode) {
        String username = objectNode.get("username").asText();
        String password = objectNode.get("password").asText();
        String firstName = objectNode.get("firstName").asText();
        String lastName = objectNode.get("lastName").asText();
        return userService.registerUser(username, password, firstName,lastName);
    }
}
