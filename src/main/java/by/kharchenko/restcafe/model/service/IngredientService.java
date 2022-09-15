package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.ingredient.CreateIngredientDTO;
import by.kharchenko.restcafe.model.dto.ingredient.IngredientDTO;
import by.kharchenko.restcafe.model.dto.ingredient.UpdateIngredientDTO;
import by.kharchenko.restcafe.model.entity.Ingredient;

import java.util.List;
import java.util.Optional;

public interface IngredientService {
    boolean delete(Long id) throws ServiceException;

    boolean add(CreateIngredientDTO ingredientDTO) throws ServiceException;

    Optional<IngredientDTO> findById(Long id) throws ServiceException;

    List<IngredientDTO> findAll() throws ServiceException;

    boolean update(UpdateIngredientDTO t) throws ServiceException;

    Long count() throws ServiceException;
}
