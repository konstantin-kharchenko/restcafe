package by.kharchenko.restcafe.model.mapper;

import by.kharchenko.restcafe.model.dto.client.ClientDTO;
import by.kharchenko.restcafe.model.entity.Client;
import by.kharchenko.restcafe.model.entity.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);
    ClientDTO clientToClientDTO(Client client);

    Ingredient clientDTOToClient(ClientDTO clientDTO);
}
