package com.bateeqshop.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.io.Serializable;

/**
 * Created by Daniel.Nababan on 5/25/2016.
 */
@Table(name = BateeqShopContract.ProductSizeDataEntry.TABLE_NAME)
public class ProductSize extends Model  implements Serializable {
    @Column(name = BateeqShopContract.ProductSizeDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.ProductSizeDataEntry.COLUMN_PRODUCT_CODE)
    private String productCode;
    @Column(name = BateeqShopContract.ProductSizeDataEntry.COLUMN_SIZE)
    private String size;
    @Column(name = BateeqShopContract.ProductSizeDataEntry.COLUMN_SEQUENCE)
    private int sequence;
//    @Column(name = BateeqShopContract.ProductSizeDataEntry.PARENT_SHOPPING_CART_ORDER_DETAIL)
    private ShoppingCartOrderDetail shoppingCartOrderDetail;

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public ShoppingCartOrderDetail getShoppingCartOrderDetail() {
        return shoppingCartOrderDetail;
    }

    public void setShoppingCartOrderDetail(ShoppingCartOrderDetail shoppingCartOrderDetail) {
        this.shoppingCartOrderDetail = shoppingCartOrderDetail;
    }
}
