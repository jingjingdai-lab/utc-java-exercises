package com.demo.spring_demo;

/*
Servelt: 浏览器 → 你 → Tomcat → 返回
Spring Boot: 浏览器 → Spring → 调你的方法 → 返回  封装了Servlet
*/


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController//自动做对象管理 自动创建这个类自动管理生命周期 自动注入依赖
public class HelloController {

    @GetMapping("/hello")//等价于@WebServlet + doGet
    public String hello() {
        return "Hello Spring Boot!";//自动生成响应 Spring帮我设置contenttype 写入响应流 关闭流
    }
}
