package com.zenchn.apilib.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * 作    者：wangr on 2017/6/21 19:43
 * 描    述：
 * 修订记录：
 */

public class ListDataModel<T> {

    public int total;
    @JSONField(name = "pageNor")
    public int pageNumber;
    public int totalPages;
    public int pageSize;


    public List<T> list;

    @Override
    public String toString() {
        return "ListDataModel{" +
                "list=" + (list != null ? list.toString() : "null") +
                '}';
    }
}
