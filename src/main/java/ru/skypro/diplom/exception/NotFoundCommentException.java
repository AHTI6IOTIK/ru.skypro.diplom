package ru.skypro.diplom.exception;

public class NotFoundCommentException extends RuntimeException {
    public NotFoundCommentException() {
        super("Comment not found");
    }
}
