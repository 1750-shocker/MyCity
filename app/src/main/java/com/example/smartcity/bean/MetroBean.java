package com.example.smartcity.bean;

import java.util.List;

public class MetroBean {

    public String msg;
    public int code;
    public DataDTO data;

    public static class DataDTO {
        public int id;
        public String name;
        public String first;
        public String end;
        public String startTime;
        public String endTime;
        public int cityId;
        public Object stationsNumber;
        public int km;
        public String runStationsName;
        public int remainingTime;
        public List<MetroStepListDTO> metroStepList;

        public static class MetroStepListDTO {
            public Object searchValue;
            public Object createBy;
            public String createTime;
            public Object updateBy;
            public String updateTime;
            public Object remark;
            public ParamsDTO params;
            public int id;
            public String name;
            public int seq;
            public int lineId;
            public String firstCh;

            public static class ParamsDTO {
            }
        }
    }
}
