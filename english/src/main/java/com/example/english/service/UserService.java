package com.example.english.service;

import com.example.english.dto.UserDto;
import com.example.english.model.User;
import com.example.english.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    public UserService(UserRepository repository, ModelMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDto> findAll() {
        return repository.findAll()
                .stream()
                .map(user -> mapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    public Optional<UserDto> findByName(String name) {
        return repository.findByName(name)
                .map(user -> mapper.map(user, UserDto.class));
    }
    //Los usuarios no tienen email por ahora
    public Optional<UserDto> findByEmail(String email) {
        return repository.findByEmail(email)
                .map(user -> mapper.map(user, UserDto.class));
    }

    public UserDto create(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setAvatar("https://robohash.org/" + user.getName() + ".png");
        user.setScore(0);
        user.setLanguage("English");
        User saved = repository.save(user);
        return mapper.map(saved, UserDto.class);
    }
}