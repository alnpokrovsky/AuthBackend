package ru.pokrov.auth.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pokrov.auth.daos.UserDao;
import ru.pokrov.auth.entities.User;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public User signup(User user) {
        User userFromDb = userDao.findByUsername(user.getUsername());
        if (userFromDb != null) {
            return null;
        } else {
            user.setActive(true);
            return user;
        }
    }

    public User login(String email, String password) {
        return new User(email, password, "a", "p");
    }
}
