package by.kharchenko.restcafe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Set;


@Data
@NoArgsConstructor
public class ClientDTO {

    @JsonProperty("idClient")
    private BigInteger idClient;

    @JsonProperty("loyaltyPoints")
    private int loyaltyPoints;

    @JsonProperty("user")
    private UserDTO user;

    @JsonProperty("orders")
    private Set<OrderDTO> orders;
}
