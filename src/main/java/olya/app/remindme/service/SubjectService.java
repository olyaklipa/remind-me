package olya.app.remindme.service;

import java.util.List;
import olya.app.remindme.dto.request.SubjectRequestDto;
import olya.app.remindme.model.Action;
import olya.app.remindme.model.Subject;

public interface SubjectService {
    Subject create(Long userId, SubjectRequestDto subjectRequestDto);
    Subject update(Long userId, Long id, SubjectRequestDto subjectRequestDto);
    void delete(Long userId, Long id);
    Subject getById(Long userId, Long id);
    List<Subject> getAll(Long userId);
}
