package com.example.mycity2.bean;

public class Chapter {
    private String name;
    private int i;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public Chapter(String name, int i) {
        this.name = name;
        this.i = i;
    }
}
