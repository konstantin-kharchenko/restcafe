package by.kharchenko.restcafe.model.mapper;

import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    OrderDTO OrderToOrderDTO(Order order);

    Order OrderDTOToOrder(OrderDTO orderDTO);
}
