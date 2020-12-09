package ru.pokrov.auth.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.time.LocalDateTime;

/**
 * User that can be serialized.
 * Password can be only write, we don't show it on GET
 * Date is angular formatted
 */
@JsonSerialize(as=UserInfo.class)
public interface UserInfo {
    Integer getId();

    String getFirstName();

    String getLastName();

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-dd'T'HH:mm:ss.SSS'Z'")
    LocalDateTime getBirthday();

    String getUsername();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String getPassword();
}
