package com.zenchn.apilib.entity;

import java.util.List;

/**
 * @author:Hzj
 * @date :2018/9/28/028
 * desc  ：检测点 数据
 * record：
 */
public class InspectPointEntity {
    private String pointName;

    private Integer status;

    private String desc;

    private List<String> photos;

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    @Override
    public String toString() {
        return "InspectPointEntity{" +
                "pointName='" + pointName + '\'' +
                ", status=" + status +
                ", desc='" + desc + '\'' +
                ", photos=" + photos +
                '}';
    }
}
