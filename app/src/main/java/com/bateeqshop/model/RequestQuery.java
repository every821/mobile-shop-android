package com.bateeqshop.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Daniel.Nababan on 5/26/2016.
 */
public class RequestQuery {
    private int page;
    private int size;
    private int count;
    private String keyword;
    private String orderBy;
    private String sort;
    private boolean asc;
    private int totalPage;
    private Date date;


    public RequestQuery(){
        page = 1;
        size = 15;
        keyword = "";
        orderBy = "";
        asc = false;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<String,String> getQuery(){
        Map<String, String> params = new HashMap<String, String>() ;
        params.put("size",String.valueOf(size));
        params.put("page",String.valueOf(page));
        params.put("sort",String.valueOf(sort));
        params.put("orderBy",String.valueOf(orderBy));

        return params;
    }

    public void resetPage() {
        this.page = 0;
    }
}
