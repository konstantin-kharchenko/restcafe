package by.kharchenko.restcafe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = PRODUCTS_INGREDIENTS)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IngredientGrams {

    @EmbeddedId
    IngredientGramsKey ingredientGramsKey;

    @ManyToOne
    @MapsId("ingredientId")
    @JoinColumn(name = INGREDIENT_ID)
    private Ingredient ingredient;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = PRODUCT_ID)
    private Product product;

    @Column(name = GRAMS)
    private double grams;
}
