package by.kharchenko.restcafe.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigInteger;
import java.util.Set;

@Data
@NoArgsConstructor
public class OrderDTO {
    @JsonProperty("idOrder")
    private BigInteger idOrder;

    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    @JsonProperty("name")
    private String name;

    @JsonProperty("products")
    private Set<ProductDTO> products;
}
