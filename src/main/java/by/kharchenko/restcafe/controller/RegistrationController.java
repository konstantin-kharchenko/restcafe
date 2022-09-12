package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.RegistrationUserDTO;
import by.kharchenko.restcafe.model.entity.User;
import by.kharchenko.restcafe.model.mapper.UserMapper;
import by.kharchenko.restcafe.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.validation.Valid;


@RestController
@AllArgsConstructor
public class RegistrationController {

    private final UserService userService;

    @PostMapping("/registration")
    public void registration(@RequestBody @Valid RegistrationUserDTO registrationUserDTO, BindingResult result) throws ServletException {
        try {
            if (result.hasErrors()) {
                throw new ServletException(result.toString());
            }
            User user = UserMapper.INSTANCE.registrationUserDTOToUser(registrationUserDTO);
            boolean isAdd = userService.add(user);
            if (!isAdd){
                throw new ServletException("Failed to add user");
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
