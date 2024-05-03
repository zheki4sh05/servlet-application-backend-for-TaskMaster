package Validator;

import DAO.UserDao;
import DTO.CreateUserDTO;
import Service.UserService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDTO>{
    private UserDao userDao = UserDao.getInstance();
    @Getter
    private static final CreateUserValidator INSTANCE = new CreateUserValidator();
    public static CreateUserValidator getInstance(){
        return INSTANCE;
    }
    public ValidationResult isValid(CreateUserDTO createUserDTO){
        ValidationResult validationResult = new ValidationResult();
        if(userDao.hasUserLogin(createUserDTO.getLogin()))
            validationResult.add("Пользователь с таким логином уже существует!");
        return validationResult;
    }
}
