package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/30/030
 * desc  ：预警信息 数据实体
 * record：
 */
public class MsgWarningEntity extends MarkReadEntity{

    //建筑名称、
    private String buildingName;
    //预警详情
    private String warningDesc;
    //预警时间
    private String warnDate;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getWarningDesc() {
        return warningDesc;
    }

    public void setWarningDesc(String warningDesc) {
        this.warningDesc = warningDesc;
    }

    public String getWarnDate() {
        return warnDate;
    }

    public void setWarnDate(String warnDate) {
        this.warnDate = warnDate;
    }

    @Override
    public String toString() {
        return "MsgWarningEntity{" +
                "buildingName='" + buildingName + '\'' +
                ", warningDesc='" + warningDesc + '\'' +
                ", warnDate=" + warnDate +
                '}';
    }
}
