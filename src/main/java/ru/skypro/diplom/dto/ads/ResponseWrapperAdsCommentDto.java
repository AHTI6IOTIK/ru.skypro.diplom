package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class ResponseWrapperAdsCommentDto {
    private long count;
    private AdsCommentDto[] result;
}
