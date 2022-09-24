package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.order.UpdateOrderDTO;
import by.kharchenko.restcafe.model.entity.Client;
import by.kharchenko.restcafe.model.entity.Order;
import by.kharchenko.restcafe.model.entity.User;
import by.kharchenko.restcafe.model.mapper.ClientMapper;
import by.kharchenko.restcafe.model.mapper.OrderMapper;
import by.kharchenko.restcafe.model.repository.ClientRepository;
import by.kharchenko.restcafe.model.repository.OrderRepository;
import by.kharchenko.restcafe.model.repository.UserRepository;
import by.kharchenko.restcafe.model.service.OrderService;
import by.kharchenko.restcafe.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(CreateOrderDTO orderDTO) throws ServiceException {
        Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            Optional<Client> client = clientRepository.findByUser(optionalUser.get());
            if (client.isPresent()) {
                ClientDTO clientDTO = ClientMapper.INSTANCE.clientToClientDTO(client.get());
                orderDTO.setClient(clientDTO);
                Order order = OrderMapper.INSTANCE.createOrderDTOToOrder(orderDTO);
                orderRepository.save(order);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Optional<OrderDTO> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<OrderDTO> findAll() throws ServiceException {
        return null;
    }

    @Override
    public boolean update(UpdateOrderDTO orderDTO) throws ServiceException {
        return false;
    }

    @Override
    public Optional<OrderDTO> findByName(String name) {
        Optional<Order> optionalOrder =  orderRepository.findByName(name);
        if (optionalOrder.isPresent()){
            Order order = optionalOrder.get();
            OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order);
            return Optional.of(orderDTO);
        }
        return Optional.empty();
    }

    @Override
    public Long count() throws ServiceException {
        return null;
    }
}
