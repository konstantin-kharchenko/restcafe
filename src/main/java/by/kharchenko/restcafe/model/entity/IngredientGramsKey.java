package by.kharchenko.restcafe.model.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientGramsKey implements Serializable {

    @Column(name = PRODUCT_ID)
   private Long productId;

    @Column(name = INGREDIENT_ID)
   private Long ingredientId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IngredientGramsKey that = (IngredientGramsKey) o;
        return Objects.equals(productId, that.productId) && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, ingredientId);
    }
}
