package ru.pokrov.auth.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.pokrov.auth.dtos.UserInfo;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.services.UserService;
import ru.pokrov.auth.dtos.AuthRequest;
import ru.pokrov.auth.dtos.JwtResponse;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping({"/api"})
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(
            @Valid @RequestBody AuthRequest authRequest
    ) throws AuthenticationException {
        return ResponseEntity.ok(new JwtResponse(
                userService.login(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                )
        ));
    }

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



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

}
