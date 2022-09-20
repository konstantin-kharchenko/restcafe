package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.order.UpdateOrderDTO;
import by.kharchenko.restcafe.model.entity.Order;
import by.kharchenko.restcafe.model.mapper.OrderMapper;
import by.kharchenko.restcafe.model.repository.OrderRepository;
import by.kharchenko.restcafe.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }

    @Override
    public boolean add(CreateOrderDTO orderDTO) throws ServiceException {
        Order order = OrderMapper.INSTANCE.createOrderDTOToOrder(orderDTO);
        orderRepository.save(order);
        return true;
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
