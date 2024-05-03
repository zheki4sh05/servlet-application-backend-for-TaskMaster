package DTO;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateUserDTO {
    private String login;
    private String password;
}
