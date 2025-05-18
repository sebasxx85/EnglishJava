package com.example.english.controller;

import com.example.english.model.User;
import com.example.english.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;
    //Constructor no funciono lombok
    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> all() {
        return service.findAll();
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User data) {
        User user = service.create(data);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/{name}")
    public ResponseEntity<User> getByName(@PathVariable String name) {
        return service.findByName(name)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}