package by.kharchenko.restcafe.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

import static by.kharchenko.restcafe.controller.DbColumn.*;

@Entity
@Table(name = USERS)
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = USER_ID)
    private Long userId;

    @Column(name = LOGIN)
    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    private String login;

    @Column(name = PASSWORD)
    @NotEmpty(message = "password must not be empty")
    @Pattern(regexp = ".*[A-Za-z.-_*].*", message = "Invalid")
    private String password;

    @Column(name = EMAIL)
    @NotEmpty(message = "email must not be empty")
    @Email(message = "email must be valid")
    private String email;

    @ManyToMany
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = USER_ID), inverseJoinColumns = @JoinColumn(name = ROLE_ID))
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return userId != null && Objects.equals(userId, user.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "userId = " + userId + ", " +
                "login = " + login + ", " +
                "password = " + password + ", " +
                "email = " + email + ")";
    }
}
