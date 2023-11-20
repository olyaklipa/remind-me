package olya.app.remindme.service;

import olya.app.remindme.dto.response.LoginResponse;

public interface AuthService {
    LoginResponse attemptLogin(String email, String password);
    void logout(String email);
}
