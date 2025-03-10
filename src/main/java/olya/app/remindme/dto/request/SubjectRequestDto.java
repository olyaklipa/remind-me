package olya.app.remindme.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SubjectRequestDto {
    @NotBlank(message = "Subject name is required")
    private String name;
}
