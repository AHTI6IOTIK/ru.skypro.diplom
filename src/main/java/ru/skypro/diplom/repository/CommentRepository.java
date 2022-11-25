package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.CommentEntity;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
}
