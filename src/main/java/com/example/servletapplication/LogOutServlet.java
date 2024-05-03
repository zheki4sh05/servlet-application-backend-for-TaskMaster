package com.example.servletapplication;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet("/logout")
public class LogOutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
    resp.getWriter().write("Привет мир");
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
       req.getSession().invalidate();
       resp.sendRedirect("/login");
    }
}
