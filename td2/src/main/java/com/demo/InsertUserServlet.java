package com.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;

//一个新的Servlet类 用于提交 这是当用户在确认页点了oui后 再次接受的数据 真正把用户插入
@WebServlet("/InsertUser")
public class InsertUserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String firstname = req.getParameter("firstname");
        String lastname = req.getParameter("lastname");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String gender = req.getParameter("gender");

        User user = new User(firstname, lastname, email, password, gender, "user");
        //此处使用了copy就是如果只是加入的话就不是新增一个而是覆盖原来的
        //为了避免key重复 所以加入了
        UserManager.usersTable.put(email + "_copy_" + System.currentTimeMillis(), user);

        resp.getWriter().println("Utilisateur ajouté après confirmation : " + firstname);
    }
}