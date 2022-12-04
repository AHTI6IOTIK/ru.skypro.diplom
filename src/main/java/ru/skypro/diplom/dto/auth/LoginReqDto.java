package ru.skypro.diplom.dto.auth;

import lombok.Data;

@Data
public class LoginReqDto {
    private String password;
    private String username;
}
