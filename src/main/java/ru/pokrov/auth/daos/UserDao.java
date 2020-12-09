package ru.pokrov.auth.daos;

import org.springframework.data.repository.CrudRepository;
import ru.pokrov.auth.entities.User;

/**
 * UserDao provide access to user's entities database
 */
public interface UserDao extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
