package by.kharchenko.restcafe.model.dto;

import by.kharchenko.restcafe.model.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
public class AdministratorDTO {

    @JsonProperty("idAdministrator")
    private BigInteger idAdministrator;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("status")
    private Status status = Status.WAITING;
}
