package olya.app.remindme.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import olya.app.remindme.exception.EntityNotFoundException;
import olya.app.remindme.model.Token;
import olya.app.remindme.model.User;
import olya.app.remindme.repository.TokenRepository;
import olya.app.remindme.repository.UserRepository;
import olya.app.remindme.service.TokenService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {
    private static final Duration ONE_DAY = Duration.of(1, ChronoUnit.DAYS);
    private static final Duration TWO_DAYS = Duration.of(2, ChronoUnit.DAYS);
    @Mock
    private UserRepository userRepository;

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private TokenServiceImpl tokenService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User();
        ReflectionTestUtils.setField(mockUser, "id", 1L);
        mockUser.setEmail("test@example.com");
        mockUser.setActive(true);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testCreateTokenEntity_Success() {
        String tokenValue = "sample-token";
        Instant expiresAt = Instant.now().plus(ONE_DAY);
        String email = "test@example.com";

        when(userRepository.findByEmailAndActiveTrue(email)).thenReturn(Optional.of(mockUser));
        tokenService.createTokenEntity(tokenValue, expiresAt, email);
        ArgumentCaptor<Token> tokenCaptor = ArgumentCaptor.forClass(Token.class);

        verify(tokenRepository).save(tokenCaptor.capture());
        Token capturedToken = tokenCaptor.getValue();
        assertEquals(tokenValue, capturedToken.getToken());
        assertFalse(capturedToken.isRevoked());
        assertEquals(Timestamp.from(expiresAt), capturedToken.getValidUntil());
        assertEquals(mockUser, capturedToken.getUser());
    }

    @Test
    void testCreateTokenEntity_UserNotFound() {
        String tokenValue = "sample-token";
        Instant expiresAt = Instant.now().plus(ONE_DAY);
        String email = "nonexistent@example.com";

        when(userRepository.findByEmailAndActiveTrue(email)).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () ->
                tokenService.createTokenEntity(tokenValue, expiresAt, email)
        );

        verify(tokenRepository, never()).save(any());
    }

    @Test
    void testRevokeUserTokens_TokensExist() {
        Token token1 = new Token();
        token1.setId(1L);
        token1.setRevoked(false);

        Token token2 = new Token();
        token2.setId(2L);
        token2.setRevoked(false);

        when(tokenRepository.findByUserIdAndRevokedFalse(mockUser.getId()))
                .thenReturn(List.of(token1, token2));
        tokenService.revokeUserTokens(mockUser);

        assertTrue(token1.isRevoked());
        assertTrue(token2.isRevoked());
        verify(tokenRepository).saveAll(List.of(token1, token2));
    }

    @Test
    void testRevokeUserTokens_NoTokensFound() {
        when(tokenRepository.findByUserIdAndRevokedFalse(mockUser.getId()))
                .thenReturn(Collections.emptyList());
        tokenService.revokeUserTokens(mockUser);

        verify(tokenRepository, never()).saveAll(any());
    }

    @Test
    void testClearExpiredTokenEntities() {
        Token expiredToken1 = new Token();
        expiredToken1.setValidUntil(Timestamp.from(Instant.now().minus(ONE_DAY)));

        Token expiredToken2 = new Token();
        expiredToken2.setValidUntil(Timestamp.from(Instant.now().minus(TWO_DAYS)));

        when(tokenRepository.findByValidUntilBefore(any(Timestamp.class)))
                .thenReturn(List.of(expiredToken1, expiredToken2));
        tokenService.clearExpiredTokenEntities();

        verify(tokenRepository).deleteAll(List.of(expiredToken1, expiredToken2));
    }
}