package com.demo;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.IOException;
import java.util.Hashtable;

@WebServlet("/UserManager")//url映射
public class UserManager extends HttpServlet {
    //这是一个内存数据库
    private static Hashtable<Integer, User> usersTable = new Hashtable<>();
    
    //当method=“post”的时候就会进入这里
    //post会携带数据（用户在表单中输入的） 去req请求里找到username的值
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        //从表单获取数据
        String username = req.getParameter("username");
        String ageStr = req.getParameter("age");
        //因为表单数据全是字符串 所以需要转换数据
        int age = Integer.parseInt(ageStr);
        //创建用户对象（面向对象）把数据封装成对象
        User user = new User(username, age);
        //存入数据库
        usersTable.put(usersTable.size(), user);

        resp.getWriter().println("Add successflly:" + username);
    }
}