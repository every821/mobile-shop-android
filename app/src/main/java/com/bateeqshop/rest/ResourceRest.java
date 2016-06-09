package com.bateeqshop.rest;

import com.bateeqshop.model.CatalogueFilter;
import com.bateeqshop.model.DashboardModel;
import com.bateeqshop.model.FacebookLoginModel;
import com.bateeqshop.model.MergeModel;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.RequestObjectList;
import com.bateeqshop.model.ResponseData;
import com.bateeqshop.model.SessionModel;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.ShoppingCartResponse;
import com.bateeqshop.model.UserProfile;
import com.bateeqshop.model.UserProfileData;
import com.bateeqshop.model.WishList;
import com.google.gson.JsonElement;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ResourceRest
{
    @POST("account/FacebookLogin")
    Call<SessionModel> loginWithFacebook(@Body FacebookLoginModel facebookLoginModel);

    @FormUrlEncoded
    @POST("account/register")
    Call<ResponseBody> register(@Field("firstname") String firstname,
                                @Field("lastname") String lastName,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("phone") String phoneNumber);

    @GET("me/profile")
    Call<UserProfile> getUserProfile(@Header("Authorization") String authorization);

    @POST("me/cart/merge")
    Call<UserProfile> mergeShoppingCart(@Header("Authorization") String authorization, @Body List<MergeModel> orderDetails);

    @POST("me/cart/merge")
    Call<UserProfile> mergeShoppingCart(@Header("Authorization") String authorization, @Body String jsonArray);
    @GET("dashboard")
    Call<DashboardModel> getDashboardItems();
    @GET("product")
    Call<RequestObjectList<Product>> getProduct(@QueryMap Map<String,String> params);

    @GET("product/search/{keyword}")
    Call<RequestObjectList<Product>> getProduct(@Path("keyword") String keyword,@QueryMap Map<String,String> params, @QueryMap Map<String,String> filters);

    @GET("product/search/{keyword}/filter")
    Call<CatalogueFilter> getFilterQuery(@Path("keyword") String keyword);

    @GET("product/category")
    Call<RequestObjectList<Product>> getProductByCategory(@Query("pathCategory") String pathCategory,@QueryMap Map<String,String> params, @QueryMap Map<String,String> filters);

    @GET("product/category/filter")
    Call<CatalogueFilter> getFilterQueryByCategory(@Query("pathCategory") String pathCategory);
    @GET("cart")
    Call<RequestObjectList<ShoppingCart>> getShoppingCarts(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("cart")
    Call<ShoppingCartResponse> addShoppingCart(@Header("Authorization") String authorization,
                                               @Field("productCode") String productCode,
                                               @Field("productColorCode") String productColorCode,
                                               @Field("productSizeCode") String productSizeCode);

    @DELETE("cart/detail/{code}")
    Call<ShoppingCartResponse> deleteShoppingCart(@Header("Authorization") String authorization, @Path("code") String code);

    @FormUrlEncoded
    @POST("wishlist")
    Call<ResponseBody> addWishList(@Header("Authorization") String authorization,
                                       @Field("productCode") String productCode,
                                       @Field("productColorCode") String productColorCode,
                                       @Field("productSizeCode") String productSizeCode);

    @DELETE("wishlist/detail/{code}")
    Call<ResponseBody> deleteWishList(@Header("Authorization") String authorization, @Path("code") String code);


    @Headers( "Content-Type: application/json" )
    @PUT("cart/checkout")
    Call<ResponseData<String>> checkOut(@Header("Authorization") String authorization, @Body JsonElement order);

//    resource/v1/cart [GET, POST(AddToCart)]
//    resource/v1/cart/detail/{code} [DELETE]
//    resource/v1/cart/addproductdiscountcoupon/{code}
//    resource/v1/cart/removeproductdiscountcoupon/{code}
//    resource/v1/wishlist [POST(AddWishlist)]
//    resource/v1/wishlist/{code} [DELETE]
//    resource/v1/wishlist/movetocart [POST]
//    resource/v1/me/cart/merge [POST]
//    resource/v1/me/wishlist/merge [POST]

}
