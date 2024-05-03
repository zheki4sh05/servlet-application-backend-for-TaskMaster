package DAO;

import Entity.Status;
import Entity.SubTask;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SubTuskDao implements Dao<Integer, SubTask>{
    private final static String DEL_SQL = "DELETE from subtask WHERE id = ?;";
    private  final String SAVE_SQL=
            "INSERT INTO subtask (name,task_id,isdone) VALUES(?, ?,?);";
    private String FIND_ALL_BY_ID = "select s.id, s.name, s.isdone from subtask s where task_id = ? ;";
    private final static SubTuskDao INSTANCE = new SubTuskDao();
    private final static String UPDATE_SQL = "UPDATE subtask SET name = ?, isdone = ? WHERE id = ? ;";
    private SubTuskDao(){}
    public static SubTuskDao getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean update(SubTask subTask) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ){
            preparedStatement.setString(1,subTask.getName());
            preparedStatement.setInt(2,subTask.isDone());
            preparedStatement.setInt(3,subTask.getId());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<SubTask> findAll() {
        return null;
    }

    @Override
    public SubTask findById(Integer id) {
        return null;
    }

    @Override
    public SubTask save(SubTask subTask) {
        try(Connection connectionManager = ConnectionManager.get();
            PreparedStatement preparedStatement  = connectionManager.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,subTask.getName());
            preparedStatement.setInt(2,subTask.getLink());
            preparedStatement.setInt(3,subTask.isDone());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            subTask.setId(generatedKeys.getInt(1));


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return subTask;
    }

    @Override
    public boolean delete(Integer id) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(DEL_SQL)
        ){
            preparedStatement.setObject(1, id);
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public List<SubTask> findAllById(int taskId) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_BY_ID)
        ){
            preparedStatement.setInt(1,taskId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<SubTask> subTaskArrayList = new ArrayList<>();
            while (resultSet.next()){
                subTaskArrayList.add(buildSubTask(resultSet));
            }
            return  subTaskArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private SubTask buildSubTask(ResultSet resultSet) throws SQLException {
        return new SubTask(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getInt("isdone")
        );
    }
}
