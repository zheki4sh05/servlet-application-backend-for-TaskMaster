package com.example.servletapplication;

import DTO.CreateUserDTO;
import DTO.UserDTO;
import Service.UserService;
import Utils.UrlPath;
import Validator.ValidationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService =UserService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        super.doGet(req,resp);
        //req.getRequestDispatcher(JSPHelper.getPath()).forward(req,resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

        //userService.login(req.getParameter("email"), req.getParameter("password")).ifPresentOrElse(userDTO -> onLoginSuccess(userDTO,req,resp), ()->onloginFail(req,resp));
       UserDTO userDTO = userService.login(req.getParameter("login"), req.getParameter("password"));
       System.out.println(userDTO);
       if(userDTO!=null){
           onLoginSuccess(userDTO,req,resp);
       }else{
           onloginFail(req,resp);
       }
    }

    private void onloginFail(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setStatus(401);
            resp.getWriter().write("Неверный логин или пароль!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onLoginSuccess(UserDTO userDTO, HttpServletRequest req, HttpServletResponse resp) {
        req.getSession().setAttribute("user",userDTO);
        try {
            System.out.println("userDTO.getId() "+userDTO.getId());
            resp.getWriter().write(String.valueOf(userDTO.getId()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
