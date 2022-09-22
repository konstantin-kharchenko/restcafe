package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.service.ClientService;
import by.kharchenko.restcafe.model.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/order/create")
    public void createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO, BindingResult result) throws ServletException {
        try {
            if (result.hasErrors()) {
                throw new ServletException(result.toString());
            }
            orderService.add(createOrderDTO);
            Optional<OrderDTO> optionalOrderDTO = orderService.findByName(createOrderDTO.getName());
            if (optionalOrderDTO.isPresent()) {
                OrderDTO orderDTO = optionalOrderDTO.get();
                this.simpMessagingTemplate.convertAndSend("/admin/orders", orderDTO);
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
