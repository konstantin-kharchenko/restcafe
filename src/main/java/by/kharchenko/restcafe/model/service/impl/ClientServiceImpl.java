package by.kharchenko.restcafe.model.service.impl;

import by.kharchenko.restcafe.exception.ServiceException;
import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.entity.Client;
import by.kharchenko.restcafe.model.entity.User;
import by.kharchenko.restcafe.model.mapper.ClientMapper;
import by.kharchenko.restcafe.model.repository.ClientRepository;
import by.kharchenko.restcafe.model.repository.UserRepository;
import by.kharchenko.restcafe.model.service.ClientService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    @Override
    public boolean delete(Long id) throws ServiceException {
        return false;
    }

    @Override
    public Optional<ClientDTO> findById(Long id) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public List<ClientDTO> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<ClientDTO> findByUserId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()){
            User user = optionalUser.get();
            Optional<Client> optionalClient = clientRepository.findByUser(user);
            if (optionalClient.isPresent()){
                Client client = optionalClient.get();
                ClientDTO clientDTO = ClientMapper.INSTANCE.clientToClientDTO(client);
                return Optional.of(clientDTO);
            }
            return Optional.empty();
        }
        return Optional.empty();
    }

    @Override
    public Long count() throws ServiceException {
        return null;
    }
}
