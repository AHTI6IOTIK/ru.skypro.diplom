package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class AdsDto {
    private long author;
    private String image;
    private long pk;
    private int price;
    private String title;
}
