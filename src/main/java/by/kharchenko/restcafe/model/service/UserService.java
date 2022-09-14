package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.TokenDTO;
import by.kharchenko.restcafe.model.entity.Role;
import by.kharchenko.restcafe.model.entity.User;

import java.util.Optional;
import java.util.Set;

public interface UserService extends BaseService<User> {
    Optional<TokenDTO> logIn(AuthenticateUserDTO authenticateUserDTO) throws ServiceException;
    Optional<User> getByLogin(String login);
    Set<Role> getRoles(Long id);
}
