package com.bateeqshop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bateeqshop.MainNavigator;
import com.bateeqshop.R;
import com.bateeqshop.adapter.NavExpandableListAdapter;
import com.bateeqshop.model.ProductCategory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavContentFragment extends Fragment {

    private static String TAB_TITLE_ARGS_PARAM = "param1";
    private static String PARENT_CODE_ARGS_PARAM = "param2";

    private Context mContext;
    private View mView;
    private ExpandableListAdapter mAdapter;

    private int mTabNumber;
    private String mTabTitle;
    private List<ProductCategory> mLevel1SubProductCategoryList = new ArrayList<>();
    private List<ProductCategory> mListDataHeader = new ArrayList<>();
    private HashMap<String, List<ProductCategory>> mListDataChild = new HashMap<>();

    public NavContentFragment() {
        // Required empty public constructor
    }

    public static NavContentFragment newInstance(ProductCategory productCategory)
    {
        NavContentFragment fragment = new NavContentFragment();
        Bundle args = new Bundle();
        args.putString(TAB_TITLE_ARGS_PARAM, productCategory.getName());
        args.putString(PARENT_CODE_ARGS_PARAM, productCategory.getCode());
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mTabTitle = getArguments().getString(TAB_TITLE_ARGS_PARAM);
        String parentCode = getArguments().getString(PARENT_CODE_ARGS_PARAM);
        mListDataHeader = ProductCategory.getProductCategoriesByParentCode(parentCode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mView = view;
        prepareListData();

        final ExpandableListView nav_content_expandable_list = (ExpandableListView) view.findViewById(R.id.nav_content_expandable_list);
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                nav_content_expandable_list.setIndicatorBounds(mView.getWidth() - 80, mView.getWidth() - 40);
            }
        });

        mAdapter = new NavExpandableListAdapter(mContext, mListDataHeader, mListDataChild, nav_content_expandable_list);
        nav_content_expandable_list.setAdapter(mAdapter);

        nav_content_expandable_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String key = mListDataHeader.get(groupPosition).getCode();
                String path = mListDataChild.get(key).get(childPosition).getPath();
//                Toast.makeText(mContext, "header : " + key + " child : " + mListDataChild.get(key).get(childPosition).getPath(), Toast.LENGTH_LONG).show();
                MainNavigator.showCatalogueFragment(mContext,"", path);

                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

                return true;
            }
        });

        if (mListDataChild.size() <= 0) {
            nav_content_expandable_list.setGroupIndicator(null);
            nav_content_expandable_list.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                    Toast.makeText(mContext, "header : " + mListDataHeader.get(groupPosition).getPath(), Toast.LENGTH_LONG).show();
                    String path = mListDataHeader.get(groupPosition).getPath();
                    MainNavigator.showCatalogueFragment(mContext,"", path);

                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);

                    return true;
                }
            });
        }
    }



    private void prepareListData() {
        for(ProductCategory subItem1 : mListDataHeader)
        {
            List<ProductCategory> dataChild = ProductCategory.getProductCategoriesByParentCode(subItem1.getCode());
            if (dataChild.size() > 0) mListDataChild.put(subItem1.getCode(), dataChild);
        }
    }

}
