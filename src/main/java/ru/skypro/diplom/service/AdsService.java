package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.ads.*;
import ru.skypro.diplom.factory.AdsFactory;
import ru.skypro.diplom.mapping.ads.AdsCommentDtoMapper;
import ru.skypro.diplom.mapping.ads.AdsDtoMapper;
import ru.skypro.diplom.mapping.ads.CreateAdsDtoMapper;
import ru.skypro.diplom.mapping.ads.FullAdsDtoMapper;

@Service
public class AdsService {

    private final AdsFactory factory;
    private final AdsCommentDtoMapper adsCommentDtoMapper;
    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;

    public AdsService(
        AdsFactory factory,
        AdsCommentDtoMapper adsCommentDtoMapper,
        AdsDtoMapper adsDtoMapper,
        CreateAdsDtoMapper createAdsDtoMapper,
        FullAdsDtoMapper fullAdsDtoMapper
    ) {
        this.factory = factory;
        this.adsCommentDtoMapper = adsCommentDtoMapper;
        this.adsDtoMapper = adsDtoMapper;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.fullAdsDtoMapper = fullAdsDtoMapper;
    }

    public AdsDto createAds(CreateAdsDto createAdsDto) {
        System.out.println("createAdsDtoMapper.toModel(createAdsDto) = " + createAdsDtoMapper.toModel(createAdsDto));
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
            100,
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
     * @param adsDto
     * @param updatedAdsDto
     * @return
     * @TODO: Ads ads - заменить на entity
     */
    public boolean updateAds(AdsDto adsDto, AdsDto updatedAdsDto) {
        return false;
    }
}
