package ru.skypro.diplom.factory;

import org.springframework.stereotype.Component;
import ru.skypro.diplom.dto.ads.AdsDto;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.CreateAdsDto;
import ru.skypro.diplom.dto.ads.FullAdsDto;

import java.time.LocalDateTime;

@Component
public class AdsFactory {
    public CreateAdsDto createCreateAds(
        String description,
        String image,
        long pk,
        int price,
        String title
    ) {
        CreateAdsDto result = new CreateAdsDto();
        result.setDescription(description);
        result.setImage(image);
        result.setPk(pk);
        result.setPrice(price);
        result.setTitle(title);

        return result;
    }

    public AdsDto createAds(
        String image,
        long pk,
        int price,
        String title
    ) {
        AdsDto result = new AdsDto();

        result.setImage(image);
        result.setPk(pk);
        result.setPrice(price);
        result.setTitle(title);

        return result;
    }

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

    public FullAdsDto createFullAds(
        String authorFirstName,
        String authorLastName,
        String description,
        String email,
        String image,
        String phone,
        long pk,
        int price,
        String title
    ) {
        FullAdsDto fullAdsDto = new FullAdsDto();

        fullAdsDto.setAuthorFirstName(authorFirstName);
        fullAdsDto.setAuthorLastName(authorLastName);
        fullAdsDto.setDescription(description);
        fullAdsDto.setEmail(email);
        fullAdsDto.setImage(image);
        fullAdsDto.setPhone(phone);
        fullAdsDto.setPk(pk);
        fullAdsDto.setPrice(price);
        fullAdsDto.setTitle(title);

        return fullAdsDto;
    }
}
