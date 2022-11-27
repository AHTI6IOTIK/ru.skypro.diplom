package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.AdsEntity;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<AdsEntity, Long> {
    int countById(long pk);

    int countByTitleAndUserId(String title, Long userId);

    List<AdsEntity> findByUserId(Long l);

    Optional<AdsEntity> findByIdAndUserId(Long id, Long userId);
    Optional<AdsEntity> findByIdAndUserEmail(Long id, String email);

    List<AdsEntity> findByUserEmail(String userLogin);
}
