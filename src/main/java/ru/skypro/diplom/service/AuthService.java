package ru.skypro.diplom.service;

import ru.skypro.diplom.dto.auth.RegisterReq;
import ru.skypro.diplom.dto.profile.Role;

public interface AuthService {
    boolean login(String userName, String password);

    boolean register(RegisterReq registerReq, Role role);
}
