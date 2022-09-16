package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.user.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.token.TokenDTO;
import by.kharchenko.restcafe.model.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Optional;

import static by.kharchenko.restcafe.model.entity.RoleType.ROLE_CLIENT;

@RestController
public class AuthenticationController {
    private static final String REFRESH_TOKEN = "Refresh-Token";
    private final UserService userService;

    public AuthenticationController(UserService userService) {
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

    @GetMapping("/refresh")
    public ResponseEntity refresh(@RequestHeader(name = REFRESH_TOKEN) String refreshToken) throws ServletException {
        try {
            Optional<TokenDTO> tokenDtoOptional = userService.refresh(refreshToken);
            if (tokenDtoOptional.isPresent()) {
                return ResponseEntity.ok(tokenDtoOptional.get());
            }
            return ResponseEntity.status(401).build();
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}