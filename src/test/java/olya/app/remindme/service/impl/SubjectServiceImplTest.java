package olya.app.remindme.service.impl;

import olya.app.remindme.dto.request.SubjectRequestDto;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.exception.ExistedEntityException;
import olya.app.remindme.model.Subject;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.SubjectRepository;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.SubjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SubjectServiceImplTest {
    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private UserService userService;

    @Spy
    private SubjectMapper subjectMapper;

    @InjectMocks
    private SubjectServiceImpl subjectService;

    @Test
    void testCreate_Success() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // mock dto
        SubjectRequestDto subjectRequestDto = new SubjectRequestDto();
        subjectRequestDto.setName("Senia");
        // mock subject saved
        Subject mockSubject = Subject.builder()
                .id(1L)
                .name("Senia")
                .user(mockUser)
                .build();

        // when -> return mocks
        when(userService.getById(1L)).thenReturn(mockUser);
        when(subjectRepository.save(any(Subject.class))).thenReturn(mockSubject);

        // acting
        Subject result = subjectService.create(1L, subjectRequestDto);

        // ensure the repositories methods are called + get captured value
        ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(captor.capture());
        Subject capturedSubject = captor.getValue();

        // step1: captured value vs input/dto
        assertThat(capturedSubject.getUser().getId()).isEqualTo(1L);
        assertThat(capturedSubject.getName()).isEqualTo(subjectRequestDto.getName());
        // step 2: result vs mock
        assertThat(result).usingRecursiveComparison().isEqualTo(mockSubject);
    }

    @Test
    void testUpdate_Success() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // existing subject
        Subject existingSubject = Subject.builder()
                .id(1L)
                .name("OldName")
                .user(mockUser)
                .build();
        // mock dto
        SubjectRequestDto subjectRequestDto = new SubjectRequestDto();
        subjectRequestDto.setName("NewName");
        // updated subject
        Subject updatedSubject = Subject.builder()
                .id(1L)
                .name("NewName")
                .user(mockUser)
                .build();

        // when -> return mocks
        when(subjectRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.findByUserIdAndName(1L, "NewName")).thenReturn(Optional.empty());
        when(subjectRepository.save(any(Subject.class))).thenReturn(updatedSubject);

        // acting
        Subject result = subjectService.update(1L, 1L, subjectRequestDto);

        // ensure the repository methods are called + capture the value
        ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).save(captor.capture());
        Subject capturedSubject = captor.getValue();

        // step1: captured value vs input/dto
        assertThat(capturedSubject.getId()).isEqualTo(1L);
        assertThat(capturedSubject.getUser().getId()).isEqualTo(1L);
        assertThat(capturedSubject.getName()).isEqualTo(subjectRequestDto.getName());
        // step 2: result vs updated mock
        assertThat(result).usingRecursiveComparison().isEqualTo(updatedSubject);
    }

    @Test
    void testUpdate_ThrowsException_WhenNewNameAlreadyExistsForUser() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // existing subject
        Subject existingSubject = Subject.builder()
                .id(1L)
                .name("OldName")
                .user(mockUser)
                .build();
        // mock dto
        SubjectRequestDto subjectRequestDto = new SubjectRequestDto();
        subjectRequestDto.setName("ExistingName");
        // updated subject
        Subject conflictingSubject = Subject.builder()
                .id(2L)
                .name("ExistingName")
                .user(mockUser)
                .build();

        // when -> return mocks
        when(subjectRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(existingSubject));
        when(subjectRepository.findByUserIdAndName(1L, "ExistingName")).thenReturn(Optional.of(conflictingSubject));

        // ensure the exception is thrown and the repository method is never called
        assertThatThrownBy(() -> subjectService.update(1L, 1L, subjectRequestDto))
                .isInstanceOf(ExistedEntityException.class)
                .hasMessageContaining("The subject with name ExistingName already exist");

        verify(subjectRepository, never()).save(any(Subject.class));
    }

    @Test
    void testDelete_Success() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // mock subject to delete
        Subject subjectToDelete = Subject.builder()
                .id(1L)
                .name("Senia")
                .user(mockUser)
                .build();

        // when -> return mocks
        when(subjectRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(subjectToDelete));

        // acting
        subjectService.delete(1L, 1L);

        // ensure the repositories methods are called + get captured value
        ArgumentCaptor<Subject> captor = ArgumentCaptor.forClass(Subject.class);
        verify(subjectRepository).delete(captor.capture());
        Subject capturedSubject = captor.getValue();

        // captured value vs subjectToDelete
        assertThat(capturedSubject).usingRecursiveComparison().isEqualTo(subjectToDelete);
    }

    @Test
    void testGetById_Success() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // mock subject
        Subject mockSubject = Subject.builder()
                .id(1L)
                .name("Senia")
                .user(mockUser)
                .build();

        // when -> return mocks
        when(subjectRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.of(mockSubject));

        // acting
        Subject result = subjectService.getById(1L, 1L);

        // result vs mock
        assertThat(result).usingRecursiveComparison().isEqualTo(mockSubject);
    }

    @Test
    void testGetById_NotFound() {
        // when -> return empty optional
        when(subjectRepository.findByUserIdAndId(1L, 1L)).thenReturn(Optional.empty());

        // then -> expect exception
        assertThatThrownBy(() -> subjectService.getById(1L, 1L))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("The subject with id 1 not found");
    }

    @Test
    void testGetAll_Success() {
        // creating mocks:
        // mock user
        User mockUser = User.builder().id(1L).build();
        // mock subjects
        List<Subject> mockSubjects = List.of(
                Subject.builder().id(1L).name("Senia").user(mockUser).build(),
                Subject.builder().id(2L).name("Jack").user(mockUser).build()
        );

        // when -> return mocks
        when(subjectRepository.findByUserId(1L)).thenReturn(mockSubjects);

        // acting
        List<Subject> result = subjectService.getAll(1L);

        // assertions
        assertThat(result).hasSize(2);
        assertThat(result).usingRecursiveComparison().isEqualTo(mockSubjects);
    }
}
