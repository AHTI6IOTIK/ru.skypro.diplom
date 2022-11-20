package ru.skypro.diplom.dto.auth;

import lombok.Data;
import ru.skypro.diplom.dto.profile.Role;

@Data
public class RegisterReq {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private Role role;
}
