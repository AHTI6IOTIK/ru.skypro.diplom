package ru.skypro.diplom.dto.auth;

import lombok.Data;
import ru.skypro.diplom.dto.profile.Role;

@Data
public class RegReq {
    private String username;
    private String password;
    private Role role;
}
