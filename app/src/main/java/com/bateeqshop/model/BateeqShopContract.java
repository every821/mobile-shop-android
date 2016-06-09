package com.bateeqshop.model;

import android.net.Uri;

import com.activeandroid.content.ContentProvider;

public class BateeqShopContract
{
    public static final class SessionItemEntry
    {
        public static final String TABLE_NAME = "session";
        public static final String COLUMN_ACCESS_TOKEN = "accessToken";
        public static final String COLUMN_TOKEN_TYPE = "tokenType";
        public static final String COLUMN_EXPIRED_IN = "expiredIn";
        public static final String COLUMN_REFRESH_TOKEN = "refreshToken";
        public static final String COLUMN_ISSUED_DATE = "issuedDate";
        public static final String COLUMN_EXPIRED_DATE = "expiredDate";
        public static final String COLUMN_DEVICE_BASED_EXPIRED_DATE = "deviceBasedExpiredDate";
    }

    public static final class UserProfileDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(UserProfileData.class, null);
        public static final String TABLE_NAME = "userProfile";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_FIRST_NAME = "firstName";
        public static final String COLUMN_LAST_NAME = "lastName";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_COMPANY = "company";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_ZIP_CODE = "zipCode";
        public static final String COLUMN_COUNTRY_NAME = "countryName";
        public static final String COLUMN_STATE_NAME = "stateName";
        public static final String COLUMN_DISTRICT_NAME = "districtName";
        public static final String COLUMN_USER_ID = "userId";
    }

    public static final class ProductCategoryDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(ProductCategory.class, null);
        public static final String TABLE_NAME = "productCategory";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PARENT_CODE = "parentCode";
        public static final String COLUMN_PATH = "path";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_ORDER = "categoryViewOrder";
    }

    public static final class SlideShowDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(SlideShow.class, null);
        public static final String TABLE_NAME = "slideShow";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_IMAGE_URL = "imageUrl";
        public static final String COLUMN_THUMBNAIL_IMAGE_URL = "thumbImageUrl";
        public static final String COLUMN_TARGET_URL = "targetUrl";
    }

    public static final class ShoppingCartOrderDetailDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(ShoppingCartOrderDetail.class, null);
        public static final String TABLE_NAME = "shoppingCartOrderDetail";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_ORDER_CODE = "orderCode";
        public static final String COLUMN_PRODUCT_PRICE = "productPrice";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
        public static final String COLUMN_PRODUCT_COLOR_CODE = "productColorCode";
        public static final String COLUMN_PRODUCT_SIZE_CODE = "productSizeCode";

        public static final String COLUMN_CUSTOMER_EMAIL = "customerEmail";
        public static final String COLUMN_ORDER_DATE = "orderDate";
        public static final String COLUMN_SUBTOTAL = "subTotal";
        public static final String COLUMN_SHIPPING_COST = "shippingCost";
        public static final String COLUMN_TAX = "tax";
        public static final String COLUMN_ORDER_STATUS = "orderStatus";
        public static final String COLUMN_REMARKS = "remarks";
    }

    public static final class WishListDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(WishList.class, null);
        public static final String TABLE_NAME = "wishList";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
        public static final String COLUMN_PRODUCT_SIZE_CODE = "productSizeCode";
        public static final String COLUMN_PRODUCT_COLOR_CODE = "productColorCode";
        public static final String COLUMN_IS_ACTIVE = "isActive";
    }

    public static final class ProductColorDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(ProductColor.class, null);
        public static final String TABLE_NAME = "productColor";
        public static final String PARENT_SHOPPING_CART_ORDER_DETAIL = "shoppingCartOrderDetail_productColor";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
        public static final String COLUMN_COLOR = "color";
        public static final String COLUMN_SEQUENCE = "sequence";
    }

    public static final class ProductSizeDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(ProductSize.class, null);
        public static final String TABLE_NAME = "productSize";
        public static final String PARENT_SHOPPING_CART_ORDER_DETAIL = "shoppingCartOrderDetail_productSize";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
        public static final String COLUMN_SIZE = "size";
        public static final String COLUMN_SEQUENCE = "sequence";
    }

    public static final class ProductImageDataEntry
    {
        public static final Uri CONTENT_URI = ContentProvider.createUri(ProductImage.class, null);
        public static final String TABLE_NAME = "productImage";
        public static final String PARENT_SHOPPING_CART_ORDER_DETAIL = "shoppingCartOrderDetail_productImage";
        public static final String COLUMN_CODE = "code";
        public static final String COLUMN_PRODUCT_CODE = "productCode";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IS_PRIMARY= "isPrimary";
    }
}
