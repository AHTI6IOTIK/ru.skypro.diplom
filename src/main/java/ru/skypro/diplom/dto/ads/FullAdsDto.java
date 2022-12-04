package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class FullAdsDto {
    private String image;
    private String authorFirstName;
    private String authorLastName;
    private String email;
    private String phone;
    private long pk;
    private int price;
    private String title;
    private String description;
}
