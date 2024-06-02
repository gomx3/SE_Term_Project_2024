package SE_team.IssueManager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

class PasswordServiceTest {

    private PasswordService passwordService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        passwordService = new PasswordService(passwordEncoder);
    }

    @Test
    @DisplayName("비밀번호 일치 확인 - 일치하는 경우")
    void isPasswordCorrect_MatchingPassword() {
        // Given
        String rawPassword = "password";
        String encodedPassword = "$2a$10$S/5eZQnx12PyvXO6vm4Fo.5tD.j/8eKQu/3AzNlWT2rCkIdRfYMe2"; // Encoded form of
                                                                                                 // "password"

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(true);

        // When
        boolean result = passwordService.isPasswordCorrect(rawPassword, encodedPassword);

        // Then
        assertTrue(result);
    }

    @Test
    @DisplayName("비밀번호 일치 확인 - 일치하지 않는 경우")
    void isPasswordCorrect_NonMatchingPassword() {
        // Given
        String rawPassword = "password";
        String encodedPassword = "$2a$10$S/5eZQnx12PyvXO6vm4Fo.5tD.j/8eKQu/3AzNlWT2rCkIdRfYMe2"; // Encoded form of
                                                                                                 // "password"

        when(passwordEncoder.matches(rawPassword, encodedPassword)).thenReturn(false);

        // When
        boolean result = passwordService.isPasswordCorrect(rawPassword, encodedPassword);

        // Then
        assertFalse(result);
    }
}