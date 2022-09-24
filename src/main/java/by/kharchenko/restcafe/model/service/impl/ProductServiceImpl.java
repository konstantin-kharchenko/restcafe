package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.product.CreateProductDTO;
import by.kharchenko.restcafe.model.dto.product.ProductDTO;
import by.kharchenko.restcafe.model.dto.product.UpdateProductDTO;
import by.kharchenko.restcafe.model.entity.Product;
import by.kharchenko.restcafe.model.mapper.ProductMapper;
import by.kharchenko.restcafe.model.repository.ProductRepository;
import by.kharchenko.restcafe.model.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(CreateProductDTO productDTO) throws ServiceException {
        return false;
    }

    @Override
    public Optional<ProductDTO> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<ProductDTO> findAll() throws ServiceException {
        List<Product> products = productRepository.findAll();
        return ProductMapper.INSTANCE.productListToProductDTOList(products);
    }

    @Override
    public boolean update(UpdateProductDTO t) throws ServiceException {
        return false;
    }

    @Override
    public Long count() throws ServiceException {
        return null;
    }
}
