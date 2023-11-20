package olya.app.remindme.service.mapper;

import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.dto.response.UserResponseDto;
import olya.app.remindme.model.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto mapToDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        return userResponseDto;
    }

    public User mapToUser(User user, UserRequestDto userRequestDto) {
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        user.setEmail(userRequestDto.getEmail());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        return user;
    }

}
