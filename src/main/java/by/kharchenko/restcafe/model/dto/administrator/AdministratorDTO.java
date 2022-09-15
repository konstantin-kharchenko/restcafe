package by.kharchenko.restcafe.model.dto.administrator;

import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.entity.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdministratorDTO {

    @JsonProperty("idAdministrator")
    private Long administratorId;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("status")
    private Status status = Status.WAITING;
}
