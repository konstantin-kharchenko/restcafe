package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.dto.order.CreateOrderDTO;
import by.kharchenko.restcafe.model.dto.order.OrderDTO;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.service.ClientService;
import by.kharchenko.restcafe.model.service.OrderService;
import by.kharchenko.restcafe.model.service.UserService;
import by.kharchenko.restcafe.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContextHolder;
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
@AllArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ClientService clientService;


    @PostMapping("/order/create")
    @SendTo("/admin/orders")
    public OrderDTO createOrder(@RequestBody @Valid CreateOrderDTO createOrderDTO, BindingResult result) throws ServletException {
        try {
            if (result.hasErrors()) {
                throw new ServletException(result.toString());
            }
            Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();
            Optional<ClientDTO> clientDTO = clientService.findByUserId(id);
            if (clientDTO.isPresent()) {
                createOrderDTO.setClient(clientDTO.get());
                orderService.add(createOrderDTO);
                Optional<OrderDTO> optionalOrderDTO = orderService.findByName(createOrderDTO.getName());
                return optionalOrderDTO.orElse(null);
            }
            throw new ServletException("User not found");
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
