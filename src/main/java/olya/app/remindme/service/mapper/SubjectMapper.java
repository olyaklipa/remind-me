package olya.app.remindme.service.mapper;

import olya.app.remindme.dto.request.SubjectRequestDto;
import olya.app.remindme.dto.response.SubjectResponseDto;
import olya.app.remindme.model.Subject;
import org.springframework.stereotype.Component;

@Component
public class SubjectMapper {
    public SubjectResponseDto mapToDto(Subject subject) {
        SubjectResponseDto subjectResponseDto = new SubjectResponseDto();
        subjectResponseDto.setId(subject.getId());
        subjectResponseDto.setName(subject.getName());
        return subjectResponseDto;
    }

    public Subject mapToEntity(Subject subject, SubjectRequestDto subjectRequestDto) {
        subject.setName(subjectRequestDto.getName());
        return subject;
    }


}
