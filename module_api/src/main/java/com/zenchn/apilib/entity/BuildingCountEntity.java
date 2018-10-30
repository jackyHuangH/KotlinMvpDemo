package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/28/028
 * desc  ：建筑 各等级 统计数据
 * record：
 */
public class BuildingCountEntity {
    private Integer total;
    private Integer abNum;
    private Integer cNum;
    private Integer dNum;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAbNum() {
        return abNum;
    }

    public void setAbNum(Integer abNum) {
        this.abNum = abNum;
    }

    public Integer getcNum() {
        return cNum;
    }

    public void setcNum(Integer cNum) {
        this.cNum = cNum;
    }

    public Integer getdNum() {
        return dNum;
    }

    public void setdNum(Integer dNum) {
        this.dNum = dNum;
    }

    @Override
    public String toString() {
        return "BuildingCountEntity{" +
                "total=" + total +
                ", abNum=" + abNum +
                ", cNum=" + cNum +
                ", dNum=" + dNum +
                '}';
    }
}
