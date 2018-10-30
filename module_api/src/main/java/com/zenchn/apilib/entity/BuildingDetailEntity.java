package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/30/030
 * desc  ：建筑详情 数据实体
 * record：
 */
public class BuildingDetailEntity extends BuildingEntity {
    private static final long serialVersionUID = 1938382679082220425L;

    //所属机构
    private String affiliation;
    //建设年份
    private String buildYear;
    //建筑面积
    private Double buildingAcreage;
    //建筑高度
    private Double buildingHeight;
    //结构类型(接口返回itemValue)
    private Integer structureType;
    private String structureTypeName;
    //所属区域
    private String region;
    //建筑层数（接口返回itemValue）
    private Integer storeys;
    //建筑状态
    private Integer status;
    private String statusName;
    //建筑类型
    private String buidlingType;

    public String getAffiliation() {
        return affiliation;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }

    public String getBuildYear() {
        return buildYear;
    }

    public void setBuildYear(String buildYear) {
        this.buildYear = buildYear;
    }

    public Double getBuildingAcreage() {
        return buildingAcreage;
    }

    public void setBuildingAcreage(Double buildingAcreage) {
        this.buildingAcreage = buildingAcreage;
    }

    public Double getBuildingHeight() {
        return buildingHeight;
    }

    public void setBuildingHeight(Double buildingHeight) {
        this.buildingHeight = buildingHeight;
    }

    public Integer getStructureType() {
        return structureType;
    }

    public void setStructureType(Integer structureType) {
        this.structureType = structureType;
    }

    public String getStructureTypeName() {
        return structureTypeName;
    }

    public void setStructureTypeName(String structureTypeName) {
        this.structureTypeName = structureTypeName;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Integer getStoreys() {
        return storeys;
    }

    public void setStoreys(Integer storeys) {
        this.storeys = storeys;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getBuidlingType() {
        return buidlingType;
    }

    public void setBuidlingType(String buidlingType) {
        this.buidlingType = buidlingType;
    }

    @Override
    public String toString() {
        return "BuildingDetailEntity{" +
                "affiliation='" + affiliation + '\'' +
                ", buildYear='" + buildYear + '\'' +
                ", buildingAcreage=" + buildingAcreage +
                ", buildingHeight=" + buildingHeight +
                ", structureType=" + structureType +
                ", structureTypeName='" + structureTypeName + '\'' +
                ", region='" + region + '\'' +
                ", storeys=" + storeys +
                ", status=" + status +
                ", statusName='" + statusName + '\'' +
                ", buidlingType='" + buidlingType + '\'' +
                '}';
    }
}
