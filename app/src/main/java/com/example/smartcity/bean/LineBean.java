package com.example.smartcity.bean;

import java.util.List;

public class LineBean {

    public String msg;
    public int code;
    public List<DataDTO> data;

    public static class DataDTO {
        public int lineId;
        public String lineName;
    }
}
