package ru.pokrov.auth.utils;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AuthRequest implements Serializable {

    private static final long serialVersionUID = 5926468583005150707L;

    @NotNull
    @Email
    private String username;
    @NotNull
    private String password;

    //need default constructor for JSON Parsing
    public AuthRequest() { }

    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
