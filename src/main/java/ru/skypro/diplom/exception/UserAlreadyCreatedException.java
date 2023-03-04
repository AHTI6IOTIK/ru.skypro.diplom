package ru.skypro.diplom.exception;

public class UserAlreadyCreatedException extends RuntimeException {
    public UserAlreadyCreatedException(String login) {
        super(String.format(
            "User with login #%s, already created",
            login
        ));
    }
}
