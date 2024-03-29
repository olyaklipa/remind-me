package olya.app.remindme.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import olya.app.remindme.exception.AuthenticationException;
import olya.app.remindme.model.Token;
import olya.app.remindme.repository.TokenRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    private final JwtDecoder jwtDecoder;
    private final JwtToPrincipalConverter jwtToPrincipalConverter;
    private final TokenRepository tokenRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Optional<String> jwtToken = extractTokenFromRequest(request);

        Optional<UserPrincipalAuthenticationToken> authentication = jwtToken
                .map(jwtDecoder::decodeAndVerify)
                .map(jwtToPrincipalConverter::convert)
                .map(UserPrincipalAuthenticationToken::new);

        if (authentication.isPresent() && !isTokenRevoked(jwtToken.get())) {
            SecurityContextHolder.getContext().setAuthentication(authentication.get());
        }
//        else {
//            throw new AuthenticationException("JWT verification failed");
//        }
//        extractTokenFromRequest(request)
//                .map(jwtDecoder::decodeAndVerify)
//                .map(jwtToPrincipalConverter::convert)
//                .map(UserPrincipalAuthenticationToken::new)
//                .ifPresent(authentication -> SecurityContextHolder.getContext().setAuthentication(authentication));

        filterChain.doFilter(request, response);
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
       String token = request.getHeader(AUTHORIZATION_HEADER);
       if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
           return Optional.of(token.substring(BEARER.length()));
       }
       return Optional.empty();
    }

    private boolean isTokenRevoked (String jwtToken) {
        return tokenRepository.findByToken(jwtToken)
                .map(Token::isRevoked).orElse(true);
    }
}
