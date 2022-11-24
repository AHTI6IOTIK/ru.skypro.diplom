package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.ads.*;
import ru.skypro.diplom.factory.AdsFactory;

@Service
public class AdsService {

    private final AdsFactory factory;

    public AdsService(AdsFactory factory) {
        this.factory = factory;
    }

    public AdsDto createAds(CreateAdsDto createAdsDto) {
        return factory.createAds(
            "",
            1L,
            100,
            "title"
        );
    }

    public ResponseWrapperAdsDto getMyAds(
        boolean authenticated,
        String authority,
        Object credentials,
        Object details,
        Object principal
    ) {
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setCount(3);
        wrapperAds.setResult(new AdsDto[]{
            factory.createAds(
                "",
                1L,
                100,
                "title"
            ),
            factory.createAds(
                "",
                2L,
                200,
                "title"
            ),
            factory.createAds(
                "",
                3L,
                300,
                "title"
            ),
        });

        return wrapperAds;
    }

    public ResponseWrapperAdsDto getAllAds() {
        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setCount(3);
        wrapperAds.setResult(new AdsDto[]{
            factory.createAds(
                "",
                1L,
                100,
                "title"
            ),
            factory.createAds(
                "",
                2L,
                200,
                "title"
            ),
            factory.createAds(
                "",
                3L,
                300,
                "title"
            ),
        });

        return wrapperAds;
    }

    public boolean removeAds(long adsId) {
        return false;
    }

    public FullAdsDto getAds(long adsId) {
        return factory.createFullAds(
            "authorFirstName",
            "authorLastName",
            "description",
            "email",
            "image",
            "phone",
            adsId,
            100L,
            "title"
        );
    }

    public AdsDto findById(long adsId) {
        return factory.createAds(
            "",
            adsId,
            100,
            "title"
        );
    }

    /**
     * @TODO: Ads ads - заменить на entity
     * @param adsDto
     * @param updatedAdsDto
     * @return
     */
    public boolean updateAds(AdsDto adsDto, AdsDto updatedAdsDto) {
        return false;
    }
}
