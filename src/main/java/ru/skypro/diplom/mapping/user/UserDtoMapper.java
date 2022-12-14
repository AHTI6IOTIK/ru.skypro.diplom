package ru.skypro.diplom.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    @Mapping(target = "password", ignore = true)
    UserEntity toModel(UserDto dto);

    UserDto toDto(UserEntity entity);

    List<UserDto> toUserDtoList(List<UserEntity> entityList);

    List<UserEntity> toUserEntityList(List<UserDto> dtoList);

}
