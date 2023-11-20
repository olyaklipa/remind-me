package olya.app.remindme.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import olya.app.remindme.exception.MyAuthenticationException;
import olya.app.remindme.model.Token;
import olya.app.remindme.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER = "Bearer ";
    @Autowired
    private PublicEndpointsConfig publicEndpointsConfig;
    @Autowired
    private JwtDecoder jwtDecoder;
    @Autowired
    private JwtToUserConverter jwtToUserConverter;
    @Autowired
    private TokenRepository tokenRepository;
    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver exceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            Optional<String> jwtToken = extractTokenFromRequest(request);
            UserPrincipalAuthenticationToken authentication = jwtToken
                    .map(jwtDecoder::decodeAndVerify)
                    .map(jwtToUserConverter::convert)
                    .map(UserPrincipalAuthenticationToken::new).orElseThrow(() -> new MyAuthenticationException("JWT verification has failed"));
            isTokenRevoked(jwtToken.get());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (MyAuthenticationException ex) {
            exceptionResolver.resolveException(request, response, null, ex);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return publicEndpointsConfig.getPublicEndpoints()
                .stream().anyMatch(url -> new AntPathRequestMatcher(url).matches(request));
    }

    private Optional<String> extractTokenFromRequest(HttpServletRequest request) {
       String token = request.getHeader(AUTHORIZATION_HEADER);
       if (StringUtils.hasText(token) && token.startsWith(BEARER)) {
           return Optional.of(token.substring(BEARER.length()));
       }
       return Optional.empty();
    }

    private void isTokenRevoked (String jwtToken) {
        tokenRepository.findByToken(jwtToken)
                .filter(Token::isRevoked) // Check if isRevoked is true
                .ifPresent(token -> {
                    throw new MyAuthenticationException("Token has been revoked");
                });
    }
}
