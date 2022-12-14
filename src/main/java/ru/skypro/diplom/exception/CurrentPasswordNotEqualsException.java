package ru.skypro.diplom.exception;

public class CurrentPasswordNotEqualsException extends RuntimeException {
    public CurrentPasswordNotEqualsException() {
        super("The current password does not match");
    }
}
