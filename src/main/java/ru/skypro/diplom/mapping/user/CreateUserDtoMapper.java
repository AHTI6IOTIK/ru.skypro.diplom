package ru.skypro.diplom.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CreateUserDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "email", source = "dto.username")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "password", source = "password")
    UserEntity toModel(CreateUserDto dto, String password);

    @Mapping(target = "username", source = "email")
    CreateUserDto toDto(UserEntity entity);
}
