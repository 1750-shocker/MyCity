package com.example.mycity2.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class ServiceTable {
    @DatabaseField(generatedId = true)
    public int _id;
    @DatabaseField
    public String servName;
    @DatabaseField
    public int imgResource;

    @Override
    public String toString() {
        return "ServiceTable{" +
                "_id=" + _id +
                ", servName='" + servName + '\'' +
                ", imgResource=" + imgResource +
                '}';
    }

    public ServiceTable() {
    }

    public ServiceTable(int _id, String servName, int imgResource) {
        this._id = _id;
        this.servName = servName;
        this.imgResource = imgResource;
    }

    public ServiceTable(String servName, int imgResource) {
        this.servName = servName;
        this.imgResource = imgResource;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getServName() {
        return servName;
    }

    public void setServName(String servName) {
        this.servName = servName;
    }

    public int getImgResource() {
        return imgResource;
    }

    public void setImgResource(int imgResource) {
        this.imgResource = imgResource;
    }
}
