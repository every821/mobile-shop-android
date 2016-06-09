package com.bateeqshop.model;


import com.activeandroid.Model;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Daniel.Nababan on 5/25/2016.
 */
public class Product extends Model implements Serializable {
    private String code;
    private String productBlueprintCode;
    private String name;
    private String description;
    private double price;
    private boolean isDiscount;
    private double discountPrice;
    private List<ProductColor> productColors;
    private List<ProductSize> productSizes;
    private List<ProductImage> productImages;

    public String getProductBlueprintCode() {
        return productBlueprintCode;
    }

    public void setProductBlueprintCode(String productBlueprintCode) {
        this.productBlueprintCode = productBlueprintCode;
    }

    public String getCode(){
        return code;
    }

    public void setCode(String code){
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(boolean isDiscount) {
        this.isDiscount = isDiscount;
    }

    public double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<ProductColor> getProductColors() {
        return productColors;
    }

    public void setProductColors(List<ProductColor> productColors) {
        this.productColors = productColors;
    }

    public List<ProductSize> getProductSizes() {
        return productSizes;
    }

    public void setProductSizes(List<ProductSize> productSizes) {
        this.productSizes = productSizes;
    }

    public List<ProductImage> getProductImages() {
        return productImages;
    }

    public void setProductImages(List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public String getPrimaryImage() {
        for (ProductImage image: this.productImages
                ) {
            if(image.isPrimary()){
                return image.getTitle();
            }
        }
        return "";
    }
}