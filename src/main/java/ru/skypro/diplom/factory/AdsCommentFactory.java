package ru.skypro.diplom.factory;

import org.springframework.stereotype.Component;
import ru.skypro.diplom.dto.ads.AdsCommentDto;

import java.time.LocalDateTime;

@Component
public class AdsCommentFactory {
    public AdsCommentDto createAdsComment(
        LocalDateTime dateTime,
        long pk,
        long author,
        String text
    ) {
        AdsCommentDto adsCommentDto = new AdsCommentDto();

        adsCommentDto.setCreatedAt(dateTime);
        adsCommentDto.setPk(pk);
        adsCommentDto.setAuthor(author);
        adsCommentDto.setText(text);

        return adsCommentDto;
    }
}
