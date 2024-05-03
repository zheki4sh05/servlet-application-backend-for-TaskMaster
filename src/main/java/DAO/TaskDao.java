package DAO;

import Entity.Project;
import Entity.Task;
import Service.TaskStatusesService;
import Service.UserTasksService;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskDao implements Dao<Integer, Task> {

    private String findst = "select id from status where name = ? and project_st_id = ?;";

    private final static String DEL_SQL = "DELETE from task WHERE id = ?";
    private  final String SAVE_SQL=
            "INSERT task (name,description,project_id,date_to, status) VALUES(?,?,?,?,?);";
    private final static String FIND_ALL_TASKS = "SELECT t.id,t.name,t.description,t.date_to, st.name as status from task t join status st on t.status=st.id where t.project_id = ? ;";
    private final static String FIND_BY_ID = FIND_ALL_TASKS + "WHERE project_id=?";
    private final static String UPDATE_SQL = "UPDATE task SET name = ?, description = ?, date_to = ?, status = ? WHERE id = ? ;";
    private static final TaskDao INSTANCE = new TaskDao();
    public static TaskDao getInstance(){
        return INSTANCE;
    }
    private TaskDao(){};
    @Override
    public boolean update(Task task) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2,task.getDesc());
            preparedStatement.setString(3,task.getDateto());
            preparedStatement.setInt(4,findStatus(task.getStatus(), task.getLink()));
            preparedStatement.setInt(5,task.getId());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Task> findAll() {
        return null;
    }
    public List<Task> findAllById(int id){
        try(Connection connection = ConnectionManager.get()){
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_TASKS);
            preparedStatement.setObject(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while (resultSet.next()){
                tasks.add(buildTask(resultSet));
            }
            return  tasks;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public Task findById(Integer id) {
        return null;
    }

    private int findStatus(String name, int projectid){
        int id=0;
        try(Connection connection = ConnectionManager.get()){
            PreparedStatement preparedStatement = connection.prepareStatement(findst);
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,projectid);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println("resultSet "+resultSet.getInt(1));
            System.out.println("projectid "+projectid);
             id =  resultSet.getInt(1);
             System.out.println("id "+id );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    @Override
    public Task save(Task task) {
        int id =0;
        try(Connection connectionManager = ConnectionManager.get();
            PreparedStatement preparedStatement  = connectionManager.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,task.getName());
            preparedStatement.setString(2,task.getDesc());
            preparedStatement.setInt(3,task.getLink());
            preparedStatement.setString(4,task.getDateto());
            preparedStatement.setInt(5, findStatus(task.getStatus(), task.getLink()));
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            id =generatedKeys.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        task.setId(id);
        return task;

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

    private Task buildTask(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("description"),
                resultSet.getString("status"),
                resultSet.getString("date_to")
        );
    }
}
