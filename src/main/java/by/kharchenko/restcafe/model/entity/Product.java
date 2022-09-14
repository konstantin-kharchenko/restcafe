package by.kharchenko.restcafe.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = PRODUCTS)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Product extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PRODUCT_ID)
    private Long productId;

    @Column(name = NAME)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = PRODUCTS_INGREDIENTS, joinColumns = @JoinColumn(name = PRODUCT_ID), inverseJoinColumns = @JoinColumn(name = INGREDIENT_ID))
    private Set<Ingredient> ingredients;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Product product = (Product) o;
        return productId != null && Objects.equals(productId, product.productId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
