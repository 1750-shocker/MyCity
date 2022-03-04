package com.example.mycity2.bean;

public class UserInfoBean {

    /**
     * msg : 操作成功
     * code : 200
     * user : {"userId":1112798,"userName":"FreddyWang","nickName":"蘑菇菇","email":"2215991508@qq.com","phonenumber":"13866774640","sex":"1","avatar":"kk","idCard":"34*********2514","balance":12023,"score":1000}
     */

    private String msg;
    private int code;
    /**
     * userId : 1112798
     * userName : FreddyWang
     * nickName : 蘑菇菇
     * email : 2215991508@qq.com
     * phonenumber : 13866774640
     * sex : 1
     * avatar : kk
     * idCard : 34*********2514
     * balance : 12023.0
     * score : 1000
     */

    private UserBean user;

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

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }


}
