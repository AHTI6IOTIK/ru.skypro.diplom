package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class Ads {
    private long author;
    private String image;
    private long pk;
    private long price;
    private String title;
}
