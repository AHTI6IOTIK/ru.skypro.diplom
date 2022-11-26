package ru.skypro.diplom.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.ResponseWrapperAdsCommentDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.CommentEntity;
import ru.skypro.diplom.mapping.ads.AdsCommentDtoMapper;
import ru.skypro.diplom.repository.AdsRepository;
import ru.skypro.diplom.repository.CommentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdsCommentService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsCommentDtoMapper adsCommentDtoMapper;

    public AdsCommentService(
        AdsRepository adsRepository,
        CommentRepository commentRepository,
        AdsCommentDtoMapper adsCommentDtoMapper
    ) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.adsCommentDtoMapper = adsCommentDtoMapper;
    }

    public ResponseWrapperAdsCommentDto getAdsComments(long adPk) {
        List<CommentEntity> commentList = commentRepository.findByAdsId(adPk);

        if (commentList.isEmpty()) {
            return null;
        }

        ResponseWrapperAdsCommentDto wrapperAdsComment = new ResponseWrapperAdsCommentDto();

        wrapperAdsComment.setCount(commentList.size());
        wrapperAdsComment.setResult(
            adsCommentDtoMapper.toAdsDtoList(commentList)
                .toArray(new AdsCommentDto[0])
        );

        return wrapperAdsComment;
    }

    public AdsCommentDto addAdsComment(long adPk, AdsCommentDto comment) {
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserId(adPk, comment.getAuthor());

        AdsEntity adsEntity = optionalAds.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        CommentEntity commentEntity = adsCommentDtoMapper.toModel(
            comment,
            adsEntity.getUser(),
            adsEntity
        );

        return adsCommentDtoMapper.toDto(
            commentRepository.save(commentEntity)
        );
    }

    public boolean deleteAdsComment(long userId, long adPk, long commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findByIdAndAdsIdAndUserId(commentId, adPk, userId);

        commentOptional.ifPresent(commentRepository::delete);

        return commentOptional.isPresent();
    }

    public AdsCommentDto getAdsComment(long userId, long adPk, long commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findByIdAndAdsIdAndUserId(commentId, adPk, userId);

        return commentOptional
            .map(adsCommentDtoMapper::toDto)
            .orElse(null);
    }

    public AdsCommentDto updateAdsComment(long userId, long adsId, long commentId, AdsCommentDto updatedAdsCommentDto) {
        Optional<CommentEntity> commentOptional = commentRepository.findByIdAndAdsIdAndUserId(commentId, adsId, userId);

        commentOptional.ifPresent(entity -> {
            entity.setText(updatedAdsCommentDto.getText());

            commentRepository.save(entity);
        });

        return commentOptional
            .map(adsCommentDtoMapper::toDto)
            .orElse(null);
    }
}
