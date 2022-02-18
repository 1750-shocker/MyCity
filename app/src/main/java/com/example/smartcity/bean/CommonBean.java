package com.example.smartcity.bean;

public class CommonBean {

    public String msg;
    public int code;

    @Override
    public String toString() {
        return "CommonBean{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
