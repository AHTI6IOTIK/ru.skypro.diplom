package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.FullAdsDto;
import ru.skypro.diplom.dto.profile.UserDto;
import ru.skypro.diplom.entity.AdsEntity;

@Mapper(componentModel = "spring")
public interface FullAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "firstName", source = "authorFirstName")
    @Mapping(target = "lastName", source = "authorLastName")
    UserDto dtoToUserDto(FullAdsDto dto);

    @Mapping(source = "dto.pk", target = "id")
    @Mapping(target = "image", source = "dto.image")
    @Mapping(target = "user.password", ignore = true)
    AdsEntity toModel(FullAdsDto dto, UserDto user);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.firstName", target = "authorFirstName")
    @Mapping(source = "user.lastName", target = "authorLastName")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.phone", target = "phone")
    FullAdsDto toDto(AdsEntity entity);
}
