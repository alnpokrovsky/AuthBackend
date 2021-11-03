package ru.pokrov.auth.entities;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;


/**
 * User entity that contains:
 * id, username, password, firstName, lastName and birthday.
 * Save it in usr table in database.
 * Implements UserDetails for authorization logic
 * and UserInfo to provide cut JSON serializer
 */
@Data
@Entity
@Table(name = "USER", indexes =
    @Index(name = "uniqueUsername", columnList = "username", unique = true)
)
public class User implements UserDetails, Serializable {

    static final long serialVersionUID = -8582691001098676108L;

    static List<Role> INIT_ROLES = Collections.singletonList(Role.USER);

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotNull
    @Email
    @Size(max=30)
    private String username;

    @NotNull
    @Size(min=1, max=64)
    private String password;

    @Size(max=30)
    private String firstName;

    @Size(max=30)
    private String lastName;

    private LocalDateTime birthday;

    private boolean isActive;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "USER_ID_FK"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(INIT_ROLES);

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

}
