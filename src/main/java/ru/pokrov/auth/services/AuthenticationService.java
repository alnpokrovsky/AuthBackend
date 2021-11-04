package ru.pokrov.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.pokrov.auth.components.JwtTokenUtil;
import ru.pokrov.auth.dtos.JwtToken;

@Service
public class AuthenticationService {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtTokenUtil jwtTokenUtil;

    /**
     * Generate auth token if user is valid
     * @param username username
     * @param password password
     * @return JWT authentication token
     * @throws AuthenticationException if user is not valid
     */
    public JwtToken login(String username, String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return jwtTokenUtil.generateToken(username);
    }
}
