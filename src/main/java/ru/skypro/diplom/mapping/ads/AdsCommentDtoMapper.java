package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.CommentEntity;
import ru.skypro.diplom.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface AdsCommentDtoMapper {
    @Mapping(source = "dto.pk", target = "id")
    CommentEntity toModel(AdsCommentDto dto, UserEntity user, AdsEntity ads);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "entity.user.id", target = "author")
    AdsCommentDto toDto(CommentEntity entity);
}