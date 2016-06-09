package com.bateeqshop.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Daniel.Nababan on 6/1/2016.
 */
public class CatalogueFilterQuery {
    private long minPrice;
    private long maxPrice;
    private List<String> categories;
    private List<String> colors;
    private List<String> sizes;

    public CatalogueFilterQuery(){
        minPrice = 0;
        maxPrice = 0;
        categories = new ArrayList<String>();
        colors = new ArrayList<String>();
        sizes = new ArrayList<String>();
    }

    public double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(long maxPrice) {
        this.maxPrice = maxPrice;
    }

    public double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(long minPrice) {
        this.minPrice = minPrice;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public void setSizes(List<String> sizes) {
        this.sizes = sizes;
    }

    public Map<String,String> getQuery(){
        Map<String,String> query = new HashMap<String, String>() ;
        if(maxPrice >= 0){
            query.put("maxPrice",String.valueOf(maxPrice));
            query.put("minPrice",String.valueOf(minPrice));
        }

        if(categories.size() > 0){
            for (String item :categories
                 ) {
                query.put("categories",item);
            }
        }

        if(colors.size() > 0){
            for (String item :colors
                    ) {
                query.put("colors",item);
            }
        }

        if(sizes.size() > 0){

            for (String item :sizes
                    ) {
                query.put("sizes",item);
            }
        }

        return query;
    }
}
