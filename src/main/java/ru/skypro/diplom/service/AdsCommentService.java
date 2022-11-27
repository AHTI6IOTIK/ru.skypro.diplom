package ru.skypro.diplom.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.ResponseWrapperAdsCommentDto;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.CommentEntity;
import ru.skypro.diplom.entity.UserEntity;
import ru.skypro.diplom.exception.NotFoundCommentException;
import ru.skypro.diplom.exception.UpdateCommentForbidden;
import ru.skypro.diplom.mapping.ads.AdsCommentDtoMapper;
import ru.skypro.diplom.repository.AdsRepository;
import ru.skypro.diplom.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AdsCommentService {
    private final AdsRepository adsRepository;
    private final CommentRepository commentRepository;
    private final AdsCommentDtoMapper adsCommentDtoMapper;
    private final UsersService usersService;

    public AdsCommentService(
        AdsRepository adsRepository,
        CommentRepository commentRepository,
        AdsCommentDtoMapper adsCommentDtoMapper,
        UsersService usersService
    ) {
        this.adsRepository = adsRepository;
        this.commentRepository = commentRepository;
        this.adsCommentDtoMapper = adsCommentDtoMapper;
        this.usersService = usersService;
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

    public AdsCommentDto addAdsComment(
        long adPk,
        String userLogin,
        AdsCommentDto comment
    ) {
        Optional<AdsEntity> optionalAds = adsRepository.findById(adPk);

        AdsEntity adsEntity = optionalAds.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        UserEntity user = usersService.getUserByLogin(userLogin);

        comment.setCreatedAt(LocalDateTime.now());
        CommentEntity commentEntity = adsCommentDtoMapper.toModel(
            comment,
            user,
            adsEntity
        );

        return adsCommentDtoMapper.toDto(
            commentRepository.save(commentEntity)
        );
    }

    public boolean deleteAdsComment(
        String userLogin,
        long adPk,
        long commentId
    ) {
        CommentEntity comment = commentRepository.findByIdAndAdsIdAndUserEmail(
            commentId,
            adPk,
            userLogin
        ).orElseThrow(NotFoundCommentException::new);

        commentRepository.delete(comment);

        return true;
    }

    public AdsCommentDto getAdsComment(
        long adPk,
        long commentId
    ) {
        Optional<CommentEntity> commentOptional = commentRepository.findByIdAndAdsId(
            commentId,
            adPk
        );

        return commentOptional
            .map(adsCommentDtoMapper::toDto)
            .orElse(null);
    }

    public AdsCommentDto updateAdsComment(
        String userLogin,
        long adsId,
        long commentId,
        AdsCommentDto updatedAdsCommentDto
    ) {
        Optional<CommentEntity> commentOptional = commentRepository.findByIdAndAdsIdAndUserEmail(
            commentId,
            adsId,
            userLogin
        );

        commentOptional.ifPresent(entity -> {
            entity.setText(updatedAdsCommentDto.getText());

            commentRepository.save(entity);
        });

        return commentOptional
            .map(adsCommentDtoMapper::toDto)
            .orElseThrow(UpdateCommentForbidden::new);
    }
}
