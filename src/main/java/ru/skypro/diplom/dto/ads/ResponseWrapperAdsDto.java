package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class ResponseWrapperAdsDto {
    private long count;
    private AdsDto[] result;
}
