package by.kharchenko.restcafe.model.repository;


import by.kharchenko.restcafe.model.entity.Administrator;
import by.kharchenko.restcafe.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AdminRepository extends JpaRepository<Administrator, Long> {

    @Modifying
    @Query("delete from Administrator c where c.user=?1")
    void deleteByUser(User user);
}
