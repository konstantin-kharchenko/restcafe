package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.service.UserService;
import by.kharchenko.restcafe.security.JwtAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final UserService userService;

    @GetMapping()
    public UserDTO getClient() throws ServletException {
        try {
            Long id = ((JwtAuthentication) SecurityContextHolder.getContext().getAuthentication()).getUserId();
            Optional<UserDTO> optionalUserDTO = userService.findById(id);
            return optionalUserDTO.orElse(null);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

    }

    @PostMapping("/{id}/edit")
    public void updateClient(@PathVariable("id") Long id, @RequestBody @Valid UpdateUserDTO updateUserDTO, BindingResult result) throws ServletException {
        try {
            if (result.hasErrors()) {
                throw new ServletException(result.toString());
            }
            updateUserDTO.setUserId(id);
            boolean isUpdate = userService.update(updateUserDTO);
            if (!isUpdate) {
                throw new ServletException("Failed to add user");
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
