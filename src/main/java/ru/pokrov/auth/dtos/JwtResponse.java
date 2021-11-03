package ru.pokrov.auth.dtos;

import lombok.Value;

import java.io.Serializable;

@Value
public class JwtResponse implements Serializable {
    String token;
}
