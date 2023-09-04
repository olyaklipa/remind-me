package olya.app.remindme.service.impl;

import java.util.List;
import olya.app.remindme.model.LoginResponse;
import olya.app.remindme.security.JwtIssuer;
import olya.app.remindme.security.UserPrincipal;
import olya.app.remindme.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(JwtIssuer jwtIssuer, AuthenticationManager authenticationManager) {
        this.jwtIssuer = jwtIssuer;
        this.authenticationManager = authenticationManager;
    }

    public LoginResponse attemptLogin(String email, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
//        List<String> roles = principal.getAuthorities()
//                .stream()
//                .map(GrantedAuthority::getAuthority)
//                .toList();
        String token = jwtIssuer.issue(principal.getUserId(), principal.getEmail());
        return new LoginResponse(token);
    }
}
