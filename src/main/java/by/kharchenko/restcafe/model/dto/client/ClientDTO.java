package by.kharchenko.restcafe.model.dto.client;

import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Set;


@Data
@NoArgsConstructor
public class ClientDTO {

    @JsonProperty("idClient")
    private Long clientId;

    @JsonProperty("loyaltyPoints")
    private int loyaltyPoints;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("orders")
    private Set<OrderDTO> orders;
}
