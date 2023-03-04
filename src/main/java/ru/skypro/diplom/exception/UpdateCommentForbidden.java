package ru.skypro.diplom.exception;

public class UpdateCommentForbidden extends RuntimeException {
    public UpdateCommentForbidden() {
        super("You don't have rights to update the comment");
    }
}
