package TaskFlow.controller;

import TaskFlow.dto.AuthenticationResponse;
import TaskFlow.dto.RegisterRequest;
import TaskFlow.model.Role;
import TaskFlow.services.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.RequestEntity.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AuthService authService;

    @Test
    void register_ShouldReturn201_WhenValidRequest() throws Exception {
        RegisterRequest request = new RegisterRequest("newuser", "new@example.com", "password");
        AuthenticationResponse response = new AuthenticationResponse("token", 1L, "newuser", "new@example.com", Role.USER);

        when(authService.register(any())).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                    {
                        "username": "newuser",
                        "email": "new@example.com",
                        "password": "password"
                    }"""))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.token").value("token"));
    }
}