package ru.skypro.diplom.dto.auth;

import lombok.Data;
import ru.skypro.diplom.dto.profile.RoleEnum;

@Data
public class RegisterReqDto {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private RoleEnum roleEnum;
}
