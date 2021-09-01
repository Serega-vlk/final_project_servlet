package com.dto;

public class LoginErrors {
    private boolean login;
    private boolean password;

    public static LoginErrors builder(){
        return new LoginErrors();
    }

    public LoginErrors setLogin(boolean login) {
        this.login = login;
        return this;
    }

    public LoginErrors setPassword(boolean password) {
        this.password = password;
        return this;
    }

    public boolean isLogin() {
        return login;
    }

    public boolean isPassword() {
        return password;
    }

    public boolean hasErrors(){
        return login || password;
    }
}
