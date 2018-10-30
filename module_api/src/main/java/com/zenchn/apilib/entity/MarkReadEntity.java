package com.zenchn.apilib.entity;

/**
 * @author:Hzj
 * @date :2018/9/30/030
 * desc  ：是否已读 标记用
 * record：
 */
public class MarkReadEntity {

    private Boolean hasRead;

    public Boolean isHasRead() {
        return hasRead;
    }

    public void setHasRead(Boolean hasRead) {
        this.hasRead = hasRead;
    }

    @Override
    public String toString() {
        return "MarkReadEntity{" +
                "hasRead=" + hasRead +
                '}';
    }
}
