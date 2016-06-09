package com.bateeqshop.fragments;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.ProductColor;
import com.bateeqshop.model.ProductSize;
import com.bateeqshop.model.ShoppingCart;
import com.bateeqshop.model.ShoppingCartOrderDetail;
import com.bateeqshop.model.ShoppingCartResponse;
import com.bateeqshop.rest.RestProvider;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.HttpURLConnection;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by yosua.petra on 5/27/2016.
 */

public class AddToCartDialogFragment extends DialogFragment {
    Product mProduct;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    View mView;
    boolean canAdd = false;
    CustomSliderPagerAdapter mPagerAdapter;
    String mCurrentImage;

    // icon
    MenuItem mIconWishList;
    MenuItem mIconShoppingCart;

    // Loading bar
    ProgressBar mProgressBar;

    private String getSelectedColorCode()
    {
        String foundValue = "";
        LinearLayout _ll =  (LinearLayout) mPagerAdapter.getItem(1).getView().findViewById(R.id.containerPicker);
        for(int i = 0 ; i < _ll.getChildCount() ; i++)
        {
            CheckedTextView tv = (CheckedTextView) _ll.getChildAt(i);
            if(tv.isChecked())
                foundValue = tv.getText().toString();
        }

        for(ProductColor col : mProduct.getProductColors())
            if(col.getColor().equals(foundValue))
                return col.getCode();

        return "";
    }

    private String getSeletedSizeCode()
    {
        String foundValue = "";
        LinearLayout _ll =  (LinearLayout) mPagerAdapter.getItem(0).getView().findViewById(R.id.containerPicker);
        for(int i = 0 ; i < _ll.getChildCount() ; i++)
        {
            CheckedTextView tv = (CheckedTextView) _ll.getChildAt(i);
            if(tv.isChecked())
                foundValue = tv.getText().toString();
        }

        for(ProductSize col : mProduct.getProductSizes())
            if(col.getSize().equals(foundValue))
                return col.getCode();

        return "";
    }

//    private void addToCart()
//    {
//        String colorCode = getSelectedColorCode();
//        String sizeCode = getSeletedSizeCode();
//
//
////        Call<ResponseBody> call = RestProvider.getResourceRest().addShoppingCart(Session.getHeaderAuthorization(),
////               mProduct.getCode(),
////                colorCode,
////                sizeCode);
////        call.enqueue(new Callback<ResponseBody>() {
////            @Override
////            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
////                Toast.makeText(getContext(), "Berhasil ke shoppingCart", Toast.LENGTH_SHORT).show();
////            }
////
////            @Override
////            public void onFailure(Call<ResponseBody> call, Throwable t) {
////
////            }
////        });
//    }

    @Override
    public void onStart(){
        super.onStart();
        if(getDialog() == null) // safety check
            return;

        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }

    public AddToCartDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

//    public void setCurrentImage(Bitmap x){
//        this.currentImage = x;
//    }

    public static String PARAM_1 = "PARAM_1";
    public static String PARAM_2 = "PARAM_2";

    public static AddToCartDialogFragment newInstance(Product product, String currentImagePath) {
        AddToCartDialogFragment frag = new AddToCartDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_1, product);
        args.putSerializable(PARAM_2, currentImagePath);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mProduct = (Product)getArguments().getSerializable(PARAM_1);
        this.mCurrentImage = getArguments().getString(PARAM_2);
        return inflater.inflate(R.layout.fragment_add_to_cart, container);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        mIconWishList = (MenuItem) menu.findItem(R.id.menu_item_wish_list);
        mIconShoppingCart = (MenuItem) menu.findItem(R.id.menu_item_shopping_bag);
    }

    private void initTab()
    {
        this.mTabLayout = (TabLayout) mView.findViewById(R.id.product_size_color_tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Size"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Colour"));

        mViewPager = (ViewPager) mView.findViewById(R.id.viewPagerColorSizePicker);
        mPagerAdapter = new CustomSliderPagerAdapter(getContext(), getChildFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private void initButton()
    {
        Button cancelButton = (Button) mView.findViewById(R.id.button_size_color_pick_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Button okButton = (Button) mView.findViewById(R.id.button_size_color_pick_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!canAdd)
                    return;



                final View decorView = getDialog().getWindow().getDecorView();

                ObjectAnimator goUp = ObjectAnimator.ofPropertyValuesHolder(decorView,
                        PropertyValuesHolder.ofFloat("scaleX", 0.5f, 0.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 0.5f, 0.0f),
                        PropertyValuesHolder.ofFloat("alpha", 0.9f, 0.0f),
                        PropertyValuesHolder.ofFloat("translationY",300, -1100 ),
                        PropertyValuesHolder.ofFloat("translationX",-200, 550));

                goUp.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        addToCart();
                        dismiss();
                    }

                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                });

