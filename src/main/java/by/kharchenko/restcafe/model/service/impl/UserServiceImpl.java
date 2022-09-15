package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.token.TokenDTO;
import by.kharchenko.restcafe.model.dto.user.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.user.RegistrationUserDTO;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.entity.*;
import by.kharchenko.restcafe.model.mapper.UserMapper;
import by.kharchenko.restcafe.model.repository.AdminRepository;
import by.kharchenko.restcafe.model.repository.ClientRepository;
import by.kharchenko.restcafe.model.repository.UserRepository;
import by.kharchenko.restcafe.model.service.UserService;
import by.kharchenko.restcafe.security.JwtTokenProvider;
import by.kharchenko.restcafe.util.email.CustomMailSender;
import by.kharchenko.restcafe.util.encryption.EncryptionPassword;
import by.kharchenko.restcafe.util.filereadwrite.FileReaderWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String APP_MAIL = "cafe.from.app@mail.ru";
    private static final String REGISTRATION_HEAD_MAIL = "Registration message";
    private static final String REGISTRATION_TEXT_MAIL = "Congratulations on your successful registration in the cafe app";
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final FileReaderWriter readerWriter;
    private final CustomMailSender mailSender;

    @Override
    public boolean delete(Long id) throws ServiceException {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public boolean add(RegistrationUserDTO registrationUserDTO) throws ServiceException {
        User user = UserMapper.INSTANCE.registrationUserDTOToUser(registrationUserDTO);
        boolean isLoginExists = userRepository.findIdByLogin(user.getLogin()).isPresent();
        if (isLoginExists) {
            throw new ServiceException("this login is already exists");
        }
        Set<Role> roleSet = user.getRoles();
        Set<Role> administratorRole = roleSet.stream()
                .filter(role -> role.getRole().equals(RoleType.ROLE_ADMINISTRATOR))
                .collect(Collectors.toSet());
        if (administratorRole.size() == 1) {
            Administrator administrator = new Administrator();
            administrator.setUser(user);
            if (adminRepository.save(administrator).equals(administrator)) {
                mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                return true;
            }
            return false;
        }
        Set<Role> clientRole = roleSet.stream()
                .filter(role -> role.getRole().equals(RoleType.ROLE_CLIENT))
                .collect(Collectors.toSet());
        if (clientRole.size() == 1) {
            Client client = new Client();
            client.setUser(user);
            if (clientRepository.save(client).equals(client)) {
                mailSender.sendCustomEmail(user.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Optional<UserDTO> findById(Long id) throws ServiceException {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(optionalUser.get());
            return Optional.of(userDTO);
        }
        return Optional.empty();
    }

    @Override
    public List<UserDTO> findAll() throws ServiceException {
        List<User> users = userRepository.findAll();
        return UserMapper.INSTANCE.listUserTOListUserDTO(users);
    }

    @Override
    @Transactional
    public boolean update(UpdateUserDTO userDTO) throws ServiceException {
        User user = UserMapper.INSTANCE.updateUserDTOToUser(userDTO);
        boolean isLoginExists = userRepository.findAnotherIdByLogin(user.getLogin(), user.getUserId()).isPresent();
        if (isLoginExists) {
            throw new ServiceException("this login is already exists");
        }
        Optional<User> optionalUserDb = userRepository.findById(user.getUserId());
        if (optionalUserDb.isPresent()) {
            User userDb = optionalUserDb.get();
            if (userDb.getRoles() == user.getRoles()){
                userDb.setLogin(user.getLogin());
                userDb.setPassword(user.getPassword());
                userDb.setRoles(user.getRoles());
                return userRepository.save(userDb).equals(userDb);
            }
            else {
                Set<Role> roleSet = user.getRoles();
                Set<Role> roleSetDb = userDb.getRoles();
                Set<Role> administratorRole = roleSet.stream()
                        .filter(role -> role.getRole().equals(RoleType.ROLE_ADMINISTRATOR))
                        .collect(Collectors.toSet());
                Set<Role> administratorRoleDb = roleSetDb.stream()
                        .filter(role -> role.getRole().equals(RoleType.ROLE_ADMINISTRATOR))
                        .collect(Collectors.toSet());
                Set<Role> clientRole = roleSet.stream()
                        .filter(role -> role.getRole().equals(RoleType.ROLE_CLIENT))
                        .collect(Collectors.toSet());
                Set<Role> clientRoleDb = roleSetDb.stream()
                        .filter(role -> role.getRole().equals(RoleType.ROLE_CLIENT))
                        .collect(Collectors.toSet());
                if (administratorRole.size() == 1 && administratorRoleDb.size() == 0){
                    clientRepository.deleteByUser(userDb);
                    userDb.setLogin(user.getLogin());
                    userDb.setPassword(user.getPassword());
                    userDb.setRoles(user.getRoles());
                    Administrator administrator = new Administrator();
                    administrator.setUser(userDb);
                    return adminRepository.save(administrator).equals(administrator);
                }
                else if (administratorRole.size() == 0 && administratorRoleDb.size() == 1){
                    adminRepository.deleteByUser(userDb);
                    userDb.setLogin(user.getLogin());
                    userDb.setPassword(user.getPassword());
                    userDb.setRoles(user.getRoles());
                    Client client = new Client();
                    client.setUser(userDb);
                    return clientRepository.save(client).equals(client);
                }
                else {
                    throw new ServiceException("this user does not have suitable roles");
                }
            }
        } else {
            throw new ServiceException("User with this id not found");
        }
    }

    @Override
    public Long count() throws ServiceException {
        return userRepository.count();
    }

    @Override
    public Optional<TokenDTO> logIn(AuthenticateUserDTO authenticateUserDTO) throws ServiceException {
        Optional<User> optionalUser = userRepository.getByLogin(authenticateUserDTO.getLogin());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            String encryptionPassword = EncryptionPassword.encryption(authenticateUserDTO.getPassword());
            if (user.getPassword().equals(encryptionPassword)) {
                TokenDTO tokenDTO = new TokenDTO();
                String accessToken = jwtTokenProvider.createAccessToken(user);
                String refreshToken = jwtTokenProvider.createRefreshToken(user);
                tokenDTO.setAccessToken(accessToken);
                tokenDTO.setRefreshToken(refreshToken);

                return Optional.of(tokenDTO);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<UserDTO> getByLogin(String login) {
        Optional<User> optionalUser = userRepository.getByLogin(login);
        if (optionalUser.isPresent()){
            UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(optionalUser.get());
            return Optional.of(userDTO);
        }
        return Optional.empty();
    }

    @Override
    public Set<Role> getRoles(Long id) {
        Set<Role> roles = new HashSet<>();
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            roles = optionalUser.get().getRoles();
        }
        return roles;
    }

    @Override
    public Optional<TokenDTO> refresh(String refreshToken) throws ServiceException {
        if (refreshToken != null && jwtTokenProvider.validateToken(refreshToken, JwtType.REFRESH)) {
            Long userId = Long.parseLong(jwtTokenProvider.getRefreshClaims(refreshToken).getSubject());
            Optional<User> optionalUser = userRepository.findById(userId);
            if (optionalUser.isPresent()) {
                String accessToken = jwtTokenProvider.createAccessToken(optionalUser.get());
                TokenDTO tokenDto = new TokenDTO(refreshToken, accessToken);
                return Optional.of(tokenDto);
            }
        }

        return Optional.empty();
    }
}
