package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.product.CreateProductDTO;
import by.kharchenko.restcafe.model.dto.product.ProductDTO;
import by.kharchenko.restcafe.model.dto.product.UpdateProductDTO;
import by.kharchenko.restcafe.model.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    boolean delete(Long id) throws ServiceException;

    boolean add(CreateProductDTO productDTO) throws ServiceException;

    Optional<ProductDTO> findById(Long id) throws ServiceException;

    List<ProductDTO> findAll() throws ServiceException;

    boolean update(UpdateProductDTO t) throws ServiceException;

    Long count() throws ServiceException;
}
