package com.example.userserver.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserInfo> signUpUser(@RequestBody UserRequest userRequest) {
        UserInfo user;
        try {
            user = userService.createUser(userRequest);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInfo> getUserInfo(@PathVariable("id") int id) {
        UserInfo user;
        try {
            user = userService.getUser(id);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<UserInfo> getUserInfoByName(@PathVariable("name") String name) {
        UserInfo user;
        try {
            user = userService.getUserByName(name);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<UserInfo> signIn(@RequestBody UserRequest signInRequest) {
        if (signInRequest.getUsername() == null) {
            return ResponseEntity.badRequest().build();
        }

        UserInfo userInfo;
        try {
            userInfo = userService.signIn(signInRequest);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(userInfo);
    }
}
