package by.kharchenko.restcafe.model.repository;


import by.kharchenko.restcafe.model.entity.Client;
import by.kharchenko.restcafe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    @Modifying
    @Query("delete from Client c where c.user=?1")
    void deleteByUser(User user);

    @Query("select c from Client c where c.user=?1")
    Optional<Client> findByUser(User user);
}
