package ru.pokrov.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.entities.UserInfo;
import ru.pokrov.auth.services.UserService;
import ru.pokrov.auth.utils.AuthRequest;
import ru.pokrov.auth.utils.JwtResponse;


@RestController
@RequestMapping({"/api"})
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody AuthRequest authRequest) throws AuthenticationException {
        return new JwtResponse(
                userService.login(authRequest.getUsername(), authRequest.getPassword())
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        user = userService.signup(user);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.GONE).body(null);
        }
    }

    @GetMapping("/user")
    public UserInfo getUser(@AuthenticationPrincipal User user) {
        return user;
    }

    @PutMapping("/user")
    public UserInfo putUser(@AuthenticationPrincipal User user, @RequestBody User info) {
        return userService.update(user, info);
    }

}
