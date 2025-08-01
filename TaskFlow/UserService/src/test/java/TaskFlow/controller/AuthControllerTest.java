package TaskFlow.controller;

import TaskFlow.controller.AuthController;
import TaskFlow.dto.AuthenticationResponse;
import TaskFlow.model.Role;
import TaskFlow.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void login_ShouldReturnToken_WhenValidCredentials() throws Exception {
        AuthenticationResponse response = new AuthenticationResponse("token", 1L, "user", "user@example.com", Role.USER);
        when(authService.authenticate(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "username": "user",
                        "password": "password"
                    }"""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token"));
    }
}