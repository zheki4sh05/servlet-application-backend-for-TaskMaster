package com.example.servletapplication;

import DTO.CreateUserDTO;
import Service.UserService;
import Utils.UrlPath;
import Validator.ValidationException;
import com.sun.net.httpserver.HttpsServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.rowset.serial.SerialException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

@WebServlet(urlPatterns = UrlPath.REGISTRATION)
public class RegistrationServlet extends HttpServlet {
    private final UserService userService = UserService.getINSTANCE();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{

        String login = req.getParameter("login");

        if(userService.findByLogin(login)){
            resp.getWriter().write("1");
        }else{
            resp.getWriter().write("0");
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDTO userDto = CreateUserDTO.builder()
                .login(req.getParameter("login"))
                .password(req.getParameter("password"))
                .build();
        try {
            resp.getWriter().write(String.valueOf(userService.create(userDto)));;
            resp.setStatus(200);
        }catch (ValidationException e){
            resp.getWriter().write(e.getErrors().get(0));
                resp.setStatus(401);
        }


    }

}
