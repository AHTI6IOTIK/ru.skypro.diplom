package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.CreateAdsDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface CreateAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    AdsEntity toModel(CreateAdsDto dto, UserEntity user);

    CreateAdsDto toDto(AdsEntity entity);
}
