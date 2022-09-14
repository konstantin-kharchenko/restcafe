package by.kharchenko.restcafe.model.dto;

import by.kharchenko.restcafe.model.entity.RoleType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserDTO {

    @JsonProperty("userId")
    private BigInteger userId;

    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    @JsonProperty("login")
    private String login;

    @JsonProperty("role")
    private RoleType role;
}
