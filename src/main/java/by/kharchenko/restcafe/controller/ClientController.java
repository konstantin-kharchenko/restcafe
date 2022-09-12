package by.kharchenko.restcafe.controller;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.UserDTO;
import by.kharchenko.restcafe.model.entity.User;
import by.kharchenko.restcafe.model.mapper.UserMapper;
import by.kharchenko.restcafe.model.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.validation.Valid;

@RestController
@RequestMapping("/client")
@AllArgsConstructor
public class ClientController {

    private final UserService userService;

    @GetMapping("/{id}")
    public UserDTO getClient(@PathVariable("id") Long id) throws ServletException {
        User user = null;
        try {
            user = userService.findById(id).get();
            return UserMapper.INSTANCE.userToUserDTO(user);
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
            User user = UserMapper.INSTANCE.updateUserDTOToUser(updateUserDTO);
            user.setUserId(id);
            boolean isUpdate = userService.update(user);
            if (!isUpdate){
                throw new ServletException("Failed to add user");
            }
        } catch (ServiceException e) {
            throw new ServletException(e);
        }
    }
}
