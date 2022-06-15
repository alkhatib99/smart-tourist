package com.example.smarttourist;

public class User {
    private String id;
    private String userName;
    private String email;
    private String password;
    private String age;
    private String sex;
    private String nationality;
    private String role;
    private String healthCondition;

    public User(String userName, String email, String password, String age, String sex, String nationality, String role, String healthCondition) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.nationality = nationality;
        this.role = role;
        this.healthCondition = healthCondition;
    }


    public User() {
    }




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHealthCondition() {
        return healthCondition;
    }

    public void setHealthCondition(String healthCondition) {
        this.healthCondition = healthCondition;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
