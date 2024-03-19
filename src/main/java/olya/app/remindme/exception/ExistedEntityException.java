package olya.app.remindme.exception;

public class ExistedEntityException extends RuntimeException {
    public ExistedEntityException(String message) {
        super(message);
    }
}
