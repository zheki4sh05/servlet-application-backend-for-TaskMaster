package Service;

import DAO.ProjectDao;
import DAO.TaskDao;
import DTO.ProjectDTO;
import DTO.TaskDTO;
import Entity.Project;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UserProjectService {
    private static final UserProjectService INSTANCE = new UserProjectService();
    private final ProjectDao projectDao = ProjectDao.getInstance();
    private final UserTasksService userTasksService = UserTasksService.getInstance();
    private final TaskStatusesService taskStatusesService = TaskStatusesService.getInstance();
//    public List<ProjectDTO> findAll(){
//        return projectDao.findAll().stream().map(project ->
//            new ProjectDTO(project.getId(),project.getName(),project.getColor())
//        ).collect(Collectors.toList());
//    }


    public  List<ProjectDTO> findAllById(int id){
        List<ProjectDTO> projectDTOList = new ArrayList<>();
        projectDao.findAllByID(id).forEach(project -> {
            projectDTOList.add(new ProjectDTO(
                    (long) project.getId(),
                    project.getName(),
                    project.getColor(),
                    userTasksService.findAllByProjectId(project.getId()),
                    taskStatusesService.findAllById(project.getId())
            ));
        });
//        return projectDao.findAll().stream().map(project ->
//                List<TaskDTO> taskDtos = userTasksService.findAllByProjectId(id);
//                    projectDTOList.add(new ProjectDTO(
//
//
//                    ))
//        ).collect(Collectors.toList());
        return projectDTOList;
    }
    public void update(Project project){
        projectDao.update(project);
    }
    public static UserProjectService getInstance(){
        return INSTANCE;
    }

    public Project save(Project project) {
       return projectDao.save(project);
    }

    public void delete(int id) {
        if( projectDao.delete(id))
            System.out.println("Удалено!!!!");

    }
}
