package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.order.UpdateOrderDTO;
import by.kharchenko.restcafe.model.entity.Client;

import java.util.List;
import java.util.Optional;

public interface ClientService {
    boolean delete(Long id) throws ServiceException;

    Optional<ClientDTO> findById(Long id) throws ServiceException;

    List<ClientDTO> findAll() throws ServiceException;

    Optional<ClientDTO> findByUserId(Long id);

    Long count() throws ServiceException;
}
