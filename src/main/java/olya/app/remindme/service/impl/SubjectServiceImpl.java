package olya.app.remindme.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.SubjectRequestDto;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.exception.ExistedEntityException;
import olya.app.remindme.model.Subject;
import olya.app.remindme.repository.SubjectRepository;
import olya.app.remindme.service.SubjectService;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.SubjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;
    private final UserService userService;


    @Override
    @Transactional
    public Subject create(Long userId, SubjectRequestDto subjectRequestDto) {
        existedSubjectNameForUser(userId, subjectRequestDto.getName());
        Subject subject = new Subject();
        subject.setUser(userService.getById(userId));
        subject = subjectMapper.mapToEntity(subject, subjectRequestDto);
        return subjectRepository.save(subject);
    }

    @Override
    @Transactional
    public Subject update(Long userId, Long id, SubjectRequestDto subjectRequestDto) {
        Subject subject = getById(userId, id);
        if(subject.getName().equals(subjectRequestDto.getName())) {
            return subject;
        } else {
            existedSubjectNameForUser(userId, subjectRequestDto.getName());
            subject = subjectMapper.mapToEntity(subject, subjectRequestDto);
            return subjectRepository.save(subject);
        }
    }

    @Override
    @Transactional
    public void delete(Long userId, Long id) {
        subjectRepository.delete(getById(userId, id));
    }

    @Override
    @Transactional(readOnly = true)
    public Subject getById(Long userId, Long id) {
        return subjectRepository.findByUserIdAndId(userId, id)
                .orElseThrow(() -> new EntityNotFoundException("The subject with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Subject> getAll(Long userId) {
        return subjectRepository.findByUserId(userId);
    }

    private void existedSubjectNameForUser(Long userId, String name) {
        subjectRepository.findByUserIdAndName(userId, name)
                .ifPresent(subject -> {throw new ExistedEntityException("The subject with name " + name + " already exist");});
    }
}
