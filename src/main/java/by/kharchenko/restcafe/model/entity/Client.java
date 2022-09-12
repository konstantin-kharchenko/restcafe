package by.kharchenko.restcafe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = CLIENTS)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Client extends AbstractEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = CLIENT_ID)
    private Long clientId;

    @Column(name = LOYALTY_POINTS)
    private int loyaltyPoints;

    @OneToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = USER_ID, referencedColumnName = USER_ID)
    protected User user;

    @OneToMany(mappedBy = CLIENT, fetch = FetchType.LAZY)
    private Set<Order> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return clientId != null && Objects.equals(clientId, client.clientId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "clientId = " + clientId + ", " +
                "loyaltyPoints = " + loyaltyPoints + ", " +
                "user = " + user + ")";
    }
}
