package by.kharchenko.restcafe.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = INGREDIENTS)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Ingredient extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = INGREDIENT_ID)
    private Long ingredientId;

    @Column(name = NAME)
    private String name;

    @OneToMany(mappedBy = "ingredient")
    Set<IngredientGrams> grams;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Ingredient that = (Ingredient) o;
        return ingredientId != null && Objects.equals(ingredientId, that.ingredientId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "ingredientId = " + ingredientId + ", " +
                "name = " + name + ")";
    }
}
