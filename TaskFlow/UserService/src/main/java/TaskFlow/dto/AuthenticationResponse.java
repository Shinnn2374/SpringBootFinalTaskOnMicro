package TaskFlow.dto;

import TaskFlow.model.Role;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
    private String token;
    private Long userId;
    private String username;
    private String email;
    private Role role;
}
