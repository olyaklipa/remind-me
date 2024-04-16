package olya.app.remindme.exception;

public class CustomRequestException extends RuntimeException {
    public CustomRequestException(String message) {
        super(message);
    }
}
