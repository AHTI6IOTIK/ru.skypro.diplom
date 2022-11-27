package ru.skypro.diplom.service;

import org.springframework.stereotype.Service;
import ru.skypro.diplom.dto.ads.*;
import ru.skypro.diplom.entity.AdsEntity;
import ru.skypro.diplom.entity.UserEntity;
import ru.skypro.diplom.mapping.ads.AdsDtoMapper;
import ru.skypro.diplom.mapping.ads.CreateAdsDtoMapper;
import ru.skypro.diplom.mapping.ads.FullAdsDtoMapper;
import ru.skypro.diplom.repository.AdsRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AdsService {

    private final AdsRepository adsRepository;
    private final UsersService usersService;

    private final AdsDtoMapper adsDtoMapper;
    private final CreateAdsDtoMapper createAdsDtoMapper;
    private final FullAdsDtoMapper fullAdsDtoMapper;

    public AdsService(
        AdsRepository adsRepository,
        AdsDtoMapper adsDtoMapper,
        CreateAdsDtoMapper createAdsDtoMapper,
        FullAdsDtoMapper fullAdsDtoMapper,
        UsersService usersService
    ) {
        this.adsRepository = adsRepository;
        this.adsDtoMapper = adsDtoMapper;
        this.createAdsDtoMapper = createAdsDtoMapper;
        this.fullAdsDtoMapper = fullAdsDtoMapper;
        this.usersService = usersService;
    }

    public AdsDto createAds(
        String userLogin,
        CreateAdsDto createAdsDto
    ) {
        UserEntity user = usersService.getUserByLogin(userLogin);

        int count = adsRepository.countByTitleAndUserId(
            createAdsDto.getTitle(),
            user.getId()
        );

        if (count > 0) {
            return null;
        }

        AdsEntity adsEntity = createAdsDtoMapper.toModel(
            createAdsDto,
            user
        );

        return adsDtoMapper.toDto(adsRepository.save(adsEntity));
    }

    public ResponseWrapperAdsDto getMyAds(
        String userLogin,
        boolean authenticated,
        String authority,
        Object credentials,
        Object details,
        Object principal
    ) {
        List<AdsEntity> myAds = adsRepository.findByUserEmail(userLogin);

        if (myAds.isEmpty()) {
            return null;
        }

        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();

        wrapperAds.setCount(myAds.size());
        wrapperAds.setResult(
            adsDtoMapper.toAdsDtoList(myAds)
                .toArray(new AdsDto[0])
        );


        return wrapperAds;
    }

    public ResponseWrapperAdsDto getAllAds() {
        List<AdsEntity> adsList = adsRepository.findAll();

        if (adsList.isEmpty()) {
            return null;
        }

        ResponseWrapperAdsDto wrapperAds = new ResponseWrapperAdsDto();
        wrapperAds.setResult(
            adsDtoMapper.toAdsDtoList(adsList)
                .toArray(new AdsDto[0])
        );

        wrapperAds.setCount(adsList.size());

        return wrapperAds;
    }

    public boolean removeAds(
        long adsId,
        String userLogin
    ) {
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserEmail(
            adsId,
            userLogin
        );
        optionalAds.ifPresent(adsRepository::delete);

        return optionalAds.isPresent();
    }

    public FullAdsDto getAds(
        long adsId
    ) {
        Optional<AdsEntity> optionalAds = adsRepository.findById(
            adsId
        );

        return optionalAds
            .map(fullAdsDtoMapper::toDto)
            .orElse(null);
    }

    public AdsDto updateAds(
        String userLogin,
        long adsId,
        AdsDto updatedAdsDto
    ) {
        UserEntity user = usersService.getUserByLogin(userLogin);
        Optional<AdsEntity> optionalAds = adsRepository.findByIdAndUserId(
            adsId,
            user.getId()
        );

        optionalAds.ifPresent(entity -> {
            entity.setImage(updatedAdsDto.getImage());
            entity.setTitle(updatedAdsDto.getTitle());
            entity.setPrice(updatedAdsDto.getPrice());

            adsRepository.save(entity);
        });

        return optionalAds
            .map(adsDtoMapper::toDto)
            .orElse(null);
    }
}
