package olya.app.remindme.service.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.dto.response.UserResponseDto;
import olya.app.remindme.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserMapper userMapper;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhoneNumber("1234567890");
        user.setPassword("encodedPassword");
    }

    @Test
    public void shouldMapUserToResponseDto() {
        UserResponseDto dto = userMapper.mapToDto(user);

        assertEquals(user.getId(), dto.getId());
        assertEquals(user.getFirstName(), dto.getFirstName());
        assertEquals(user.getLastName(), dto.getLastName());
        assertEquals(user.getEmail(), dto.getEmail());
        assertEquals(user.getPhoneNumber(), dto.getPhoneNumber());
    }

    @Test
    public void shouldMapRequestDtoToUser() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setFirstName("Jane");
        userRequestDto.setLastName("Doe");
        userRequestDto.setEmail("jane.doe@example.com");
        userRequestDto.setPhoneNumber("987654321");
        userRequestDto.setPassword("password");
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        User mappedUser = userMapper.mapToUser(user, userRequestDto);

        assertEquals(userRequestDto.getFirstName(), mappedUser.getFirstName());
        assertEquals(userRequestDto.getLastName(), mappedUser.getLastName());
        assertEquals(userRequestDto.getEmail(), mappedUser.getEmail());
        assertEquals(userRequestDto.getPhoneNumber(), mappedUser.getPhoneNumber());
        assertEquals("encodedPassword", mappedUser.getPassword());

        verify(passwordEncoder).encode(userRequestDto.getPassword());
    }

}