package by.kharchenko.restcafe.model.dto.order;

import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.dto.product.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Set;


@Data
@NoArgsConstructor
public class CreateOrderDTO {

    @NotEmpty(message = "name must not be empty")
    @Pattern(regexp = "^[А-Яа-яA-Za-z0-9-_\\s]{3,}$", message = "Invalid")
    @JsonProperty("name")
    private String name;

    @JsonProperty("products")
    private Set<ProductDTO> products;

    @JsonProperty("client")
    private ClientDTO client;
}
