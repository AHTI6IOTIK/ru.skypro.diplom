package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class ResponseWrapperAdsComment {
    private long count;
    private AdsComment[] result;
}
