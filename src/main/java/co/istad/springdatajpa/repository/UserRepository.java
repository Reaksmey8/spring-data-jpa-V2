package co.istad.springdatajpa.repository;

import co.istad.springdatajpa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);
    // optional : if user does not exist return empty instead of crashing NullPointerException

}
