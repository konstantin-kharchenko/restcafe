package by.kharchenko.restcafe.model.mapper;

import by.kharchenko.restcafe.model.dto.RegistrationUserDTO;
import by.kharchenko.restcafe.model.dto.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.UserDTO;
import by.kharchenko.restcafe.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    RegistrationUserDTO userToRegistrationUserDTO(User user);

    User registrationUserDTOToUser(RegistrationUserDTO registrationUserDTO);

    UpdateUserDTO userToUpdateUserDTO(User user);

    User updateUserDTOToUser(UpdateUserDTO userDTO);
}
