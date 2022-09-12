package by.kharchenko.restcafe.model.repository;

import by.kharchenko.restcafe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigInteger;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u.userId from User u where u.login = ?1")
    Optional<Long> findIdByLogin(String login);

    @Query("SELECT u.userId from User u WHERE u.login = ?1 AND u.userId <> ?2")
    Optional<Long> findAnotherIdByLogin(String login, Long userId);

    @Query("select u from User u where u.login=?1")
    User getByLogin(String login);
}
