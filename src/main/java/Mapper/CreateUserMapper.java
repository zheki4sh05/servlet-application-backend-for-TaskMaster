package Mapper;

import DTO.CreateUserDTO;
import DTO.User;

public class CreateUserMapper implements Mapper<User, CreateUserDTO> {
    private static final CreateUserMapper INSTANCE  = new CreateUserMapper();
    private CreateUserMapper(){};
    @Override
    public User mapFrom(CreateUserDTO createUserDTO) {
        return User.builder()
                .login(createUserDTO.getLogin())
                .password(createUserDTO.getPassword())
                .build();
    }
    public static CreateUserMapper getINSTANCE() {
        return INSTANCE;
    }

}
