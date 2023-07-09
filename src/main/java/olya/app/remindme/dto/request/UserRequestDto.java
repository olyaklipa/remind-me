package olya.app.remindme.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRequestDto {
    @NotNull
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String phoneNumber;
    @Size(min = 4)
    private String password;
    @Size(min = 4)
    private String repeatPassword;
}
