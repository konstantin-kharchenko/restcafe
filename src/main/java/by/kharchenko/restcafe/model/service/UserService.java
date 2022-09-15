package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.token.TokenDTO;
import by.kharchenko.restcafe.model.dto.user.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.user.RegistrationUserDTO;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.entity.Role;
import by.kharchenko.restcafe.model.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    boolean delete(Long id) throws ServiceException;

    boolean add(RegistrationUserDTO userDTO) throws ServiceException;

    Optional<UserDTO> findById(Long id) throws ServiceException;

    List<UserDTO> findAll() throws ServiceException;

    boolean update(UpdateUserDTO userDTO) throws ServiceException;

    Long count() throws ServiceException;

    Optional<TokenDTO> logIn(AuthenticateUserDTO authenticateUserDTO) throws ServiceException;

    Optional<UserDTO> getByLogin(String login);

    Set<Role> getRoles(Long id);

    Optional<TokenDTO> refresh(String refreshToken) throws ServiceException;
}
