package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.order.UpdateOrderDTO;
import by.kharchenko.restcafe.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    boolean delete(Long id) throws ServiceException;

    boolean add(CreateOrderDTO orderDTO) throws ServiceException;

    Optional<OrderDTO> findById(Long id) throws ServiceException;

    List<OrderDTO> findAll() throws ServiceException;

    boolean update(UpdateOrderDTO orderDTO) throws ServiceException;

    Long count() throws ServiceException;
}
