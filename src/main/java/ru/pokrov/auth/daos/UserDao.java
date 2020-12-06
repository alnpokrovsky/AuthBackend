package ru.pokrov.auth.daos;

import org.springframework.data.repository.CrudRepository;
import ru.pokrov.auth.entities.User;

public interface UserDao extends CrudRepository<User, Integer> {

}
