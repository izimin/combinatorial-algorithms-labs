package sample.exceptions;

public class NotFoundItemException extends Exception {
    public NotFoundItemException() {
        super("Элемент не найден!");
    }
}
