package com.bateeqshop.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel.Nababan on 5/31/2016.
 */
public class FilterModel {
    private String name;
    private String value;

    public FilterModel(String name,String value){
        this.name = name;
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static List<FilterModel> parse(String json) throws JSONException {
        List<FilterModel> filterModelList = new ArrayList<>();

        JSONObject object = new JSONObject(json);
        JSONArray array = object.getJSONArray("data");
        for (int i = 0 ;i<array.length();i++) {
            filterModelList.add(new FilterModel(array.getJSONObject(0).toString(),array.getJSONObject(1).toString()));
        }

        return filterModelList;
    }
}
