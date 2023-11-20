package olya.app.remindme.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class LoginRequest {
    @Email
    private String email;
    @Size(min = 4)
    private String password;
}
