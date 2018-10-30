package com.zenchn.apilib.entity;

import java.io.Serializable;

/**
 * @author:Hzj
 * @date :2018/9/25/025
 * desc  ：
 * record：
 */
public class BuildingEntity implements Serializable {
    private static final long serialVersionUID = -396659869999256864L;

    private String buildingId;//建筑ID(新增则为null)

    private String buildingName;

    private String appraisalName;//鉴定等级名称
    private Integer appraisalGradeValue;//鉴定等级

    private String address;//地址
    private String portraitUrl;//展示图
    private String ownerName;//户主姓名
    private Double longitude;//经度
    private Double latitude;//纬度

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAppraisalName() {
        return appraisalName;
    }

    public void setAppraisalName(String appraisalName) {
        this.appraisalName = appraisalName;
    }

    public Integer getAppraisalGradeValue() {
        return appraisalGradeValue;
    }

    public void setAppraisalGradeValue(Integer appraisalGradeValue) {
        this.appraisalGradeValue = appraisalGradeValue;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPortraitUrl() {
        return portraitUrl;
    }

    public void setPortraitUrl(String portraitUrl) {
        this.portraitUrl = portraitUrl;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "BuildingEntity{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", appraisalName='" + appraisalName + '\'' +
                ", appraisalGradeValue=" + appraisalGradeValue +
                ", address='" + address + '\'' +
                ", portraitUrl='" + portraitUrl + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}

