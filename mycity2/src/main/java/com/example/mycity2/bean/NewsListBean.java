package com.example.mycity2.bean;

import java.util.List;

public class NewsListBean {

    /**
     * total : 10
     * rows :
     * code : 200
     * msg : 查询成功
     */

    private int total;
    private int code;
    private String msg;
    /**
     * searchValue : null
     * createBy : 1
     * createTime : 2021-05-08 10:05:09
     * updateBy : 1
     * updateTime : 2022-03-02 20:15:55
     * remark : null
     * params : {}
     * id : 29
     * appType : smart_city
     * cover : /prod-api/profile/upload/image/2021/05/08/a8ece791-1fe8-4e09-8e3d-c42bc2ab481e.jpg
     * title : 长视频斗不过短视频的底层逻辑
     * subTitle : null
     * content :
     * status : Y
     * publishDate : 2021-05-08
     * tags : null
     * commentNum : 1340
     * likeNum : 2741
     * readNum : 3321
     * type : 9
     * top : N
     * hot : N
     */

    private List<RowsBean> rows;

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

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }
}
