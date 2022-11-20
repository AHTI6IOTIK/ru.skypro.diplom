package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class CreateAds {
    private String description;
    private String image;
    private long pk;
    private long price;
    private String title;
}
