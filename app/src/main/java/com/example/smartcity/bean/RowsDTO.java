package com.example.smartcity.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable
public class RowsDTO {
    @DatabaseField(generatedId = true)
    public int _id;
    @DatabaseField
    public String searchValue;
    @DatabaseField
    public String createBy;
    @DatabaseField
    public String createTime;
    @DatabaseField
    public String updateBy;
    @DatabaseField
    public String updateTime;
    @DatabaseField
    public String remark;
    @DatabaseField
    public int id;
    @DatabaseField
    public String appType;
    @DatabaseField
    public String cover;
    @DatabaseField
    public String title;
    @DatabaseField
    public String subTitle;
    @DatabaseField
    public String content;
    @DatabaseField
    public String status;
    @DatabaseField
    public String publishDate;
    @DatabaseField
    public String tags;
    @DatabaseField
    public int commentNum;
    @DatabaseField
    public int likeNum;
    @DatabaseField
    public int readNum;
    @DatabaseField
    public String type;
    @DatabaseField
    public String top;
    @DatabaseField
    public String hot;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSearchValue() {
        return searchValue;
    }

    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getCommentNum() {
        return commentNum;
    }

    public void setCommentNum(int commentNum) {
        this.commentNum = commentNum;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public int getReadNum() {
        return readNum;
    }

    public void setReadNum(int readNum) {
        this.readNum = readNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getHot() {
        return hot;
    }

    public void setHot(String hot) {
        this.hot = hot;
    }

    public RowsDTO(int _id, String searchValue, String createBy, String createTime, String updateBy, String updateTime, String remark, int id, String appType, String cover, String title, String subTitle, String content, String status, String publishDate, String tags, int commentNum, int likeNum, int readNum, String type, String top, String hot) {
        this._id = _id;
        this.searchValue = searchValue;
        this.createBy = createBy;
        this.createTime = createTime;
        this.updateBy = updateBy;
        this.updateTime = updateTime;
        this.remark = remark;
        this.id = id;
        this.appType = appType;
        this.cover = cover;
        this.title = title;
        this.subTitle = subTitle;
        this.content = content;
        this.status = status;
        this.publishDate = publishDate;
        this.tags = tags;
        this.commentNum = commentNum;
        this.likeNum = likeNum;
        this.readNum = readNum;
        this.type = type;
        this.top = top;
        this.hot = hot;
    }

    public RowsDTO() {
    }
}