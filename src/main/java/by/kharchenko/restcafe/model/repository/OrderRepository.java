package by.kharchenko.restcafe.model.repository;


import by.kharchenko.restcafe.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
