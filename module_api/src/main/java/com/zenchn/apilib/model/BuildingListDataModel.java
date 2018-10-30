package com.zenchn.apilib.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 作    者：wangr on 2017/6/21 19:43
 * 描    述：
 * 修订记录：
 */

public class BuildingListDataModel<T> {

    @JSONField(name = "total")
    public int totalCount;
    @JSONField(name = "pageNor")
    public int pageNumber;
    public int totalPages;
    public int pageSize;

    @JSONField(name = "housesList")
    public List<T> list;


    @Override
    public String toString() {
        return "ListDataModel{" +
                "list=" + (list != null ? list.toString() : "null") +
                '}';
    }
}
