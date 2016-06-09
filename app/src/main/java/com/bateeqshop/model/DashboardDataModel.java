package com.bateeqshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DashboardDataModel
{
    @SerializedName("productCategories")
    private List<ProductCategory> productCategories = new ArrayList<>();
    @SerializedName("slideshows")
    private List<SlideShow> slideShows = new ArrayList<>();

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }

    public void setProductCategories(List<ProductCategory> productCategories) {
        this.productCategories = productCategories;
    }

    public List<SlideShow> getSlideShows() {
        return slideShows;
    }

    public void setSlideShows(List<SlideShow> slideShows) {
        this.slideShows = slideShows;
    }
}
