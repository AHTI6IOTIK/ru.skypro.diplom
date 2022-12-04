package ru.skypro.diplom.mapping.ads;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.CommentEntity;
import ru.skypro.diplom.entity.UserEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdsCommentDtoMapper {
    @Mapping(source = "dto.pk", target = "id")
    @Mapping(source = "user", target = "user")
    @Mapping(source = "ads", target = "ads")
    CommentEntity toModel(AdsCommentDto dto, UserEntity user, AdsEntity ads);

    @Mapping(source = "id", target = "pk")
    @Mapping(source = "entity.user.id", target = "author_id")
    @Mapping(source = "entity.user.avatar", target = "author_image")
    AdsCommentDto toDto(CommentEntity entity);

    List<AdsCommentDto> toAdsDtoList(List<CommentEntity> commentList);
}
