package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Table(name = BateeqShopContract.ProductCategoryDataEntry.TABLE_NAME)
public class ProductCategory extends Model implements Serializable
{
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_NAME)
    private String name;
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_PARENT_CODE)
    private String parentCode;
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_PATH)
    private String path;
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_LEVEL)
    private int level;
    @Column(name = BateeqShopContract.ProductCategoryDataEntry.COLUMN_ORDER)
    private int order;

    @SerializedName("subCategory")
    private List<ProductCategory> subCategories = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<ProductCategory> getSubCategories() {
        return subCategories;
    }

    public void setSubCategories(List<ProductCategory> subCategories) {
        this.subCategories = subCategories;
    }

    public static List<ProductCategory> getProductCategoriesByLevel(int level)
    {
        return new Select()
                .from(ProductCategory.class)
                .where(BateeqShopContract.ProductCategoryDataEntry.COLUMN_LEVEL + " = ?", level)
                .orderBy(BateeqShopContract.ProductCategoryDataEntry.COLUMN_ORDER + " ASC")
                .execute();
    }

    public static List<ProductCategory> getProductCategoriesByParentCode(String parentCode)
    {
        return new Select()
                .from(ProductCategory.class)
                .where(BateeqShopContract.ProductCategoryDataEntry.COLUMN_PARENT_CODE + " = ?", parentCode)
                .orderBy(BateeqShopContract.ProductCategoryDataEntry.COLUMN_ORDER + " ASC")
                .execute();
    }

    public static String getProducCategoryInfo(int level)
    {
        List<ProductCategory> listProductCategory = getProductCategoriesByLevel(level);
        String info = "";
        for (ProductCategory item : listProductCategory)
        {
            info += "code : " + item.code +
                    ", level : " + item.level +
                    ", name : " + item.name +
                    ", order : " + item.order +
                    ", parentCode : " + item.parentCode +
                    ", path : " + item.path +
                    "\n";
        }
        return info;
    }
}
