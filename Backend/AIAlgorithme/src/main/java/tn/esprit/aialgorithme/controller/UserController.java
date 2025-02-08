package tn.esprit.aialgorithme.controller;

import org.springframework.http.ResponseEntity;
import tn.esprit.aialgorithme.model.User;
import tn.esprit.aialgorithme.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.aialgorithme.service.UserService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService; // Must use constructor injection

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User savedUser = userService.createUser(user); // Method name must match
        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/matches/{userId}") // Fix endpoint path
    public List<User> findMatches(@PathVariable Long userId) {
        return userService.findMatches(userId);
    }
}
