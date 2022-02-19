package com.example.smartcity.bean;

public class MessageEvent {
    private int fragmentid;

    public int getFragmentid() {
        return fragmentid;
    }

    public void setFragmentid(int fragmentid) {
        this.fragmentid = fragmentid;
    }

    public MessageEvent(int fragmentid) {
        this.fragmentid = fragmentid;
    }
}
