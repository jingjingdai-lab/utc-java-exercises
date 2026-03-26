package com.demo;

public class User {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String gender;
    private String role;

    // 构造函数
    public User(String firstname, String lastname, String email, String password, String gender, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.gender = gender;
        this.role = role;
    }


    // getters
    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getGender() {
        return gender;
    }

    public String getRole() {
        return role;
    }
}