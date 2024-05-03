package Service;

import DAO.StatusNamesDao;
import DAO.SubTuskDao;
import DAO.TaskDao;
import DTO.StatusDTO;
import DTO.SubTaskDTO;
import DTO.TaskDTO;
import Entity.Status;

import java.util.List;
import java.util.stream.Collectors;

public class TaskStatusesService {

    private StatusNamesDao statusNamesDao = StatusNamesDao.getInstance();
    private final static TaskStatusesService INSTANSE = new TaskStatusesService();
    public static TaskStatusesService getInstance(){
        return INSTANSE;
    }
    public List<StatusDTO> findAllById(int projectId){
        return statusNamesDao.findAllByProjectId(projectId).stream().map(status ->
                new StatusDTO(
                        status.getId(),
                        status.getStatusName())
        ).collect(Collectors.toList());
    }

    public void update(Status status) {
        statusNamesDao.update(status);
    }

    public Status save(Status status) {
       return statusNamesDao.save(status);
    }

    public void delete(int id) {
        statusNamesDao.delete(id);
    }
}
