package ru.pokrov.auth.dtos;

import lombok.Value;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Value
public class AuthRequest implements Serializable {

    @NotNull
    @Email
    String username;

    @NotNull
    String password;
}
