package com.demo;

import java.io.IOException;
import java.io.PrintWriter;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// 你的 HelloServlet 继承httpservlet之后，就具备了处理浏览器请求的能力
//当用户访问：  http://localhost:8080/servlet-demo/hello
//服务器就会把这个请求交给 HelloServlet 来处理。
//你可以把它理解成“网址绑定”。

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {//这个类不是普通 Java 类，而是一个能处理网页请求的 Servlet 类

    @Override
    //当服务器发来请求的时候就使用这个方法
    /* HttpServletRequest req:请求对象，里面装的是浏览器发来的信息
       HttpServletResponse resp:响应对象，里面是你准备回给浏览器的内容
       throws IOException:表示这个方法可能会抛出输入输出异常
    */
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        
        //用于设置返回内容的类型 是html 用utf-8编码
        resp.setContentType("text/html;charset=UTF-8");
        
        //从响应对象那里拿一个输出通道 往网页里写东西
        PrintWriter out = resp.getWriter();
        out.println("<html><body>");
        out.println("<h1>Hello Servlet!</h1>");
        out.println("</body></html>");
    }
}