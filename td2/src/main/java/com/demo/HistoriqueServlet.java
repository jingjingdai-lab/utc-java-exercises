package com.demo;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;

@WebServlet("/historique")
public class HistoriqueServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();//输出笔

        // 读取 cookie
        Cookie[] cookies = request.getCookies();//读取浏览器带来的cookie
        String lastVisit = null;

        if (cookies != null) {
            for (Cookie c : cookies) {//一个个  查看所有的cookie
                if (c.getName().equals("lastVisit")) {//找到lastvisit的值并取出
                    lastVisit = URLDecoder.decode(c.getValue(), "UTF-8");
                }
            }
        }

        // 如果找到上次的visittime就显示结果
        if (lastVisit != null) {
            out.println("<h3>Votre dernière visite était : " + lastVisit + "</h3>");
        } else {
            out.println("<h3>Bienvenue, c'est votre première visite !</h3>");//没找到就是第一次访问
        }

        // 生成当前时间
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'à' HH'h'mm");//创建时间格式化器
        String currentTime = sdf.format(new Date());
        String encodedTime = URLEncoder.encode(currentTime, "UTF-8");

        Cookie cookie = new Cookie("lastVisit", encodedTime);
        cookie.setMaxAge(60 * 60 * 24 * 30); // 30天 设置cookie保存时间 在浏览器里保存时间
        cookie.setPath("/"); //让整个项目路径下都能带上这个 Cookie
        response.addCookie(cookie);

        out.close();
    }
}