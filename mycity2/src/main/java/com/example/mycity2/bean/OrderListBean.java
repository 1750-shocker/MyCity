package com.example.mycity2.bean;

import java.util.List;

public class OrderListBean {
    private int total;
    private int code;
    private String msg;
    /**
     * searchValue : null
     * createBy : null
     * createTime : null
     * updateBy : null
     * updateTime : null
     * remark : null
     * params : {}
     * id : 1914
     * orderNo : 2022022011245742570
     * amount : 64.0
     * orderStatus : 待支付
     * userId : 1112798
     * payTime : null
     * name : 尊宝比萨
     * orderType : takeout
     * orderTypeName : 外卖订餐
     */

    private List<OrderBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<OrderBean> getRows() {
        return rows;
    }

    public void setRows(List<OrderBean> rows) {
        this.rows = rows;
    }


}
