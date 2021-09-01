package com.dto;

public class User {
    public User(){
    }
    private int id;
    private String email;
    private String login;
    private String name;
    private String password;
    private Role role;
    private int money;

    public static User builder(){
        return new User();
    }

    public User setId(int id) {
        this.id = id;
        return this;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public User setLogin(String login) {
        this.login = login;
        return this;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public User setRole(Role role) {
        this.role = role;
        return this;
    }

    public User setMoney(int money) {
        this.money = money;
        return this;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", login='" + login + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", money=" + money +
                '}';
    }
}
