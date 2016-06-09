package com.bateeqshop.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.LoginNavigator;
import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.Session;
import com.bateeqshop.config.ApiConfig;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.ProductImage;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;


/**
 * Created by yosua.petra on 5/26/2016.
 */

/**
 * Uses a combination of a PageTransformer and swapping X & Y coordinates
 * of touch events to create the illusion of a vertically scrolling ViewPager.
 *
 * Requires API 11+
 *
 */


public class ProductFragment extends Fragment {
    public static int LOOPS_COUNT = 100;
    public ArrayList<String> ImagesPath;
    private ViewPager mPager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private Context mContext;
    private View mView;
    private Product mProduct;
//    public Bitmap currentImage;

    private static final String ARG_PARAM1 = "param1";

    public ProductFragment() {
        // Required empty public constructor
    }

    public static ProductFragment newInstance(Product param1) {
        ProductFragment fragment = new ProductFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    private void ShareProduct()
    {
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "Hello! Look, I found something great that might be interesting to you. Check it here: http://bateeqshop.com/ShopProduct?id=" + mProduct.getCode();
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Bateeq");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext = getContext();
        if (getArguments() != null) {
            mProduct = (Product) getArguments().getSerializable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        initView();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initTextViews()
    {
        TextView _productName = (TextView) mView.findViewById(R.id.txtProductTitle);
        _productName.setText(this.mProduct.getName());

        TextView _productCode = (TextView) mView.findViewById(R.id.txtProductCode);
        String moneyValue = "";
        DecimalFormat df = new DecimalFormat("###,###", new DecimalFormatSymbols(Locale.US));
        moneyValue = df.format(mProduct.getPrice());
        _productCode.setText("IDR " + moneyValue);
    }

    private void initImageSliderAndHoverButtons()
    {
        ImagesPath = new ArrayList<>();
        for (ProductImage imagePath : mProduct.getProductImages()) {
            ImagesPath.add(ApiConfig.IMAGE_BASE_URL + imagePath.getTitle());
        }

//        Picasso.with(mContext).load(ApiConfig.IMAGE_BASE_URL + mProduct.getPrimaryImage()).error(R.drawable.noimage).into(target);

        mPager = (ViewPager) mView.findViewById(R.id.img_slider_product) ;
        if(ImagesPath.size() == 0)
            mPagerAdapter = new ScreenSlidePagerAdapter(mContext, getChildFragmentManager(), false);
        else
            mPagerAdapter = new ScreenSlidePagerAdapter(mContext, getChildFragmentManager(), true);

        mPager.setAdapter(mPagerAdapter);
        mPager.setCurrentItem(ImagesPath.size() == 0 ? 0 : ImagesPath.size() * LOOPS_COUNT / 2, false);

        FrameLayout leftNav = (FrameLayout)mView.findViewById(R.id.left_nav);
        leftNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();

                if(mPagerAdapter.getCount() == 1)
                    mPager.setCurrentItem(tab, true);
                else {
                    if (tab > 0) {
                        mPager.setCurrentItem(tab - 1, true);
                    } else if (tab == 0) {
                        mPager.setCurrentItem(mPagerAdapter.getCount(), true);
                    }
                }
            }
        });

        FrameLayout rightNav = (FrameLayout) mView.findViewById(R.id.right_nav);
        rightNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mPager.getCurrentItem();
                if(mPagerAdapter.getCount() == 1)
                    mPager.setCurrentItem(tab);
                else {
                    if(tab == mPagerAdapter.getCount() - 1)
                        mPager.setCurrentItem(0);
                    else
                        mPager.setCurrentItem(tab+1);
                }
            }
        });

        ImageButton _btnShare = (ImageButton) mView.findViewById(R.id.product_share);
        _btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShareProduct();
            }
        });
    }

    private void initButtons()
    {
        Button _btnDetails = (Button) mView.findViewById(R.id.btn_product_details);
        _btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainNavigator.showProductDetailFragment(mContext, mProduct);
            }
        });

        Button _btnAddToCart = (Button) mView.findViewById(R.id.btn_product_add_to_cart);
        _btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Session.isValid()) {
                    MainNavigator.showAddToCartDialogFragment(mContext, mProduct, ImagesPath.size() > 0 ? ImagesPath.get(mPager.getCurrentItem() % ImagesPath.size()) : "");
                }
                else
                {
                    LoginNavigator.navigateToLoginActivity(mContext);
                }
            }
        });
    }

    private void initView()
    {
        initTextViews();
        initImageSliderAndHoverButtons();
        initButtons();
    }
//
//    private Target target = new Target() {
//        @Override
//        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//            currentImage = bitmap;
//            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.curentImageLoadingBar);
//            _progressBar.setVisibility(View.INVISIBLE);
//        }
//
//        @Override
//        public void onBitmapFailed(Drawable errorDrawable) {
//            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.curentImageLoadingBar);
//            _progressBar.setVisibility(View.INVISIBLE);
//        }
//
//        @Override
//        public void onPrepareLoad(Drawable placeHolderDrawable) {
//            ProgressBar _progressBar = (ProgressBar) getView().findViewById(R.id.curentImageLoadingBar);
//            _progressBar.setVisibility(View.VISIBLE);
//        }
//    };

    private class ScreenSlidePagerAdapter extends FragmentPagerAdapter {
        Context ctx;
        boolean hasImage;

        public ScreenSlidePagerAdapter(Context ctx, FragmentManager fm, boolean hasImage) {
            super(fm);
            this.ctx = ctx;
            this.hasImage = hasImage;
        }

        @Override
        public Fragment getItem(int position) {

            if (hasImage)
            {
                int currentPosition = position % ImagesPath.size();  // use modulo for infinite cycling
                return ImageSlideFragment.newInstance(position, ImagesPath.get(currentPosition), false);
            }
            else
            {
                return ImageSlideFragment.newInstance(0,"", true);
            }
        }

        @Override
        public int getCount() {
            if (ImagesPath != null && ImagesPath.size() > 0)
            {
                return ImagesPath.size()*LOOPS_COUNT; // simulate infinite by big number of products
            }
            else
            {
                return 1;
            }
        }
    }
}
