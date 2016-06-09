package com.bateeqshop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.Query;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.ShoppingCartResponse;
import com.bateeqshop.rest.RestProvider;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class DevFragment extends BaseFragment {

    private Context mContext;
    private View mView;
    private MenuItem m_menu_item_shopping_bag;
    private MenuItem m_menu_item_wish_list;


    public DevFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_dev_web_view, container, false);
        return inflater.inflate(R.layout.fragment_dev_shopping_cart, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initShoppingCart();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        m_menu_item_shopping_bag = menu.findItem(R.id.menu_item_shopping_bag);
        m_menu_item_wish_list = menu.findItem(R.id.menu_item_wish_list);
    }

    @Override
    public void syncNavigationViewSelection() {

    }

    @Override
    public ViewGroup getContentLayout() {
        return null;
    }

    @Override
    public void loadContent() {

    }

    private void initWebView()
    {
        WebView devWebView = (WebView) mView.findViewById(R.id.forgot_password_web_view);
        WebSettings webSettings = devWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        devWebView.loadUrl("http://11.11.1.42:36/");
    }

    private void initShoppingCart()
    {
        Button btn_plus_shopping = (Button) mView.findViewById(R.id.btn_plus_shopping);
        Button btn_minus_shopping = (Button) mView.findViewById(R.id.btn_minus_shopping);
        Button btn_plus_wish = (Button) mView.findViewById(R.id.btn_plus_wish);
        Button btn_minus_wish = (Button) mView.findViewById(R.id.btn_minus_wish);
        Button btn_user_info = (Button) mView.findViewById(R.id.btn_user_info);
        Button btn_merge_cart = (Button) mView.findViewById(R.id.btn_merge_cart);

        btn_plus_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });

        btn_minus_shopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCart();
            }
        });

        btn_plus_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToWishList();
            }
        });

        btn_minus_wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteWishList();
            }
        });

        btn_user_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showToast(Session.userInfo());
            }
        });

        btn_merge_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mergeCart();
            }
        });

//        mShoppingBagBadgeCount = ((MainActivity)getActivity()).getShoppingBagBadgeCount();
//        mWishListBadgeCount = ((MainActivity)getActivity()).getWishListBadgeCount();
//        Button btn_min_shopping = (Button) view.findViewById(R.id.btn_minus_shopping);
//        btn_min_shopping.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mShoppingBagBadgeCount > 0) {
//                    mShoppingBagBadgeCount--;
//                    if (mShoppingBagBadgeCount <= 0) mShoppingBagBadgeCount = Integer.MIN_VALUE;
//                    ActionItemBadge.update(m_menu_item_shopping_bag, mShoppingBagBadgeCount);
//                }
//            }
//        });
//
//        Button btn_min_wish_list = (Button) view.findViewById(R.id.btn_minus_wish);
//        btn_min_wish_list.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mWishListBadgeCount > 0) {
//                    mWishListBadgeCount--;
//                    if (mWishListBadgeCount <= 0) mWishListBadgeCount = Integer.MIN_VALUE;
//                    ActionItemBadge.update(m_menu_item_wish_list, mWishListBadgeCount);
//                }
//            }
//        });
    }

    private void addToCart()
    {
        if (Session.isValid()) {
            Call<ShoppingCartResponse> call = RestProvider.getResourceRest().addShoppingCart(Session.getHeaderAuthorization(),
                    ApiConfig.DEV_PRODUCT_CODE_1,
                    ApiConfig.DEV_PRODUCT_COLOR_CODE_1,
                    ApiConfig.DEV_PRODUCT_SIZE_CODE_1);
            call.enqueue(new Callback<ShoppingCartResponse>() {
                @Override
                public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        ShoppingCart shoppingCart = response.body().getData();
                        Session.updateShoppingCarts(shoppingCart);
                    } else {
                        Log.e("ADD TO CART", "FAILED");
                    }
                }

                @Override
                public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                    Log.e("ADD TO CART", "FAILED");
                }
            });
        }
        else
        {
            ShoppingCartOrderDetail orderDetail = new ShoppingCartOrderDetail();
            orderDetail.setProductCode(ApiConfig.DEV_PRODUCT_CODE_1);
            orderDetail.setProductColorCode(ApiConfig.DEV_PRODUCT_COLOR_CODE_1);
            orderDetail.setProductSizeCode(ApiConfig.DEV_PRODUCT_SIZE_CODE_1);
            Session.addOrderDetailLocal(orderDetail);
//            if (Session.shoppingCart == null) Session.shoppingCart = new ShoppingCart();
//            if (Session.shoppingCart.getShoppingCartOrderDetails() == null) Session.shoppingCart.setShoppingCartOrderDetails(new ArrayList<ShoppingCartOrderDetail>());
//            Session.shoppingCart.getShoppingCartOrderDetails().add(orderDetail);
//            Session.updateShoppingCarts(Session.shoppingCart);
//            shoppingCart.setShoppingCartOrderDetails(new ArrayList<ShoppingCartOrderDetail>());
//            shoppingCart.getShoppingCartOrderDetails().add(orderDetail);
//            Session.setShoppingCart(shoppingCart);
        }
    }

    private void deleteCart()
    {
        final ShoppingCartOrderDetail orderDetail = (ShoppingCartOrderDetail) Query.getSingle(ShoppingCartOrderDetail.class);
        if (Session.isValid()) {
            if (orderDetail != null) {
                Call<ShoppingCartResponse> call = RestProvider.getResourceRest().deleteShoppingCart(Session.getHeaderAuthorization(), orderDetail.getCode());
                call.enqueue(new Callback<ShoppingCartResponse>() {
                    @Override
                    public void onResponse(Call<ShoppingCartResponse> call, Response<ShoppingCartResponse> response) {
                        ShoppingCartOrderDetail.deleteItem(orderDetail.getCode());
                        ShoppingCart shoppingCart = response.body().getData();
                        Session.updateShoppingCarts(shoppingCart);
                    }

                    @Override
                    public void onFailure(Call<ShoppingCartResponse> call, Throwable t) {
                        Log.e("DELETE CART", "FAILED");
                    }
                });
            } else {
                Log.e("DELETE CART", "EMPTY");
            }
        }
        else
        {
            if (orderDetail != null)
            {
                Session.deleteOrderDetailLocal(orderDetail.getId());
            }
            else
            {
                Log.e("DELETE CART", "EMPTY");
            }

        }
    }

    private void mergeCart()
    {

    }

    private void addToWishList()
    {

    }

    private void deleteWishList()
    {

    }
}
