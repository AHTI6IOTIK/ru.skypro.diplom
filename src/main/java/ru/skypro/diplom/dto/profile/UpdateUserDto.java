package ru.skypro.diplom.dto.profile;

import lombok.Data;

@Data
public class UpdateUserDto {
    private String firstName;
    private String lastName;
    private String phone;
}
