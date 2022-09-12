package by.kharchenko.restcafe.model.repository;


import by.kharchenko.restcafe.model.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Long> {

}
