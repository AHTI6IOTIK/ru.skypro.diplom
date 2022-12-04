package ru.skypro.diplom.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CreateUserDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    @Mapping(target = "email", source = "username")
    @Mapping(target = "role", ignore = true, defaultValue = "USER")
    UserEntity toModel(CreateUserDto dto);

    @Mapping(target = "username", source = "email")
    CreateUserDto toDto(UserEntity entity);
}
