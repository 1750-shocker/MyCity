package com.example.smartcity.bean;

import java.util.List;

public class Stepbean {

    public String msg;
    public int code;
    public DataDTO data;

    public static class DataDTO {
        public StepInfoDTO stepInfo;
        public List<LineListDTO> lineList;

        public static class StepInfoDTO {
            public String name;
            public int crowd;
        }

        public static class LineListDTO {
            public Object searchValue;
            public Object createBy;
            public String createTime;
            public Object updateBy;
            public String updateTime;
            public Object remark;
            public ParamsDTO params;
            public int id;
            public String name;
            public String first;
            public String end;
            public String startTime;
            public String endTime;
            public int cityId;
            public String cityName;

            public static class ParamsDTO {
            }
        }
    }
}
