package by.kharchenko.restcafe.model.dto.product;


import by.kharchenko.restcafe.model.dto.ingredient.IngredientDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Set;

@Data
@NoArgsConstructor
public class ProductDTO {
    @JsonProperty("idProduct")
    private Long productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("ingredients")
    private Set<IngredientDTO> ingredients;
}
