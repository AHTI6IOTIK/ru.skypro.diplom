package ru.skypro.diplom.dto.ads;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdsCommentDto {
    private long author;
    private LocalDateTime createdAt;
    private long pk;
    private String text;
}
