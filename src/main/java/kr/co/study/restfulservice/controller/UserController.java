package kr.co.study.restfulservice.controller;

import jakarta.validation.Valid;
import kr.co.study.restfulservice.bean.User;
import kr.co.study.restfulservice.dao.UserDaoService;
import kr.co.study.restfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserDaoService service;


    @GetMapping("/users")
    public List<User> allUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User findUser(@PathVariable int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("ID[%s] not found".formatted(id));
        }

        return user;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser (@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> createUser (@PathVariable int id) {
        User deletedUser = service.deleteById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException("ID[%s] not found".formatted(id));
        }

        return ResponseEntity.noContent().build();
    }

}
