package ru.gb.pugacheva.webapp.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.gb.pugacheva.webapp.core.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {


    Optional<User> findByUsername(String username);
}
