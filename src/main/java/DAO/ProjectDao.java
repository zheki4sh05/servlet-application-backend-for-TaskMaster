package DAO;


import Entity.Project;
import Utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProjectDao implements Dao<Integer,Project>{
    private final static ProjectDao INSTANCE = new ProjectDao();
    private ProjectDao(){}
    public static ProjectDao getInstance(){
        return INSTANCE;
    }
    private final static String SAVE_SQL = "INSERT project (name,color,user_id) VALUES(?,?,?)";
    private final static String DEL_SQL = "DELETE from project WHERE id = ?";
    private final static String FIND_ALL_PROJECTS = "SELECT id,name,color FROM project ";
    private final static String FIND_BY_ID = FIND_ALL_PROJECTS + "WHERE user_id = ?;";
    private final static String UPDATE_SQL = "UPDATE project SET name = ?, color = ? WHERE id = ? ;";
    public Project save(Project project){
        try(Connection connectionManager = ConnectionManager.get();
            PreparedStatement preparedStatement  = connectionManager.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1,project.getName());
            preparedStatement.setString(2,project.getColor());
            preparedStatement.setInt(3,project.getLink());
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            project.setId(generatedKeys.getInt(1));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return project;
    }

    public boolean delete(Integer id){
        try(Connection connection = ConnectionManager.get();
        PreparedStatement preparedStatement = connection.prepareStatement(DEL_SQL)
        ){
            preparedStatement.setObject(1, id);
            int c = preparedStatement.executeUpdate();
            System.out.println(c);
            return c>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    private List<Project> findALL(String sql, int id){
        try(Connection connection = ConnectionManager.get()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Project> projects = new ArrayList<>();
            while (resultSet.next()){
                projects.add(buildProject(resultSet));
            }
            return  projects;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public List<Project> findAll(){
//       return findALL(FIND_ALL_PROJECTS);
        return  new ArrayList<>();
    }
    public List<Project> findAllByID(int id){
        return findALL(FIND_BY_ID,id);
    }

    private Project buildProject(ResultSet resultSet) throws SQLException {
        return new Project(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("color")
        );
    }

    public Project findById(Integer id){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID)
        ){
            preparedStatement.setLong(1,id);
            ResultSet resultSet = preparedStatement.executeQuery();
            Project project = null;
            if(resultSet.next()){
                project=  buildProject(resultSet);
            }
            return  project;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public boolean update(Project project){
        try(Connection connection = ConnectionManager.get();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_SQL)
        ){
            System.out.println("project.getColor() "+project.getColor());
            preparedStatement.setString(1,project.getName());
            preparedStatement.setString(2,project.getColor());
            preparedStatement.setInt(3,project.getId());
            return preparedStatement.executeUpdate()>0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
