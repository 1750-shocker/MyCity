package com.example.smartcity.bean;

import java.util.List;

public class CurrentStepBean {

    public String msg;
    public int code;
    public List<DataDTO> data;

    public static class DataDTO {
        public int lineId;
        public String lineName;
        public PreStepDTO preStep;
        public NextStepDTO nextStep;
        public String currentName;
        public int reachTime;

        public static class PreStepDTO {
            public String name;
            public List<LinesDTO> lines;

            public static class LinesDTO {
                public int lineId;
                public String lineName;
            }
        }

        public static class NextStepDTO {
            public String name;
            public List<LinesDTO> lines;

            public static class LinesDTO {
                public int lineId;
                public String lineName;
            }
        }
    }
}
