package com.example.mycity2.bean;

public class CodeMsgBean {

    /**
     * msg : Required request body is missing: public com.ljxl.common.core.domain.AjaxResult com.ljxl.sc.common.api.LoginRegisterApi.add(com.ljxl.common.core.domain.entity.SysUser)
     * code : 500
     */

    private String msg;
    private int code;

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
