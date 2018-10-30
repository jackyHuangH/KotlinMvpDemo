package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/30/030
 * desc  ：巡检信息 数据实体
 * record：
 */
public class MsgInspectEntity extends MarkReadEntity {

    //建筑名称、
    private String buildingName;
    //最新巡检结果（巡检点名称+巡检点是否正常）
    private String inspectResult;
    //巡检完成时间
    private String finishDate;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getInspectResult() {
        return inspectResult;
    }

    public void setInspectResult(String inspectResult) {
        this.inspectResult = inspectResult;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    @Override
    public String toString() {
        return "MsgInspectEntity{" +
                "buildingName='" + buildingName + '\'' +
                ", inspectResult='" + inspectResult + '\'' +
                ", finishDate=" + finishDate +
                '}';
    }
}
