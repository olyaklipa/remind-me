package olya.app.remindme.exception;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //400
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleFieldException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CustomRequestException.class)
    public Map<String, String> handleCustomRequestException(CustomRequestException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return errors;
    }

    //404
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public Map<String, String> handleNotFoundException(EntityNotFoundException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return errors;
    }

    //401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MyAuthenticationException.class)
    public Map<String, String> handleMyAuthenticationException(MyAuthenticationException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return errors;
    }

    //401
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    public Map<String, String> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return errors;
    }

    //409
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(ExistedEntityException.class)
    public Map<String, String> handleExistedUserException(ExistedEntityException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return errors;
    }

}
