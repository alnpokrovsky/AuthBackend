package ru.pokrov.auth.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=UserInfo.class)
public interface UserInfo {
    Integer getId();

    String getFirstName();

    String getLastName();

    String getUsername();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String getPassword();
}
