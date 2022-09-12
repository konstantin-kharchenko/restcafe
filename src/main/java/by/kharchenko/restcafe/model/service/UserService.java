package by.kharchenko.restcafe.model.service;

import by.kharchenko.restcafe.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends BaseService<User>, UserDetailsService {
    User getByLogin(String login);
}
