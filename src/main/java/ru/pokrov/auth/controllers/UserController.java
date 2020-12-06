package ru.pokrov.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.services.UserService;

class LoginInf {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

@RestController
@RequestMapping({"/api"})
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public User login(@RequestBody LoginInf l) {
        return userService.login(l.getEmail(), l.getPassword());
    }

    @GetMapping("/user")
    public String getUser() {
        return "hello";
    }

}
