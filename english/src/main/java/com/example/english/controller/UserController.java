package com.example.english.controller;

import com.example.english.dto.UserDto;
import com.example.english.model.User;
import com.example.english.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserDto> all() {
        return service.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<UserDto> getByName(@PathVariable String name) {
        return service.findByName(name)
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getByEmail(@PathVariable String email) {
        return service.findByEmail(email)
                .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping
    public ResponseEntity<UserDto> create(@RequestBody User user) {
        UserDto created = service.create(user);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }
}