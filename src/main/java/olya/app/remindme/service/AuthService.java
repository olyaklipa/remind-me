package olya.app.remindme.service;

import olya.app.remindme.model.LoginResponse;

public interface AuthService {
    LoginResponse attemptLogin(String email, String password);
}
