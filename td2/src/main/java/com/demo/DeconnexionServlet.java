package com.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/Deconnexion")
public class DeconnexionServlet extends HttpServlet {

    @Override
    //点击链接使用get
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        //获取session false 如果session不存在 不要新建！！
        HttpSession session = req.getSession(false);
        //销毁session
        if (session != null) {
            session.invalidate();
        }
        //回到登录页面
        resp.sendRedirect("connexion.html");
    }
}