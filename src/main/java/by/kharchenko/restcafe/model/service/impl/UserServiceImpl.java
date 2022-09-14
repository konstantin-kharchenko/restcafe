package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.AuthenticateUserDTO;
import by.kharchenko.restcafe.model.dto.TokenDTO;
import by.kharchenko.restcafe.model.entity.*;
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
    public boolean delete(User user) throws ServiceException {
        userRepository.delete(user);
        return true;
    }

    @Override
    public boolean delete(Long id) throws ServiceException {
        userRepository.deleteById(id);
        return false;
    }

    @Override
    public boolean add(User userData) throws ServiceException {
        boolean isLoginExists = userRepository.findIdByLogin(userData.getLogin()).isPresent();
        if (isLoginExists) {
            throw new ServiceException("this login is already exists");
        }
        Set<Role> roleSet = userData.getRoles();
        Set<Role> administratorRole = roleSet.stream()
                .filter(role -> role.getRole().equals(RoleType.ADMINISTRATOR))
                .collect(Collectors.toSet());
        if (administratorRole.size() == 1) {
            Administrator administrator = new Administrator();
            administrator.setUser(userData);
            if (adminRepository.save(administrator).equals(administrator)) {
                mailSender.sendCustomEmail(userData.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                return true;
            }
            return false;
        }
        Set<Role> clientRole = roleSet.stream()
                .filter(role -> role.getRole().equals(RoleType.CLIENT))
                .collect(Collectors.toSet());
        if (clientRole.size() == 1) {
            Client client = new Client();
            client.setUser(userData);
            if (clientRepository.save(client).equals(client)) {
                mailSender.sendCustomEmail(userData.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    public Optional<User> findById(Long id) throws ServiceException {
        return userRepository.findById(id);
    }

    @Override
    public List<User> findAll() throws ServiceException {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public boolean update(User userData) throws ServiceException {
        boolean isLoginExists = userRepository.findAnotherIdByLogin(userData.getLogin(), userData.getUserId()).isPresent();
        if (isLoginExists) {
            throw new ServiceException("this login is already exists");
        }
        Optional<User> optionalUserDb = userRepository.findById(userData.getUserId());
        if (optionalUserDb.isPresent()) {
            User userDb = optionalUserDb.get();
            if (userDb.getRoles() == userData.getRoles()){
                userDb.setLogin(userData.getLogin());
                userDb.setPassword(userData.getPassword());
                userDb.setRoles(userData.getRoles());
                return userRepository.save(userDb).equals(userDb);
            }
            else {
                Set<Role> roleSet = userData.getRoles();
                Set<Role> roleSetDb = userDb.getRoles();
                Set<Role> administratorRole = roleSet.stream()
                        .filter(role -> role.getRole().equals(RoleType.ADMINISTRATOR))
                        .collect(Collectors.toSet());
                Set<Role> administratorRoleDb = roleSetDb.stream()
                        .filter(role -> role.getRole().equals(RoleType.ADMINISTRATOR))
                        .collect(Collectors.toSet());
                Set<Role> clientRole = roleSet.stream()
                        .filter(role -> role.getRole().equals(RoleType.CLIENT))
                        .collect(Collectors.toSet());
                Set<Role> clientRoleDb = roleSetDb.stream()
                        .filter(role -> role.getRole().equals(RoleType.CLIENT))
                        .collect(Collectors.toSet());
                if (administratorRole.size() == 1 && administratorRoleDb.size() == 0){
                    clientRepository.deleteByUser(userDb);
                    userDb.setLogin(userData.getLogin());
                    userDb.setPassword(userData.getPassword());
                    userDb.setRoles(userData.getRoles());
                    Administrator administrator = new Administrator();
                    administrator.setUser(userDb);
                    return adminRepository.save(administrator).equals(administrator);
                }
                else if (administratorRole.size() == 0 && administratorRoleDb.size() == 1){
                    adminRepository.deleteByUser(userDb);
                    userDb.setLogin(userData.getLogin());
                    userDb.setPassword(userData.getPassword());
                    userDb.setRoles(userData.getRoles());
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

    public Optional<User> getByLogin(String login) {
        return userRepository.getByLogin(login);
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
}
