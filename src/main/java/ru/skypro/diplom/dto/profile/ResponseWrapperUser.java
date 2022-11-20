package ru.skypro.diplom.dto.profile;

import lombok.Data;

@Data
public class ResponseWrapperUser {
    private long count;
    private User[] result;
}
