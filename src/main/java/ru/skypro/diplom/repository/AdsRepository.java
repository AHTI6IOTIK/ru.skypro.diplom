package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.AdsEntity;

import java.util.List;
import java.util.Optional;

public interface AdsRepository extends JpaRepository<AdsEntity, Long> {
    int countById(long pk);

    List<AdsEntity> findByUserId(Long l);

    Optional<AdsEntity> findByIdAndUserId(Long id, Long userId);
}
