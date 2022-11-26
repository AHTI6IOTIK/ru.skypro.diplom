package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.ads.*;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.UserEntity;
import ru.skypro.diplom.factory.AdsFactory;
import ru.skypro.diplom.mapping.ads.AdsDtoMapper;
import ru.skypro.diplom.mapping.ads.CreateAdsDtoMapper;
import ru.skypro.diplom.mapping.ads.FullAdsDtoMapper;
import ru.skypro.diplom.repository.AdsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdsService {

    private final AdsRepository repository;

    private final AdsFactory factory;
    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;

    public AdsService(
        AdsRepository repository,
        AdsFactory factory,
        AdsDtoMapper adsDtoMapper,
        CreateAdsDtoMapper createAdsDtoMapper,
        FullAdsDtoMapper fullAdsDtoMapper
    ) {
        this.repository = repository;
        this.factory = factory;
        this.adsDtoMapper = adsDtoMapper;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.fullAdsDtoMapper = fullAdsDtoMapper;
    }

    public AdsDto createAds(CreateAdsDto createAdsDto) {
        int count = repository.countById(createAdsDto.getPk());
        if (count > 0) {
            return null;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);

        AdsEntity adsEntity = createAdsDtoMapper.toModel(
            createAdsDto,
            userEntity
        );
        AdsEntity savedEntity = repository.save(adsEntity);

        return adsDtoMapper.toDto(savedEntity);
    }

    public ResponseWrapperAdsDto getMyAds(
        boolean authenticated,
        String authority,
        Object credentials,
        Object details,
        Object principal
    ) {
        List<AdsEntity> myAds = repository.findByUserId(1L);

        if (myAds.isEmpty()) {
            return null;
        }

        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();

        wrapperAds.setCount(myAds.size());
        wrapperAds.setResult(
            adsDtoMapper.toAdsDtoList(myAds).toArray(new AdsDto[0])
        );


        return wrapperAds;
    }

    public ResponseWrapperAdsDto getAllAds() {
        List<AdsEntity> adsList = repository.findAll();

        if (adsList.isEmpty()) {
            return null;
        }

        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setResult(
            adsDtoMapper.toAdsDtoList(adsList).toArray(new AdsDto[0])
        );

        wrapperAds.setCount(adsList.size());

        return wrapperAds;
    }

    public boolean removeAds(long adsId, long userId) {
        Optional<AdsEntity> optionalAds = repository.findByIdAndUserId(adsId, userId);
        optionalAds.ifPresent((repository::delete));

        return optionalAds.isPresent();
    }

    public FullAdsDto getAds(long adsId, long userId) {
        Optional<AdsEntity> optionalAds = repository.findByIdAndUserId(adsId, userId);

        return optionalAds
            .map(fullAdsDtoMapper::toDto)
            .orElse(null);
    }

    public AdsDto updateAds(long userId, long adsId, AdsDto updatedAdsDto) {
        Optional<AdsEntity> optionalAds = repository.findByIdAndUserId(adsId, userId);

        optionalAds.ifPresent(entity -> {
            entity.setImage(updatedAdsDto.getImage());
            entity.setTitle(updatedAdsDto.getTitle());
            entity.setPrice(updatedAdsDto.getPrice());

            repository.save(entity);
        });

        return optionalAds
            .map(adsDtoMapper::toDto)
            .orElse(null);
    }
}
