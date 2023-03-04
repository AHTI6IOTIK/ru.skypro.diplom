package ru.skypro.diplom.dto.profile;

import lombok.Data;

@Data
public class CreateUserDto {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
}
