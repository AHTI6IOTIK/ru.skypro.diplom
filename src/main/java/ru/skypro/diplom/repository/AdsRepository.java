package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.AdsEntity;

public interface AdsRepository extends JpaRepository<AdsEntity, Long> {
}
