package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.ads.AdsDto;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.ResponseWrapperAdsCommentDto;
import ru.skypro.diplom.factory.AdsCommentFactory;

import java.time.LocalDateTime;

@Service
public class AdsCommentService {
    private final AdsCommentFactory factory;

    public AdsCommentService(AdsCommentFactory factory) {
        this.factory = factory;
    }

    public ResponseWrapperAdsCommentDto getAdsComments(long adPk) {
        ResponseWrapperAdsCommentDto wrapperAdsComment = new ResponseWrapperAdsCommentDto();
        wrapperAdsComment.setCount(3);
        wrapperAdsComment.setResult(new AdsCommentDto[]{
            factory.createAdsComment(
                LocalDateTime.of(2022, 12, 1, 12, 25),
                1L,
                1L,
                "text"
            ),
            factory.createAdsComment(
                LocalDateTime.of(2022, 12, 1, 12, 25),
                2L,
                2L,
                "text"
            ),
            factory.createAdsComment(
                LocalDateTime.of(2022, 12, 1, 12, 25),
                2L,
                2L,
                "text"
            ),
        });

        return wrapperAdsComment;
    }

    public AdsCommentDto addAdsComment(long adPk, AdsCommentDto comment) {
        comment.setPk(adPk);
        return comment;
    }

    public boolean deleteAdsComment(long adPk, long commentId) {
        return false;
    }

    public AdsCommentDto getAdsComment(long adPk, long commentId) {
        return factory.createAdsComment(
            LocalDateTime.of(2022, 12, 20, 20, 20),
            commentId,
            1L,
            "Text"
        );
    }

    public AdsCommentDto findById(long adsId) {
        return factory.createAdsComment(
            LocalDateTime.of(2022, 12, 20, 20, 20),
            adsId,
            1L,
            "Text"
        );
    }

    /**
     * @TODO: Ads ads, AdsComment adsComment - заменить на entity
     * @param adsDto
     * @param adsCommentDto
     * @param updatedAdsCommentDto
     * @return
     */
    public boolean updateAdsComment(AdsDto adsDto, AdsCommentDto adsCommentDto, AdsCommentDto updatedAdsCommentDto) {
        return false;
    }
}
