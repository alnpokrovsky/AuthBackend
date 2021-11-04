package ru.pokrov.auth.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;
import ru.pokrov.auth.dtos.AuthRequest;
import ru.pokrov.auth.dtos.JwtToken;
import ru.pokrov.auth.services.AuthenticationService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping({"/api"})
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {
    public static final String ACCESS_TOKEN_COOKIE = "access_token";

    @Autowired private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            @Valid @RequestBody AuthRequest authRequest,
            HttpServletResponse response
    ) throws AuthenticationException {
        JwtToken token = authenticationService.login(
                authRequest.getUsername(),
                authRequest.getPassword()
        );

        // Add jwt as a cookie
        Cookie jwtCookie = new Cookie(ACCESS_TOKEN_COOKIE, token.getToken());
        jwtCookie.setMaxAge((int) token.getLifetimeSec());
        //Cookie cannot be accessed via JavaScript
        jwtCookie.setHttpOnly(true);
        response.addCookie(jwtCookie);

        return ResponseEntity.ok("");
    }

}