//
//                final ImageView _img = (ImageView) mView.findViewById(R.id.zoomableImageView);
//                ObjectAnimator scaleDownPhoto = ObjectAnimator.ofPropertyValuesHolder(_img,
//                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f),
//                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f));
//                scaleDownPhoto.setDuration(100);

                ObjectAnimator goDown = ObjectAnimator.ofPropertyValuesHolder(decorView,
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 0.5f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 0.5f),
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.9f),
                        PropertyValuesHolder.ofFloat("translationY",0, 300),
                        PropertyValuesHolder.ofFloat("translationX",0, -200));

                goDown.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        ((Button) mView.findViewById(R.id.button_size_color_pick_ok)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });


                final ImageView _imageCover = (ImageView) mView.findViewById(R.id.imageViewOverlap);
                final LinearLayout _linearLayoutContent = (LinearLayout) mView.findViewById(R.id.linear_container_content_add_to_cart);

                ObjectAnimator removeLinearLayout =  ObjectAnimator.ofPropertyValuesHolder(_linearLayoutContent,
                        PropertyValuesHolder.ofFloat("alpha", 1.0f, 0.0f)
                );
                ObjectAnimator showImageCover =  ObjectAnimator.ofPropertyValuesHolder(_imageCover,
                        PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("scaleX", 1.0f, 1.0f),
                        PropertyValuesHolder.ofFloat("scaleY", 1.0f, 1.0f)
                );

                showImageCover.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        _imageCover.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {

                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });

                removeLinearLayout.setDuration(50);
                showImageCover.setDuration(250);

                AnimatorSet switchOverlay = new AnimatorSet();
                switchOverlay.play(removeLinearLayout).with(showImageCover);
                switchOverlay.start();

                goDown.setDuration(500);
                goUp.setDuration(500);

                AnimatorSet addToCartAnimation = new AnimatorSet();
                addToCartAnimation.play(goDown).before(goUp);
                addToCartAnimation.start();
            }
        });
        refreshButton();
//        addToCart();
    }

    private void initDownloadImage()
    {
        Picasso.with(getContext()).load(this.mCurrentImage).into(target);
    }

    private Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.add_to_cart_loading_bar);
            LinearLayout ll = (LinearLayout) mView.findViewById(R.id.linear_container_content_add_to_cart) ;
            ll.setVisibility(View.VISIBLE);
            _progressBar.setVisibility(View.INVISIBLE);
            ImageView _imgOverlap = (ImageView) mView.findViewById(R.id.imageViewOverlap);
            _imgOverlap.setImageBitmap( bitmap);
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.add_to_cart_loading_bar);
            LinearLayout ll = (LinearLayout) mView.findViewById(R.id.linear_container_content_add_to_cart) ;
            _progressBar.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "Failed to load size and colour available", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.add_to_cart_loading_bar);
            LinearLayout ll = (LinearLayout) mView.findViewById(R.id.linear_container_content_add_to_cart) ;
            _progressBar.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
//            Toast.makeText(getContext(), "Failed to load size and colour available", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.mView = view;

        initTab();
        initButton();
        initDownloadImage();

//        if(!canAdd)
//            Toast.makeText(getContext(), "Please select the size and colour", Toast.LENGTH_SHORT).show();
    }

    private class CustomSliderPagerAdapter extends FragmentPagerAdapter {
        ArrayList<SizeColorPickerFragment> mFragments = new ArrayList<>();
        Context ctx;
        public CustomSliderPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            this.ctx = ctx;
        }

        @Override
        public SizeColorPickerFragment getItem(int position) {
            if(position == 0)
            {
                if(mFragments.size() > 0)
                    return mFragments.get(0);

                ArrayList<String> a = new ArrayList<>();
                for(ProductSize x : mProduct.getProductSizes())
                    a.add(x.getSize());

                SizeColorPickerFragment frag = SizeColorPickerFragment.newInstance(a);
                mFragments.add(0, frag);
                return frag;
            }
            else
            {
                if(mFragments.size() > 1)
                    return mFragments.get(1);

                ArrayList<String> a = new ArrayList<>();
                for(ProductColor x : mProduct.getProductColors())
                    a.add(x.getColor());

                SizeColorPickerFragment frag = SizeColorPickerFragment.newInstance(a);
                mFragments.add(1, frag);
                return frag;
            }
        }

        @Override
        public int getCount() {
            return mTabLayout.getTabCount();
        }
    }

    public void refreshButton()
    {
        int hasNotSelectedCtr = 0;

        if(mPagerAdapter.getItem(0).isNothingSelected())
            hasNotSelectedCtr++;

        if(mPagerAdapter.getItem(1).isNothingSelected())
            hasNotSelectedCtr++;

        Button _btnOk = (Button) mView.findViewById(R.id.button_size_color_pick_ok);

        if(hasNotSelectedCtr > 0) {
            canAdd = false;
            _btnOk.setTextColor(Color.parseColor("#d3d3d3"));
        }
        else {
            canAdd = true;
            _btnOk.setTextColor(Color.parseColor("#000000"));
        }
    }

    private void addToCart()
    {
        if (Session.isValid()) {
            Call<ShoppingCartResponse> call = RestProvider.getResourceRest().addShoppingCart(Session.getHeaderAuthorization(),
                    mProduct.getCode(),
                    getSelectedColorCode(),
                    getSeletedSizeCode());
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
            orderDetail.setProductCode(mProduct.getCode());
            orderDetail.setProductColorCode(getSelectedColorCode());
            orderDetail.setProductSizeCode(getSeletedSizeCode());
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
}
