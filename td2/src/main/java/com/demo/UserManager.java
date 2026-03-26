package com.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Hashtable;

@WebServlet("/UserManager")//url映射
public class UserManager extends HttpServlet {
    //这是一个内存数据库
    public static Hashtable<String, User> usersTable = new Hashtable<String, User>(); 
    
    //初始化一个role为admin的用户用于测试
    static {
    usersTable.put("admin@test.com",
        new User("Admin", "Root", "admin@test.com", "1234", "male", "admin"));
    }
    
    //当method=“post”的时候就会进入这里
    //post会携带数据（用户在表单中输入的） 去req请求里找到username的值
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws IOException, ServletException{

        // 先检查 session 和 role
        HttpSession session = req.getSession(false);
        //如果没有登录直接送回登录界面
        if (session == null || session.getAttribute("login") == null) {
            resp.sendRedirect("connexion.html");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            resp.getWriter().println("Accès refusé : admin seulement.");
            return;
        }
         
        //防止乱码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        
        //从表单获取数据
        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        //1.空表检查
        if (firstname == null || firstname.trim().isEmpty() ||
            lastname == null || lastname.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            gender == null) {

            resp.getWriter().println("Erreur : champ vide");
            return;
        }


        // 重复用户检查 如果用户已经存在->forward到确认页面
        //把数据临时存放到request页面准备传给confirm页面
        if (usersTable.containsKey(email)) {
            req.setAttribute("firstname", firstname);
            req.setAttribute("lastname", lastname);
            req.setAttribute("email", email);
            req.setAttribute("password", password);
            req.setAttribute("gender", gender);

            RequestDispatcher rd = req.getRequestDispatcher("/confirm.jsp");
            rd.forward(req, resp);
            return;
        }
         
        //不存在 → 直接添加
        //创建用户对象（面向对象）把数据封装成对象
        User user = new User(firstname, lastname, email, password, gender, "user");
        //存入数据库
        usersTable.put(email, user);
        //返回结果
        resp.getWriter().println("Utilisateur ajouté: " + firstname + " " + lastname);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        // 先检查 session 和 role
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("login") == null) {
            resp.sendRedirect("connexion.html");
            return;
        }

        String role = (String) session.getAttribute("role");
        if (!"admin".equals(role)) {
            resp.getWriter().println("Accès refusé : admin seulement.");
            return;
        }
        

        //告诉浏览器我要返回的是html页面
        resp.setContentType("text/html;charset=UTF-8");
        
        //写网页标题
        resp.getWriter().println("<h2>Liste des utilisateurs :</h2>");
        //遍历所有用户展示信息
        for (User u : usersTable.values()) {
            resp.getWriter().println(
                "<p>" +
                u.getFirstname() + " " +
                u.getLastname() +
                " - " + u.getEmail() +
                "</p>"
            );
        }

        resp.getWriter().println("</body></html>");
    }

}