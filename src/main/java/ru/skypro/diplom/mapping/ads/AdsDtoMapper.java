package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.AdsDto;
import ru.skypro.diplom.entity.AdsEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsDtoMapper {
    @Mapping(source = "pk", target = "id")
    @Mapping(target = "description", ignore = true)
    @Mapping(target = "user", ignore = true)
    AdsEntity toModel(AdsDto dto);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "user.id", target = "author")
    AdsDto toDto(AdsEntity entity);

    List<AdsDto> toAdsDtoList(List<AdsEntity> entityList);

    List<AdsEntity> toAdsEntityList(List<AdsDto> dtoList);
}
