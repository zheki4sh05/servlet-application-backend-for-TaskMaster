package Mapper;

import DTO.User;
import DTO.UserDTO;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements Mapper<UserDTO, User> {
    private static final UserMapper INSTANCE = new UserMapper();

    public static UserMapper getInstance(){
        return INSTANCE;
    }

    @Override
    public UserDTO mapFrom(User user) {
        if(user!=null){
            return UserDTO.builder()
                    .id(user.getId())
                    .login(user.getLogin())
                    .password(user.getPassword())
                    .build();
        }else{
            return null;
        }

    }
}
