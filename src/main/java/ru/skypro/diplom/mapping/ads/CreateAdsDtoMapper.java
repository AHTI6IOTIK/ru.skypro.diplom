package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.CreateAdsDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CreateAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "image", source = "image")
    AdsEntity toModel(CreateAdsDto dto, UserEntity user, String image);

    CreateAdsDto toDto(AdsEntity entity);
}
