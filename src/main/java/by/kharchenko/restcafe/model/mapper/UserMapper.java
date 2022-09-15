package by.kharchenko.restcafe.model.mapper;
import by.kharchenko.restcafe.model.dto.user.RegistrationUserDTO;
import by.kharchenko.restcafe.model.dto.user.UpdateUserDTO;
import by.kharchenko.restcafe.model.dto.user.UserDTO;
import by.kharchenko.restcafe.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);

    RegistrationUserDTO userToRegistrationUserDTO(User user);

    User registrationUserDTOToUser(RegistrationUserDTO registrationUserDTO);

    UpdateUserDTO userToUpdateUserDTO(User user);

    User updateUserDTOToUser(UpdateUserDTO userDTO);

    List<UserDTO> listUserTOListUserDTO(List<User> users);
}
