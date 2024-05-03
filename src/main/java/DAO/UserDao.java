package DAO;

import DTO.User;
import Utils.ConnectionManager;
import Utils.DataBase;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.List;
import java.util.Optional;
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements Dao<Long,User> {

    private  final String SAVE_SQL=
            "INSERT INTO user (login,password) VALUES(?, ?)";
    private static  final UserDao INSTANCE = new UserDao();
    private  final String GET_BY_LOGIN_AND_PASSOWRD_SQL = "SELECT * FROM user WHERE login = ? AND password = ?;";
    private  final String findByLogin = "select login from user where login  =  ?;";

    private final String UPDATE_SQL = "UPDATE user SET login = ? WHERE id = ? ;";

    public static UserDao getInstance() {
        return INSTANCE;
    }
    public boolean hasUserLogin(String login){
        try (Connection conn = ConnectionManager.get()){
            PreparedStatement preparedStatement = conn.prepareStatement(findByLogin);
            preparedStatement.setObject(1, login);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
                return true;
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
    @Override
    public boolean update(User user) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ){
            preparedStatement.setString(1,user.getLogin());
            preparedStatement.setInt(2,user.getId());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<User> findAll() {
        return null;
    }

    @Override
    public User findById(Long id) {
        return null;
    }

    @Override
    public User save(User user) {
            try (Connection conn = ConnectionManager.get()){
                PreparedStatement preparedStatement = conn.prepareStatement(SAVE_SQL,Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setObject(1, user.getLogin());
                preparedStatement.setObject(2, user.getPassword());
                        int rows = preparedStatement.executeUpdate();
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                generatedKeys.next();
                user.setId(generatedKeys.getInt(1));
                System.out.printf("%d rows added", rows);
            } catch (SQLException e) {
                e.printStackTrace();
            }

        return user;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    ;
    public User findByEmailAndPassword(String login, String password){
        User user=null;
        try (Connection conn = ConnectionManager.get()){
            PreparedStatement preparedStatement = conn.prepareStatement(GET_BY_LOGIN_AND_PASSOWRD_SQL);
            preparedStatement.setObject(1, login);
            preparedStatement.setObject(2,password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                user = buildEntity(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
    private User buildEntity(ResultSet resultSet)throws SQLException{
        return User.builder()
                .id(resultSet.getObject("id",Integer.class))
                .login(resultSet.getObject("login",String.class))
                .password(resultSet.getObject("password",String.class))
                .build();
    }


}
