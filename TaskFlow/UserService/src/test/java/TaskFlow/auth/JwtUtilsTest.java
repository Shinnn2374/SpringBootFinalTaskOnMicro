package TaskFlow.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class JwtUtilsTest {

    private JwtUtils jwtUtils;

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils("secretsecretsecretsecretsecret", 3600000);
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        UserDetails user = User.withUsername("testuser")
                .password("password")
                .authorities("ROLE_USER")
                .build();
        Authentication auth = new UsernamePasswordAuthenticationToken(user, null);

        String token = jwtUtils.generateJwtToken(auth);

        assertNotNull(token);
        assertEquals("testuser", jwtUtils.getUsernameFromJwtToken(token));
        assertTrue(jwtUtils.validateJwtToken(token));
    }

    @Test
    void validateToken_ShouldReturnFalse_ForInvalidToken() {
        assertFalse(jwtUtils.validateJwtToken("invalid.token.here"));
    }
}