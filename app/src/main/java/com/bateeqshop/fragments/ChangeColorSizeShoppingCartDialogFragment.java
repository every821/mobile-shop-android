package com.bateeqshop.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.bateeqshop.R;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.ProductColor;
import com.bateeqshop.model.ProductSize;

import java.util.ArrayList;

/**
 * Created by yosua.petra on 5/27/2016.
 */

public class ChangeColorSizeShoppingCartDialogFragment extends DialogFragment {
    Product mProduct;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    View mView;
    boolean canAdd = true;
    CustomSliderPagerAdapter mPagerAdapter;
    String mShowFor;
    String mPreviouslySelectedColor;
    String mPreviouslySelectedSize;
    String position;
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
            if(col.getColor() == foundValue)
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
            if(col.getSize()== foundValue)
                return col.getCode();

        return "";
    }


    private String getSelectedColor()
    {
        String foundValue = "";
        LinearLayout _ll =  (LinearLayout) mPagerAdapter.getItem(1).getView().findViewById(R.id.containerPicker);
        for(int i = 0 ; i < _ll.getChildCount() ; i++)
        {
            CheckedTextView tv = (CheckedTextView) _ll.getChildAt(i);
            if(tv.isChecked())
                return tv.getText().toString();
        }
        return "";
    }

    private String getSeletedSize()
    {
        String foundValue = "";
        LinearLayout _ll =  (LinearLayout) mPagerAdapter.getItem(0).getView().findViewById(R.id.containerPicker);
        for(int i = 0 ; i < _ll.getChildCount() ; i++)
        {
            CheckedTextView tv = (CheckedTextView) _ll.getChildAt(i);
            if(tv.isChecked())
                return tv.getText().toString();
        }
        return "";
    }

    private void addToCart()
    {
        String colorCode = getSelectedColorCode();
        String sizeCode = getSeletedSizeCode();
    }

    @Override
    public void onStart(){
        super.onStart();
        if(getDialog() == null) // safety check
            return;

        getDialog().getWindow().setWindowAnimations(
                R.style.dialog_animation_fade);
    }

    public ChangeColorSizeShoppingCartDialogFragment() {
        // Empty constructor is required for DialogFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static String PARAM_1 = "PARAM_1";
    public static String PARAM_2 = "PARAM_2";
    public static String PARAM_3 = "PARAM_3";
    public static String PARAM_4 = "PARAM_4";
    public static String PARAM_5 = "PARAM_5";

    public static ChangeColorSizeShoppingCartDialogFragment newInstance(Product product, String showFor, String selectedSize, String selectedColor, String position) {
        ChangeColorSizeShoppingCartDialogFragment frag = new ChangeColorSizeShoppingCartDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(PARAM_1, product);
        args.putSerializable(PARAM_2, showFor);
        args.putSerializable(PARAM_3, selectedSize);
        args.putSerializable(PARAM_4, selectedColor);
        args.putSerializable(PARAM_5, position);

        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mShowFor = getArguments().getString(PARAM_2);
        mPreviouslySelectedSize = getArguments().getString(PARAM_3);
        mPreviouslySelectedColor = getArguments().getString(PARAM_4);
        position = getArguments().getString(PARAM_5);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.mProduct = (Product)getArguments().getSerializable(PARAM_1);
        return inflater.inflate(R.layout.fragment_add_to_cart, container);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        if(mShowFor == "colour")
            mViewPager.setCurrentItem(1);
        else
            mViewPager.setCurrentItem(0);
    }

    private void updateProduct()
    {
//        Toast.makeText(getContext(), "Ganti Warna", Toast.LENGTH_SHORT).show();
        for(Fragment f : getFragmentManager().getFragments())
        {
            if(f.getClass() == ShoppingCartFragment.class) {
                ((ShoppingCartFragment) f).updateProduct(Integer.parseInt(position), getSeletedSize(), getSelectedColor());
            }
        }
//        a.updateProduct(Integer.parseInt(position), getSeletedSize(), getSelectedColor());
        dismiss();
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
                else {
                    updateProduct();
                }
            }
        });
        refreshButton();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        this.mView = view;

        initTab();
        initButton();
        ProgressBar _prog = (ProgressBar) mView.findViewById(R.id.add_to_cart_loading_bar);
        _prog.setVisibility(View.INVISIBLE);
    }

    private class CustomSliderPagerAdapter extends FragmentPagerAdapter {
        ArrayList<SizeColorShoppingCartPickerFragment> mFragments = new ArrayList<>();
        Context ctx;
        public CustomSliderPagerAdapter(Context ctx, FragmentManager fm) {
            super(fm);
            this.ctx = ctx;
        }

        @Override
        public SizeColorShoppingCartPickerFragment getItem(int position) {
            if(position == 0)
            {
                if(mFragments.size() > 0)
                    return mFragments.get(0);

                ArrayList<String> a = new ArrayList<>();
                for(ProductSize x : mProduct.getProductSizes())
                    a.add(x.getSize());

                SizeColorShoppingCartPickerFragment frag = SizeColorShoppingCartPickerFragment.newInstance(a, mPreviouslySelectedSize);
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

                SizeColorShoppingCartPickerFragment frag = SizeColorShoppingCartPickerFragment.newInstance(a,mPreviouslySelectedColor);
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
}
