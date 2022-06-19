package com.example.smarttourist;

public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String age;
    private String sex;
    private String nationality;
    private String role;
    private String healthCondition;

    public User(String username, String email, String password, String age, String sex, String nationality, String role, String healthCondition) {
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
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

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                ", nationality='" + nationality + '\'' +
                ", role='" + role + '\'' +
                ", healthCondition='" + healthCondition + '\'' +
                '}';
    }
}

