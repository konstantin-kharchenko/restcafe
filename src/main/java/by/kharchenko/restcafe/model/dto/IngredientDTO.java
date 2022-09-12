package by.kharchenko.restcafe.model.dto;

import by.kharchenko.restcafe.model.entity.IngredientGrams;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Set;

@Data
@NoArgsConstructor
public class IngredientDTO {
    @JsonProperty("idIngredient")
    private BigInteger idIngredient;

    @JsonProperty("name")
    private String name;

    @JsonProperty("grams")
    Set<IngredientGrams> grams;
}
