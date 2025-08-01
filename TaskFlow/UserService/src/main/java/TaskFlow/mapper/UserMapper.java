package TaskFlow.mapper;

import TaskFlow.dto.RegisterRequest;
import TaskFlow.dto.UserDto;
import TaskFlow.dto.UserUpdateDto;
import TaskFlow.model.Role;
import TaskFlow.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole(),
                user.getCreatedAt()
        );
    }

    public User toEntity(RegisterRequest registerRequest) {
        if (registerRequest == null) {
            return null;
        }

        return User.builder()
                .username(registerRequest.getUsername())
                .email(registerRequest.getEmail())
                .password(registerRequest.getPassword())
                .role(Role.USER)
                .build();
    }

    public void updateUserFromDto(UserUpdateDto updateDto, User user) {
        if (updateDto == null || user == null) {
            return;
        }

        if (updateDto.getUsername() != null) {
            user.setUsername(updateDto.getUsername());
        }
        if (updateDto.getUsername() != null) {
            user.setEmail(updateDto.getEmail());
        }
    }
}