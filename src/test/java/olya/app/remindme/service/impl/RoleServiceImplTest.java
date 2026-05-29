package olya.app.remindme.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Role;
import olya.app.remindme.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role buildRole(Long id, Role.RoleName roleName) {
        return Role.builder()
                .id(id)
                .roleName(roleName)
                .build();
    }

    @Test
    void testAdd_Success() {
        //when -> return mocks
        Role.RoleName roleName = Role.RoleName.USER;
        Role mockRole = buildRole(1L, roleName);
        when(roleRepository.save(any(Role.class))).thenReturn(mockRole);
        // acting
        Role result = roleService.add(roleName);
        //ensure the repositories methods are called + get captured value
        ArgumentCaptor<Role> captor = ArgumentCaptor.forClass(Role.class);
        verify(roleRepository).save(captor.capture());
        Role capturedRole = captor.getValue();
        //step1: input vs captured value
        assertThat(capturedRole.getRoleName()).isEqualTo(roleName);
        //step2: expected vs return value
        assertThat(result).usingRecursiveComparison().isEqualTo(mockRole);
    }

    @Test
    void testGetByName_Success() {
        //when -> return mocks
        String roleName = "USER";
        Role mockRole = buildRole(1L, Role.RoleName.USER);
        when(roleRepository.findByRoleName(Role.RoleName.valueOf(roleName))).thenReturn(Optional.of(mockRole));
        // acting
        Role result = roleService.getByName(roleName);
        // ensure the repositories methods are called (not required, but better for explicit call)
        verify(roleRepository).findByRoleName(Role.RoleName.USER);
        assertThat(result).usingRecursiveComparison().isEqualTo(mockRole);
    }

    @Test
    void testGetByName_RoleNotFound() {
        //when -> return mocks
        String roleName = "ADMIN";
        when(roleRepository.findByRoleName(Role.RoleName.valueOf(roleName))).thenReturn(Optional.empty());
        // acting + verifying exception
        assertThatThrownBy(() -> roleService.getByName(roleName))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Role ADMIN not found");
        // ensure the repositories methods are called (not required, but better for explicit call)
        verify(roleRepository).findByRoleName(Role.RoleName.valueOf(roleName));
    }

    @Test
    void testGetByName_InvalidRole() {
        //when -> return mocks
        String roleName = "INVALID_ROLE";
        // acting + verifying exception
        assertThatThrownBy(() -> roleService.getByName(roleName))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Role INVALID_ROLE not found");
        // ensure the repositories methods are NOT called
        verify(roleRepository, never()).findByRoleName(any());
    }

    @Test
    void testGetAll_Success() {
        //when -> return mocks
        Role mockRole = buildRole(1L, Role.RoleName.USER);
        Role anotherRole = buildRole(2L, Role.RoleName.ADMIN);
        when(roleRepository.findAll()).thenReturn(List.of(mockRole, anotherRole));
        // acting
        List<Role> result = roleService.getAll();
        // ensure the repositories methods are called (not required, but better for explicit call)
        verify(roleRepository).findAll();
        assertThat(result).hasSize(2);
        assertThat(result).containsExactly(mockRole, anotherRole);
    }
}
