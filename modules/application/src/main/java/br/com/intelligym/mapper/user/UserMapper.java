package br.com.intelligym.mapper.user;

import br.com.intelligym.dto.user.UserDTO;
import br.com.intelligym.model.user.User;

import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDTO userToUserDTO(User user);

    User userDTOToUser(UserDTO userDTO);
}
