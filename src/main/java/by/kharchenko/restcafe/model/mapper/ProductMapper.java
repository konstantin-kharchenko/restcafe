package by.kharchenko.restcafe.model.mapper;

import by.kharchenko.restcafe.model.dto.product.ProductDTO;
import by.kharchenko.restcafe.model.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    ProductDTO productToProductDTO(Product product);

    Product productDTOToProduct(ProductDTO productDTO);

    List<ProductDTO> productListToProductDTOList(List<Product> products);
}
