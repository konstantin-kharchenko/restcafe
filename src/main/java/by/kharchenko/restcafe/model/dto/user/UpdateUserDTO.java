package by.kharchenko.restcafe.model.dto.user;


import by.kharchenko.restcafe.model.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

import static by.kharchenko.restcafe.controller.DbColumn.PASSWORD;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateUserDTO {

    @JsonProperty("userId")
    private Long userId;

    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    @JsonProperty("login")
    private String login;

    @Column(name = PASSWORD)
    @NotEmpty(message = "password must not be empty")
    @Pattern(regexp = ".*[A-Za-z.-_*].*", message = "Invalid")
    private String password;

    @JsonProperty("role")
    private RoleType role;
}