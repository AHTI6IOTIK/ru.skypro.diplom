package ru.skypro.diplom.dto.auth;

import lombok.Data;

@Data
public class LoginReq {
    private String password;
    private String username;

}
