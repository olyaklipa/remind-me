package olya.app.remindme.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailData {
    private String subject;
    private String to;
    private String userFirstname;
    private String actionTitle;
    private String subjectName;
    private String scheduledDate;
    private String template;
    private String id;
}
