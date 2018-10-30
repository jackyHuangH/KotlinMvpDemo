package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/27/027
 * desc  ：巡检首页总览信息：建筑总数，巡检完成率
 * record：
 */
public class InspectTotalEntity {

    private Integer totalHouseNum;

    private Double finishRate;

    public Integer getTotalHouseNum() {
        return totalHouseNum;
    }

    public void setTotalHouseNum(Integer totalHouseNum) {
        this.totalHouseNum = totalHouseNum;
    }

    public Double getFinishRate() {
        return finishRate;
    }

    public void setFinishRate(Double finishRate) {
        this.finishRate = finishRate;
    }

    @Override
    public String toString() {
        return "InspectTotalEntity{" +
                "totalHouseNum=" + totalHouseNum +
                ", finishRate=" + finishRate +
                '}';
    }
}
