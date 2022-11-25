package ru.skypro.diplom.service;

import ru.skypro.diplom.dto.auth.RegReqDto;
import ru.skypro.diplom.dto.profile.RoleEnum;

public interface AuthService {
    boolean login(String userName, String password);
    boolean register(RegReqDto registerReq, RoleEnum roleEnum);
}
