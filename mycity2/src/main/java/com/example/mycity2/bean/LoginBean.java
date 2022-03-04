package com.example.mycity2.bean;

public class LoginBean {

    /**
     * msg : 操作成功
     * code : 200
     * token : eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6Ijg4MjhlNWZkLWUwNWUtNDc0NS05ZWNmLTJlMjBmZmIyNzdkZCJ9.VRmHXnsUY0bE_X9TR4SNpxInKnoldondAuETwCLbaVe9iRXnbYm1ZfFg1CUkrby8cWK9yxl-WvfAZSaka6sKrA
     */

    private String msg;
    private int code;
    private String token;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
