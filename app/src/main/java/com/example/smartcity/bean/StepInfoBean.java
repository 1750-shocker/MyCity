package com.example.smartcity.bean;

import java.util.List;

public class StepInfoBean {

    public String msg;
    public int code;
    public List<DataDTO> data;

    public static class DataDTO {
        public String name;
        public List<LinesDTO> lines;

        public static class LinesDTO {
            public int lineId;
            public String lineName;
        }
    }
}
