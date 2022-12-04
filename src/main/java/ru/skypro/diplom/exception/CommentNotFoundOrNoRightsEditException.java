package ru.skypro.diplom.exception;

public class CommentNotFoundOrNoRightsEditException extends RuntimeException {
    public CommentNotFoundOrNoRightsEditException() {
        super("The comment was not found or there are no rights to edit it");
    }
}
