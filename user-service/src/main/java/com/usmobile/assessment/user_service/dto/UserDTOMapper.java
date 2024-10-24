package com.usmobile.assessment.user_service.dto;

import com.usmobile.assessment.user_service.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDTOMapper {
    UserDTOMapper INSTANCE = Mappers.getMapper(UserDTOMapper.class);

    @Mapping(source = "id", target = "id")
    UserDTO toDTO(User user);
    User toEntity(UserDTO userDTO);
}
