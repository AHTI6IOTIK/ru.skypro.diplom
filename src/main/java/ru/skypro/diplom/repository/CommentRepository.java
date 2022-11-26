package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.CommentEntity;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    List<CommentEntity> findByAdsId(long adPk);

    Optional<CommentEntity> findByIdAndAdsIdAndUserId(long commentId, long adsId, long userId);
}
