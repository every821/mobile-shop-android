package com.bateeqshop.model;

import android.provider.BaseColumns;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.List;

@Table(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.TABLE_NAME, id = BaseColumns._ID)
public class ShoppingCartOrderDetail extends Model
{
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_CODE)
    private String code;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_ORDER_CODE)
    private String orderCode;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_PRODUCT_PRICE)
    private String productPrice;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_PRODUCT_CODE)
    private String productCode;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_PRODUCT_COLOR_CODE)
    private String productColorCode;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_PRODUCT_SIZE_CODE)

    private String productSizeCode;
    private Product product;
    private ProductColor productColor;
    private ProductSize productSize;

    /**SHOPPING CART PARENT*/
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_CUSTOMER_EMAIL)
    private String customerEmail;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_ORDER_DATE)
    private String orderDate;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_SUBTOTAL)
    private String subTotal;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_SHIPPING_COST)
    private String shippingCost;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_TAX)
    private String tax;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_ORDER_STATUS)
    private String orderStatus;
    @Column(name = BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_REMARKS)
    private String remarks;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductColorCode() {
        return productColorCode;
    }

    public void setProductColorCode(String productColorCode) {
        this.productColorCode = productColorCode;
    }

    public String getProductSizeCode() {
        return productSizeCode;
    }

    public void setProductSizeCode(String productSizeCode) {
        this.productSizeCode = productSizeCode;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public ProductColor getProductColor() {
        return productColor;
    }

    public void setProductColor(ProductColor productColor) {
        this.productColor = productColor;
    }

    public ProductSize getProductSize() {
        return productSize;
    }

    public void setProductSize(ProductSize productSize) {
        this.productSize = productSize;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public String getShippingCost() {
        return shippingCost;
    }

    public void setShippingCost(String shippingCost) {
        this.shippingCost = shippingCost;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public static List<ShoppingCartOrderDetail> getAll()
    {
        return new Select().from(ShoppingCartOrderDetail.class).execute();
    }

    public static void deleteItem(String code)
    {
        new Delete().from(ShoppingCartOrderDetail.class).where(BateeqShopContract.ShoppingCartOrderDetailDataEntry.COLUMN_CODE + " = ?", code).execute();
    }


    public static void deleteItemById(long id)
    {
        new Delete().from(ShoppingCartOrderDetail.class).where(BaseColumns._ID + " = ?", id).execute();
    }

    public static class ShoppingCartOrderDetailSerializer implements JsonSerializer<ShoppingCartOrderDetail> {
        @Override
        public JsonElement serialize(ShoppingCartOrderDetail src, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject result = new JsonObject();
            result.add("code",new JsonPrimitive(src.getCode()));
            result.add("orderCode",new JsonPrimitive(src.getOrderCode()));
            result.add("productPrice",new JsonPrimitive(src.getProductPrice()));
            result.add("productCode",new JsonPrimitive(src.getProductCode()));
            result.add("productColorCode",new JsonPrimitive(src.getProductColorCode()));
            result.add("productSizeCode",new JsonPrimitive(src.getProductSizeCode()));
            return result;
        }
    }

}