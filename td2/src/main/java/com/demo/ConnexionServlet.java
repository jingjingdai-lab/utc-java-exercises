package com.demo;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/Connexion")
public class ConnexionServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        
        //取出从connexion.html里拿输入的值
        String email = req.getParameter("email");
        String password = req.getParameter("password");
       
        //去数据库里找用户 用email查询
        User user = UserManager.usersTable.get(email);
        
        //判断是否登录成功了
        if (user != null && user.getPassword().equals(password)) {
            HttpSession session = req.getSession();//创建session存入信息
            session.setAttribute("login", user.getEmail());
            session.setAttribute("role", user.getRole());
            
            //成功显示成功信息
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Connexion réussie</h2>");
            resp.getWriter().println("<p>Bienvenue " + user.getFirstname() + "</p>");
            resp.getWriter().println("<p>Login : " + session.getAttribute("login") + "</p>");
            resp.getWriter().println("<p>Role : " + session.getAttribute("role") + "</p>");
            resp.getWriter().println("<a href='Deconnexion'>Se déconnecter</a>");
            resp.getWriter().println("</body></html>");
        } else {//失败显示失败信息
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Échec de connexion</h2>");
            resp.getWriter().println("<p>Email ou mot de passe incorrect.</p>");
            resp.getWriter().println("<a href='connexion.html'>Retour à la connexion</a>");
            resp.getWriter().println("</body></html>");
        }
    }
}