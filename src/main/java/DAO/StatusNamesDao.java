package DAO;

import Entity.Project;
import Entity.Status;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StatusNamesDao implements Dao<Integer, Status> {
    private final static String DEL_SQL = "DELETE from status WHERE id = ?";
    private  final String SAVE_SQL=
            "INSERT INTO status (name,project_st_id) VALUES(?, ?)";
    private String FIND_BY_PROJECT_ID_SQL = "SELECT id, name FROM status WHERE project_st_id=?;";
    private final static String UPDATE_SQL = "UPDATE status SET name = ? WHERE project_st_id = ? and name=?;";
    private final static StatusNamesDao INSTANCE = new StatusNamesDao();
    private StatusNamesDao(){}
    public static StatusNamesDao getInstance(){
        return INSTANCE;
    }
    @Override
    public boolean update(Status status) {
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ){
            preparedStatement.setString(1,status.getStatusName());
            preparedStatement.setInt(2,status.getId());
            preparedStatement.setString(3,status.getOldName());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Status> findAll() {
        return null;
    }

    @Override
    public Status findById(Integer id) {
        return null;
    }

    @Override
    public Status save(Status status) {
        try(Connection connectionManager = ConnectionManager.get();
            PreparedStatement preparedStatement  = connectionManager.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,status.getStatusName());
            preparedStatement.setInt(2,status.getLink());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            status.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
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
    public List<Status> findAllByProjectId(int id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_PROJECT_ID_SQL)
        ){
            preparedStatement.setInt(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Status> statusArrayList = new ArrayList<>();
            while (resultSet.next()){
                statusArrayList.add(buildStatus(resultSet));
            }
            return  statusArrayList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    private Status buildStatus(ResultSet resultSet) throws SQLException {
        return new Status(
                resultSet.getInt("id"),
                resultSet.getString("name")
        );
    }
}
