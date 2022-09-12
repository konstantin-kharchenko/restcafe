package by.kharchenko.restcafe.model.repository;


import by.kharchenko.restcafe.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
