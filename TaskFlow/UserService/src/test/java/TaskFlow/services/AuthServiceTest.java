package TaskFlow.services;

import TaskFlow.auth.JwtUtils;
import TaskFlow.dao.UserRepository;
import TaskFlow.dto.AuthenticationRequest;
import TaskFlow.dto.AuthenticationResponse;
import TaskFlow.dto.RegisterRequest;
import TaskFlow.exception.UserAlreadyExistsException;
import TaskFlow.mapper.UserMapper;
import TaskFlow.model.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthService authService;

    @Test
    void register_ShouldReturnAuthResponse_WhenValidRequest() {
        RegisterRequest request = new RegisterRequest("testuser", "test@example.com", "password");
        User savedUser = User.builder().id(1L).username("testuser").build();

        when(userRepository.existsByUsername(anyString())).thenReturn(false);
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(savedUser);
        when(jwtUtils.generateJwtToken(any(Authentication.class))).thenReturn("jwtToken");

        AuthenticationResponse response = authService.register(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        RegisterRequest request = new RegisterRequest("existing", "test@example.com", "password");
        when(userRepository.existsByUsername(anyString())).thenReturn(true);

        assertThrows(UserAlreadyExistsException.class, () -> authService.register(request));
    }

    @Test
    void authenticate_ShouldReturnAuthResponse_WhenValidCredentials() {
        AuthenticationRequest request = new AuthenticationRequest("testuser", "password");
        User user = User.builder().id(1L).username("testuser").build();

        when(userRepository.findByUsername(anyString()))
                .thenReturn(Optional.of(user));
        when(jwtUtils.generateJwtToken(any(Authentication.class)))
                .thenReturn("jwtToken");

        AuthenticationResponse response = authService.authenticate(request);

        assertNotNull(response);
        assertEquals("jwtToken", response.getToken());
        verify(authenticationManager).authenticate(any());
    }
}