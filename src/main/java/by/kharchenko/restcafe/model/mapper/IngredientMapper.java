package by.kharchenko.restcafe.model.mapper;

import by.kharchenko.restcafe.model.dto.IngredientDTO;
import by.kharchenko.restcafe.model.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface IngredientMapper {
    IngredientMapper INSTANCE = Mappers.getMapper(IngredientMapper.class);

    IngredientDTO ingredientToIngredientDTO(Ingredient ingredient);

    Ingredient ingredientDTOToIngredient(IngredientDTO ingredientDTO);
}
