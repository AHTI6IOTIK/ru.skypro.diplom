package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.skypro.diplom.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByAdsId(long adPk);

    Optional<CommentEntity> findByIdAndAdsId(
        long commentId,
        long adsId
    );

    Optional<CommentEntity> findByIdAndAdsIdAndUserEmail(
        long commentId,
        long adsId,
        String email
    );

    @Transactional
    void deleteByAdsId(Long adsId);
}
