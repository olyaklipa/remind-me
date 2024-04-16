package olya.app.remindme.security;

import lombok.RequiredArgsConstructor;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmailAndActiveTrue(username)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }
}
