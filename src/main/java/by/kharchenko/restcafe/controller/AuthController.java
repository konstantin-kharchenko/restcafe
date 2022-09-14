package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.TokenDTO;
import by.kharchenko.restcafe.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody AuthenticateUserDTO authenticateUserDTO, BindingResult bindingResult) throws ServletException {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        try {
            Optional<TokenDTO> tokenDto = userService.logIn(authenticateUserDTO);
            if (tokenDto.isPresent()) {
                return ResponseEntity.ok(tokenDto.get());
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
        return ResponseEntity.status(400).build();
    }

}