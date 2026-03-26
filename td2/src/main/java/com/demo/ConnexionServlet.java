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
    HttpSession session = req.getSession();
    session.setAttribute("login", user.getEmail());
    session.setAttribute("role", user.getRole());

    if ("admin".equals(user.getRole())) {//role为admin的
                resp.getWriter().println("<!DOCTYPE html>");
                resp.getWriter().println("<html><head><meta charset='UTF-8'><title>Navigation</title></head>");
                resp.getWriter().println("<body>");
                resp.getWriter().println("<h1>Hello " + session.getAttribute("login") + "</h1>");
                resp.getWriter().println("<nav><ul>");
                resp.getWriter().println("<li>Connected</li>");
                resp.getWriter().println("<li><a href='form.html'>Créer un nouveau utilisateur</a></li>");
                resp.getWriter().println("<li><a href='UserManager'>Afficher la liste des utilisateurs</a></li>");
                resp.getWriter().println("<li><a href='Deconnexion'>Se déconnecter</a></li>");
                resp.getWriter().println("</ul></nav>");
                resp.getWriter().println("</body></html>");
            } else {//role为user的
                resp.getWriter().println("<html><body>");
                resp.getWriter().println("<h2>Connexion réussie</h2>");
                resp.getWriter().println("<p>Bienvenue " + user.getFirstname() + "</p>");
                resp.getWriter().println("<p>Login : " + session.getAttribute("login") + "</p>");
                resp.getWriter().println("<p>Role : " + session.getAttribute("role") + "</p>");
                resp.getWriter().println("<a href='Deconnexion'>Se déconnecter</a>");
                resp.getWriter().println("</body></html>");
            }
        } else {//登录失败的
            resp.getWriter().println("<html><body>");
            resp.getWriter().println("<h2>Échec de connexion</h2>");
            resp.getWriter().println("<p>Email ou mot de passe incorrect.</p>");
            resp.getWriter().println("<a href='connexion.html'>Retour à la connexion</a>");
            resp.getWriter().println("</body></html>");
        }
    }
}