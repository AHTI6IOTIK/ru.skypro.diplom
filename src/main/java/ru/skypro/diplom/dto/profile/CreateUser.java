package ru.skypro.diplom.dto.profile;

import lombok.Data;

@Data
public class CreateUser {
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String phone;
}
