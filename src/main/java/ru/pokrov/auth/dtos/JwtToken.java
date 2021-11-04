package ru.pokrov.auth.dtos;

import lombok.Value;

import java.io.Serializable;
import java.util.Date;

@Value
public class JwtToken implements Serializable {
    String token;
    long lifetimeSec;
    Date expireTime;
}
