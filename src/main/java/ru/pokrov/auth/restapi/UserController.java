package ru.pokrov.auth.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.pokrov.auth.dtos.UserInfo;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.services.UserService;

import javax.validation.Valid;


@RestController
@RequestMapping({"/api"})
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserInfo> signup(@Valid @RequestBody UserInfo userInfo) {
        return ResponseEntity.ok(userService.signup(userInfo));
    }

    @GetMapping("/user")
    public UserInfo getUser(@AuthenticationPrincipal User user) {
        return UserInfo.of(user);
    }

    @PutMapping("/user")
    public ResponseEntity<UserInfo> putUser(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody UserInfo userInfo
    ) {
        return ResponseEntity.ok(userService.update(user, userInfo));
    }

}
