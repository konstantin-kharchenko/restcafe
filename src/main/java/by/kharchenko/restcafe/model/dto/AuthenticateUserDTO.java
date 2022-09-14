package by.kharchenko.restcafe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AuthenticateUserDTO {
    @NotEmpty(message = "login must not be empty")
    @Pattern(regexp = "^[A-Za-z0-9-_]{3,}$", message = "Invalid")
    @JsonProperty("login")
    private String login;

    @NotEmpty(message = "password must not be empty")
    @Pattern(regexp = ".*[A-Za-z.-_*].*", message = "Invalid")
    @JsonProperty("password")
    private String password;
}
