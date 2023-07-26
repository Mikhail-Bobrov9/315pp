package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;


import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/restAdmin")
public class
RestAdminController {

    private final UserService userService;

    @Autowired
    public RestAdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<User>> restShowAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<User> restAddUser(@RequestBody User user) {
        String username = user.getUsername();
        if (!userService.isUsernameExists(username)) {
            userService.save(user);
        }
        return ResponseEntity.ok(user);

    }

    @PatchMapping()
    public ResponseEntity<User> restUpdateUser(@RequestBody User user) {
        String username = user.getUsername();
        User existingUser = userService.findByUsername(username);
        if (existingUser == null || existingUser.getId().equals(user.getId())) {
            userService.update(user);

        }
        return ResponseEntity.ok(user);

    }


    @DeleteMapping()
    public ResponseEntity<User> restDelete(@RequestBody User user, Principal principal) {
        if (!(principal.getName().equals(user.getUsername()))) {
            userService.deleteUser(user.getId());
        }
        return ResponseEntity.ok(user);
    }
}
