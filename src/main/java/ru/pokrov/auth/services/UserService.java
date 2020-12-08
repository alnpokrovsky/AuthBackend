package ru.pokrov.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.pokrov.auth.daos.UserDao;
import ru.pokrov.auth.entities.User;
import ru.pokrov.auth.entities.UserInfo;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserDao userDao;

    public User signup(User user) {
        User userFromDb = userDao.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        } else {
            user.setActive(true);
            return userDao.save(user);
        }
    }

    public User update(User user, final UserInfo info) {
        user.setUsername(info.getUsername());
        user.setFirstName(info.getFirstName());
        user.setLastName(info.getLastName());
        user.setPassword(info.getPassword());
        return userDao.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.findByUsername(username);
    }
}
