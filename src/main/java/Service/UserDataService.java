package Service;

import DAO.ProjectDao;
import DAO.ProjectsDao;
import DTO.ProjectDTO;
import DTO.UserDTO;
import DTO.UserProjectDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;


public class UserDataService {
    private static final UserDataService INSTANCE = new UserDataService();
    private UserDataService(){};
    private final UserProjectService userProjectService = UserProjectService.getInstance();
    public static UserDataService getInstance(){
        return INSTANCE;
    }
//    public UserProjectDTO getProjectsData(String login,String pwd){
////        return new UserProjectDTO(projectsDao.findUserProjectsData(login,pwd));
////    }
    public String mapPOJOToJSON(List<ProjectDTO> userProjectDTO){
        ObjectMapper objectMapper  = new ObjectMapper();
        String data=null;
        try {
            data =  objectMapper.writeValueAsString(userProjectDTO);
        }catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return data;
    }
    public List<ProjectDTO> getProjects(UserDTO userDTO){

        return userProjectService.findAllById(userDTO.getId());
    }
}
