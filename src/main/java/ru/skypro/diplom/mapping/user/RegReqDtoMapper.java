package ru.skypro.diplom.mapping.user;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.auth.RegReqDto;
import ru.skypro.diplom.dto.profile.CreateUserDto;
import ru.skypro.diplom.dto.profile.RoleEnum;

@Mapper(componentModel = "spring")
public interface RegReqDtoMapper {
    @Mapping(target = "roleEnum", source = "role")
    RegReqDto fromCreateUser(CreateUserDto dto, RoleEnum role);
}
