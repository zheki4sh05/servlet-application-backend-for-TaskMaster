package DAO;

import Utils.DataBase;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;

public class ProjectsDao {
    private static final ProjectsDao INSTANCE = new ProjectsDao();
    private ProjectsDao(){};
    public static ProjectsDao getInstance(){
        return INSTANCE;
    }
    private static final String GET_BY_LOGIN_AND_PASSOWRD_SQL = "SELECT data FROM user WHERE login = ? AND password = ?";
    public String findUserProjectsData(String login, String password){
        String data=null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
            try (Connection conn = DriverManager.getConnection(DataBase.URL.value, DataBase.NAME.value, DataBase.PWD.value)){
                PreparedStatement preparedStatement = conn.prepareStatement(GET_BY_LOGIN_AND_PASSOWRD_SQL);
                preparedStatement.setObject(1, login);
                preparedStatement.setObject(2,password);
                ResultSet resultSet = preparedStatement.executeQuery();

                if(resultSet.next()){
                    data = resultSet.getString("data");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return data;
    }
}
