package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.entity.Administrator;
import by.kharchenko.restcafe.model.entity.Client;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import by.kharchenko.restcafe.model.entity.User;
import by.kharchenko.restcafe.model.repository.AdminRepository;
import by.kharchenko.restcafe.model.repository.ClientRepository;
import by.kharchenko.restcafe.model.repository.UserRepository;
import by.kharchenko.restcafe.model.service.UserService;
import by.kharchenko.restcafe.util.email.CustomMailSender;
import by.kharchenko.restcafe.util.filereadwrite.FileReaderWriter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private static final String APP_MAIL = "cafe.from.app@mail.ru";
    private static final String REGISTRATION_HEAD_MAIL = "Registration message";
    private static final String REGISTRATION_TEXT_MAIL = "Congratulations on your successful registration in the cafe app";
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final AdminRepository adminRepository;
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
        switch (userData.getRole()) {
            case CLIENT -> {
                Client client = new Client();
                client.setUser(userData);
                if (clientRepository.save(client).equals(client)) {
                    mailSender.sendCustomEmail(userData.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                    return true;
                }
            }
            case ADMINISTRATOR -> {
                Administrator administrator = new Administrator();
                administrator.setUser(userData);
                if (adminRepository.save(administrator).equals(administrator)) {
                    mailSender.sendCustomEmail(userData.getEmail(), APP_MAIL, REGISTRATION_HEAD_MAIL, REGISTRATION_TEXT_MAIL);
                    return true;
                }
            }
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
            if (userData.getRole() != null && userDb.getRole() != userData.getRole()) {
                switch (userData.getRole()) {
                    case CLIENT -> {
                        adminRepository.deleteByUser(userDb);
                        userDb.setLogin(userData.getLogin());
                        userDb.setPassword(userData.getPassword());
                        userDb.setRole(userData.getRole());
                        Client client = new Client();
                        client.setUser(userDb);
                        return clientRepository.save(client).equals(client);
                    }
                    case ADMINISTRATOR -> {
                        clientRepository.deleteByUser(userDb);
                        userDb.setLogin(userData.getLogin());
                        userDb.setPassword(userData.getPassword());
                        userDb.setRole(userData.getRole());
                        Administrator administrator = new Administrator();
                        administrator.setUser(userDb);
                        return adminRepository.save(administrator).equals(administrator);
                    }
                }
                throw new ServiceException("unknown role");
            } else {
                return userRepository.save(userDb).equals(userDb);
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
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User u = userRepository.getByLogin(login);
        if (Objects.isNull(u)) {
            throw new UsernameNotFoundException(String.format("User %s is not found", login));
        }
        return new org.springframework.security.core.userdetails.User(u.getLogin(), u.getPassword(), true, true, true, true, new HashSet<>());
    }

    public User getByLogin(String login) {
        return userRepository.getByLogin(login);
    }
}
