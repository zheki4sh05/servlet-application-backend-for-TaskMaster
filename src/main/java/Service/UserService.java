package Service;

import DAO.UserDao;
import DTO.CreateUserDTO;
import DTO.User;
import DTO.UserDTO;
import Mapper.CreateUserMapper;
import Mapper.UserMapper;
import Validator.CreateUserValidator;
import Validator.ValidationException;
import Validator.ValidationResult;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {
    public static UserService getINSTANCE() {
        return INSTANCE;
    }

    private static final UserService INSTANCE  = new UserService();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getINSTANCE();
    private final UserDao userDao = UserDao.getInstance();
    private final CreateUserValidator createUserValidator  =CreateUserValidator.getInstance();
    private final UserMapper userMapper= UserMapper.getInstance();
    public Integer create(CreateUserDTO createUserDTO){
        ValidationResult validationResult = createUserValidator.isValid(createUserDTO);
        if(!validationResult.isValid()){
           throw new ValidationException(validationResult.getErrors());
        }
        User user = createUserMapper.mapFrom(createUserDTO);
        userDao.save(user);
        return user.getId();
    }

    public UserDTO login(String login, String password) {
        return userMapper.mapFrom(userDao.findByEmailAndPassword(login, password));
    }

    public boolean findByLogin(String login) {

       return userDao.hasUserLogin(login);

    }

    public void update(User user) {
        userDao.update(user);
    }
}
