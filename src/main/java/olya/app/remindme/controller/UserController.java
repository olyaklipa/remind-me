package olya.app.remindme.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.dto.request.UserRequestDto;
import olya.app.remindme.dto.response.UserResponseDto;
import olya.app.remindme.model.User;
import olya.app.remindme.service.UserService;
import olya.app.remindme.service.mapper.UserMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRequestDto userRequestDto) {
        User user = userService.create(userRequestDto);
        return userMapper.mapToDto(user);
    }

    @PutMapping("/{id}")
    public UserResponseDto update(@PathVariable Long id, @RequestBody UserRequestDto userRequestDto) {
        return null;
    }

    @PutMapping("/deactivate/{id}")
    public void deactivate(@PathVariable Long id) {

    }

    @GetMapping("/by-email")
    public UserResponseDto getByEmail(@RequestParam String email) {
        return null;
    }
}
