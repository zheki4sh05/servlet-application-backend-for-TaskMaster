package com.example.servletapplication;

import DAO.ProjectsDao;
import DTO.*;
import Entity.Project;
import Entity.Status;
import Entity.SubTask;
import Entity.Task;
import Service.*;
import Validator.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/projects")
public class ProjectsTransferServlet extends HttpServlet {
    private final UserDataService userDataService = UserDataService.getInstance();
    private final UserProjectService userProjectService = UserProjectService.getInstance();
    private final UserTasksService userTasksService = UserTasksService.getInstance();
    private final UserSubTaskService userSubTaskService = UserSubTaskService.getInstance();
    private final TaskStatusesService taskStatusesService = TaskStatusesService.getInstance();
    private final UserService userService = UserService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        resp.setContentType("application/json");
        Writer writer = resp.getWriter();
        System.out.println("Заппос данных!");
        List<ProjectDTO> userProjectDTO =userDataService.getProjects((UserDTO)req.getSession().getAttribute("user"));
        writer.write(userDataService.mapPOJOToJSON(userProjectDTO));
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String type = req.getParameter("type");
            String method = req.getParameter("method");
            int id;
            switch (method){
                case "update":{
                    makeUpdate(type, req);
                    break;
                }
                case "delete":{
                      makeDel(type, req);
                    break;
                }
                case "add":{
                    id =  makeAdd(type,req);
                    resp.getWriter().write(String.valueOf(id));
                    break;
                }
            }


    }
    private void makeDel(String type,HttpServletRequest req){

        switch (type){
            case "project":{
                userProjectService.delete(Integer.parseInt(req.getParameter("id")));
                break;
            }
            case "task":{
                userTasksService.delete(Integer.parseInt(req.getParameter("id")));
                break;
            }
            case "subtask":{

                userSubTaskService.delete(Integer.parseInt(req.getParameter("id")));
                break;
            }
            case "status":{
                String name = req.getParameter("name");
                int prId = Integer.parseInt(req.getParameter("id"));
                List<StatusDTO> st = taskStatusesService.findAllById(prId).stream().filter(item->item.getName().equals(name)).collect(Collectors.toList());
                System.out.println(st.get(0));
                taskStatusesService.delete(st.get(0).getId());
                break;
            }
        }


    }
    private void makeUpdate(String type,HttpServletRequest req){
        switch (type){
            case "project":{
                userProjectService.update(new Project(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("color")
                ));

                break;
            }
            case "task":{
                Task task = new Task(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("desc"),
                        req.getParameter("status"),
                        req.getParameter("date")
                );
                task.setlinkId(Integer.parseInt(req.getParameter("link")));
                System.out.println(task);
                userTasksService.update(task);

                break;
            }
            case "subtask":{

                userSubTaskService.update(new SubTask(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        Integer.parseInt(req.getParameter("checked"))
                ));
                break;
            }
            case "status":{
                taskStatusesService.update(new Status(
                        Integer.parseInt(req.getParameter("id")),
                        req.getParameter("name"),
                        req.getParameter("oldName")
                ));
                break;
            }
            case "user":{
                User user = new User();

                user.setLogin(req.getParameter("login"));
                user.setId(Integer.parseInt(req.getParameter("id")));

                userService.update(user);
                break;
            }
        }
    }
    private int  makeAdd(String type,HttpServletRequest req){
        switch (type){
            case "project":{
                Project project  = new Project();
                project.setName(req.getParameter("name"));
                project.setColor(req.getParameter("color"));
                project.setlinkId(Integer.parseInt(req.getParameter("link")));
               return userProjectService.save(project).getId();
            }
            case "task":{
                Task task = new Task();
                task.setName( req.getParameter("name"));
                task.setDesc(req.getParameter("desc"));
                task.setDateto( req.getParameter("date"));
                task.setStatus(req.getParameter("status"));
                task.setlinkId(Integer.parseInt(req.getParameter("link")));
               return userTasksService.save(task).getId();
            }
            case "subtask":{
                SubTask subTask = new SubTask();
                subTask.setName(req.getParameter("name"));
                subTask.setDone(Integer.parseInt(req.getParameter("checked")));
                subTask.setlinkId(Integer.parseInt(req.getParameter("link")));
                return userSubTaskService.save(subTask).getId();
            }
            case "status":{
                Status status = new Status();
                status.setStatusName( req.getParameter("name"));
                status.setlinkId(Integer.parseInt(req.getParameter("link")));
               return taskStatusesService.save(status).getId();
            }
            default:{
                return 0;
            }
        }
    }

}
