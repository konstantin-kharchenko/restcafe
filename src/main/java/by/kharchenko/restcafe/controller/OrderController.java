package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/create")
    @SendTo("/admin/orders")
    public OrderDTO createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            throw new ServletException(result.toString());
        }
        orderService.add(createOrderDTO);
        Optional<OrderDTO> optionalOrderDTO = orderService.findByName(createOrderDTO.getName());
        return optionalOrderDTO.orElse(null);
    }
}
