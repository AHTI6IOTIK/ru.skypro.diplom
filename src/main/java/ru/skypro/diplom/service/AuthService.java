package ru.skypro.diplom.service;

import ru.skypro.diplom.dto.RegisterReq;
import ru.skypro.diplom.dto.Role;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegisterReq registerReq, Role role);
}
