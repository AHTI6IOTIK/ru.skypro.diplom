package ru.skypro.diplom.dto.ads;

import lombok.Data;

@Data
public class ResponseWrapperAds {
    private long count;
    private Ads[] result;
}
