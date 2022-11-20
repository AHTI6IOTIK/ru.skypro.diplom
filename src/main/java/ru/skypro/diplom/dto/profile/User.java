package ru.skypro.diplom.dto.profile;

import lombok.Data;

@Data
public class User {
    private String email;
    private String firstName;
    private long id;
    private String lastName;
    private String phone;
}
