package Service;

import DAO.ProjectDao;
import DAO.TaskDao;
import DTO.TaskDTO;
import Entity.Task;

import java.util.List;
import java.util.stream.Collectors;

public class UserTasksService {
  private TaskDao taskDao = TaskDao.getInstance();
  private UserSubTaskService userSubTaskService = UserSubTaskService.getInstance();
  private final static UserTasksService INSTANSE = new UserTasksService();
  public static UserTasksService getInstance(){
      return INSTANSE;
  }
  public List<TaskDTO> findAllByProjectId(int projectId){
      return taskDao.findAllById(projectId).stream().map(task ->
          new TaskDTO(
                  task.getId(),
                  task.getName(),
                      task.getDesc(),
                      task.getStatus(),
                      task.getDatefrom(),
                      task.getDateto(),
                      userSubTaskService.findAllByTaskId(task.getId()))
      ).collect(Collectors.toList());
  }
  public void update(Task task){
      taskDao.update(task);
  }

    public Task save(Task task) {
      return taskDao.save(task);
    }

    public void delete(int id) {
      taskDao.delete(id);
    }
}
