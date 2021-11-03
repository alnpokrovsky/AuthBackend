package ru.pokrov.auth.dtos;

import lombok.Value;
import ru.pokrov.auth.entities.User;

import java.io.Serializable;
import java.time.LocalDateTime;

@Value
public class UserInfo implements Serializable {
    Integer id;
    String username;
    String password;
    String firstName;
    String lastName;
    LocalDateTime birthday;

    public static UserInfo of(User user) {
        return new UserInfo(
                user.getId(),
                user.getUsername(),
                null,
                user.getFirstName(),
                user.getLastName(),
                user.getBirthday()
        );
    }
}
