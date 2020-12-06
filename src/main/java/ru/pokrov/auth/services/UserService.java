package ru.pokrov.auth.services;

import org.springframework.stereotype.Service;
import ru.pokrov.auth.entities.User;

@Service
public class UserService {
    public User login(String email, String password) {
        return new User(email, password, "a", "p");
    }
}
