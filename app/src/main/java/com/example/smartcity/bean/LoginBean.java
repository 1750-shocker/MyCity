package com.example.smartcity.bean;

public class LoginBean {
    public String msg;
    public int code;
    public String token;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", token='" + token + '\'' +
                '}';
    }
}
