package by.kharchenko.restcafe.model.entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = ADMINISTRATORS)
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
public class Administrator extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ADMINISTRATOR_ID)
    private Long idAdministrator;

    @OneToOne
    @JoinColumn(name = USER_ID, referencedColumnName = USER_ID)
    private User user;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = STATUS_ID)
    private Status status = Status.WAITING;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Administrator that = (Administrator) o;
        return idAdministrator != null && Objects.equals(idAdministrator, that.idAdministrator);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
