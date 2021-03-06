package ru.pokrov.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.pokrov.auth.daos.UserDao;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.entities.UserInfo;
import ru.pokrov.auth.utils.JwtTokenUtil;

/**
 * UserService provide user's manipulations
 */
@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Generate auth token if user is valid
     * @param username
     * @param password
     * @return JWT authentication token
     * @throws AuthenticationException if user is not valid
     */
    public String login(final String username, final String password) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        final UserDetails userDetails = loadUserByUsername(username);
        return jwtTokenUtil.generateToken(userDetails);
    }

    /**
     * Register new user and add them to database
     * @param user user to be registered
     * @return this user with autogenerated id or null if exists
     */
    public User signup(User user) {
        User userFromDb = userDao.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        } else {
            user.setActive(true);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            return userDao.save(user);
        }
    }

    /**
     * Update user information
     * @param user user to be changed
     * @param info new user information
     * @return changed user
     */
    public User update(User user, final UserInfo info) {
        user.setFirstName(info.getFirstName());
        user.setLastName(info.getLastName());
        user.setBirthday(info.getBirthday());
        user.setUsername(info.getUsername());
        if (!info.getPassword().isEmpty())
            user.setPassword(passwordEncoder.encode(info.getPassword()));
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}
