package com.eli.emailserver.entity;

public class RegisterInfo {
    public User user;
    public String code;

    public RegisterInfo(){

    }

    public RegisterInfo(User user, String code) {
        this.user = user;
        this.code = code;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
