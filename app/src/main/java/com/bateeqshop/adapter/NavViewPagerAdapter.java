package com.bateeqshop.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bateeqshop.fragments.NavContentFragment;
import com.bateeqshop.model.ProductCategory;

import java.util.List;

public class NavViewPagerAdapter extends FragmentStatePagerAdapter {
    private int PAGE_INDEX;
    private int PAGE_COUNT;
    private String tabTitles[];
    private Fragment mFragment[];

    public NavViewPagerAdapter(FragmentManager fragmentmanager)
    {
        super(fragmentmanager);

        List<ProductCategory> headerList = ProductCategory.getProductCategoriesByLevel(0);
        PAGE_COUNT = headerList.size();

        PAGE_INDEX = 0;
        tabTitles = new String[PAGE_COUNT];
        mFragment = new Fragment[PAGE_COUNT];
        while (PAGE_INDEX < PAGE_COUNT) {
            tabTitles[PAGE_INDEX] =  headerList.get(PAGE_INDEX).getName();
            mFragment[PAGE_INDEX] = NavContentFragment.newInstance(headerList.get(PAGE_INDEX));
            PAGE_INDEX++;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragment[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
