package ru.skypro.diplom.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CreateUserDtoMapper {
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserEntity toModel(CreateUserDto dto);
    CreateUserDto toDto(UserEntity entity);
}
