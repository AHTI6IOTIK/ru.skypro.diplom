package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.CreateAdsDto;
import ru.skypro.diplom.entity.AdsEntity;

@Mapper(componentModel = "spring")
public interface CreateAdsDtoMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", ignore = true)
    AdsEntity toModel(CreateAdsDto dto);

    @Mapping(source = "id", target = "pk")
    CreateAdsDto toDto(AdsEntity entity);
}
