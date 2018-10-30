package com.zenchn.apilib.entity;

import java.util.Date;

/**
 * @author:Hzj
 * @date :2018/9/27/027
 * desc  ：巡检记录
 * record：
 */
public class InspectRecordEntity {

    private String buildingId;

    //建筑名称、巡检结论（异常-红色字体；正常-蓝色字体）、建筑地址、巡检完成时间；
    private String buildingName;

    private Integer status;

    private String buildingAddress;

    private Date finishDate;

    private String finishDateString;

    public String getFinishDateString() {
        return finishDateString;
    }

    public void setFinishDateString(String finishDateString) {
        this.finishDateString = finishDateString;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBuildingAddress() {
        return buildingAddress;
    }

    public void setBuildingAddress(String buildingAddress) {
        this.buildingAddress = buildingAddress;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "InspectRecordEntity{" +
                "buildingId='" + buildingId + '\'' +
                ", buildingName='" + buildingName + '\'' +
                ", status=" + status +
                ", buildingAddress='" + buildingAddress + '\'' +
                ", finishDate=" + finishDate +
                ", finishDateString='" + finishDateString + '\'' +
                '}';
    }
}
