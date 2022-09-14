package by.kharchenko.restcafe.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = ORDERS)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Order extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ORDER_ID)
    private Long orderId;

    @Column(name = NAME)
    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = ORDERS_PRODUCTS, joinColumns = @JoinColumn(name = ORDER_ID), inverseJoinColumns = @JoinColumn(name = PRODUCT_ID))
    private Set<Product> products;

    @ManyToOne
    @JoinColumn(name = CLIENT_ID)
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Order order = (Order) o;
        return orderId != null && Objects.equals(orderId, order.orderId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
