package Service;

import DAO.SubTuskDao;
import DAO.TaskDao;
import DTO.SubTaskDTO;
import DTO.TaskDTO;
import Entity.SubTask;

import java.util.List;
import java.util.stream.Collectors;

public class UserSubTaskService {
    private SubTuskDao subtaskDao = SubTuskDao.getInstance();
    private final static UserSubTaskService INSTANSE = new UserSubTaskService();
    public static UserSubTaskService getInstance(){
        return INSTANSE;
    }

    public List<SubTaskDTO> findAllByTaskId(int projectId){
        return subtaskDao.findAllById(projectId).stream().map(task ->
                new SubTaskDTO(task.getId(),task.getName(),task.isDone())
        ).collect(Collectors.toList());
    }

    public void update(SubTask subTask) {
        subtaskDao.update(subTask);
    }

    public SubTask save(SubTask subTask) {
        System.out.println(subTask);
        return subtaskDao.save(subTask);
    }

    public void delete(int id) {
        subtaskDao.delete(id);
    }
}
