package ru.skypro.diplom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.skypro.diplom.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
