package DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@Value
@Builder
public class UserDTO {
    int id;
    String login;
    String password;

}
