package com.zenchn.apilib.entity;

import java.util.List;

/**
 * @author:Hzj
 * @date :2018/9/28/028
 * desc  ：巡检详情 数据
 * record：
 */
public class InspectDetailEntity {

    private String buildingId;

    //结论
    private String conclusion;

    //巡检评价
    private String appraisal;

    //整体照片
    private List<String> overallPhotos;

    /**
     * 检测点集合
     */
    private List<InspectPointEntity> pointList;

    public String getConclusion() {
        return conclusion;
    }

    public void setConclusion(String conclusion) {
        this.conclusion = conclusion;
    }

    public String getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal;
    }

    public List<String> getOverallPhotos() {
        return overallPhotos;
    }

    public void setOverallPhotos(List<String> overallPhotos) {
        this.overallPhotos = overallPhotos;
    }

    public List<InspectPointEntity> getPointList() {
        return pointList;
    }

    public void setPointList(List<InspectPointEntity> pointList) {
        this.pointList = pointList;
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    @Override
    public String toString() {
        return "InspectDetailEntity{" +
                "buildingId='" + buildingId + '\'' +
                ", conclusion='" + conclusion + '\'' +
                ", appraisal='" + appraisal + '\'' +
                ", overallPhotos=" + overallPhotos +
                ", pointList=" + pointList +
                '}';
    }
}
