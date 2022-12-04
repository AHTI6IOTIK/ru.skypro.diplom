package ru.skypro.diplom.dto.auth;

import lombok.Data;
import ru.skypro.diplom.dto.profile.RoleEnum;

@Data
public class RegReqDto {
    private String username;
    private String password;
    private RoleEnum roleEnum;
}
