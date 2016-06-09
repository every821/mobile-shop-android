package com.bateeqshop.fragments;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.util.SizeF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.adapter.CatalogueFilterAdapter;
import com.bateeqshop.adapter.CategoryFilterAdapter;
import com.bateeqshop.adapter.ColorFilterAdapter;
import com.bateeqshop.adapter.SizeFilterAdapter;
import com.bateeqshop.model.FilterModel;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel.Nababan on 5/31/2016.
 */
public class FilterDialog extends DialogFragment {

    String data;
    List<FilterModel> filterModelList;
    ListView listFilterItem;
    public CatalogueFragment mFragment;

    CatalogueFilterAdapter filterAdapter;
    CategoryFilterAdapter categoryFilterAdapter;
    ColorFilterAdapter colorFilterAdapter;
    SizeFilterAdapter sizeFilterAdapter;

    LinearLayout mainFilterLayout;
    LinearLayout priceFilterLayout;
    LinearLayout categoryFilterLayout;
    LinearLayout colorFilterLayout;
    LinearLayout sizeFilterLayout;

    RangeSeekBar priceSeekBar;
    TextView minimumTextView;
    TextView maximumTextView;

    LinearLayout currentLayout;
    TextView mainTitle;
    TextView title;

    public FilterDialog() {
    }

    public static FilterDialog newInstance(CatalogueFragment fragment){

        FilterDialog filterDialog = new FilterDialog();
        filterDialog.mFragment = fragment;
        return filterDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.fragment_filter_catalogue, container, false);

        listFilterItem = (ListView) rootView.findViewById(R.id.list_filter_option);
        mainFilterLayout = (LinearLayout) rootView.findViewById(R.id.mainFilterLayout);
        priceFilterLayout = (LinearLayout) rootView.findViewById(R.id.priceFilterLayout);
        categoryFilterLayout = (LinearLayout) rootView.findViewById(R.id.category_filter_layout);
        colorFilterLayout = (LinearLayout) rootView.findViewById(R.id.color_filter_layout);
        sizeFilterLayout = (LinearLayout) rootView.findViewById(R.id.size_filter_layout);


        mainTitle = (TextView) rootView.findViewById(R.id.filterTextView);
        title = (TextView) rootView.findViewById(R.id.filterTitleTextView);

        if(filterModelList == null) {
            filterModelList = new ArrayList<>();
            filterModelList.add(new FilterModel("Price", ""));
            filterModelList.add(new FilterModel("Category", ""));
            filterModelList.add(new FilterModel("Color", ""));
            filterModelList.add(new FilterModel("Size", ""));
        }

        filterAdapter = new CatalogueFilterAdapter(getContext(), this.getLayoutInflater(savedInstanceState),filterModelList,this);

        listFilterItem.setAdapter(filterAdapter);

        final FilterDialog dialog = this;
        listFilterItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = ((TextView) view.findViewById(R.id.textFilterName)).getText().toString();

                if (name.equalsIgnoreCase("price")) {
                    currentLayout = priceFilterLayout;
                    mainFilterLayout.setVisibility(View.GONE);
                    priceFilterLayout.setVisibility(View.VISIBLE);
                    mainTitle.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    title.setText(" < Price");

                    minimumTextView.setText("RP " + priceSeekBar.getSelectedMinValue());
                    maximumTextView.setText("RP " + priceSeekBar.getSelectedMaxValue());

                } else if (name.equalsIgnoreCase("category")) {
                    currentLayout = categoryFilterLayout;
                    mainFilterLayout.setVisibility(View.GONE);
                    categoryFilterLayout.setVisibility(View.VISIBLE);
                    mainTitle.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    title.setText(" < Category");
                } else if (name.equalsIgnoreCase("color")) {

                    currentLayout = colorFilterLayout;
                    mainFilterLayout.setVisibility(View.GONE);
                    colorFilterLayout.setVisibility(View.VISIBLE);
                    mainTitle.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    title.setText(" < Color");
                }else if (name.equalsIgnoreCase("size")) {

                    currentLayout = sizeFilterLayout;
                    mainFilterLayout.setVisibility(View.GONE);
                    sizeFilterLayout.setVisibility(View.VISIBLE);
                    mainTitle.setVisibility(View.GONE);
                    title.setVisibility(View.VISIBLE);
                    title.setText(" < Size");
                }
            }
        });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
            }
        });

        initializePriceSeekBar(rootView);
        initializePriceLayout(rootView);
        initializeFilterButton(rootView, this);
        initializeCancelButton(rootView,this);
        initializeFilterCategory(rootView, savedInstanceState);
        initializeFilterColor(rootView, savedInstanceState);
        initializeFilterSize(rootView,savedInstanceState);

        return rootView;
    }
    public void initializeCancelButton(View root,final FilterDialog filterDialog){
        Button cancelButton = (Button) root.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.dismiss();
            }
        });
    }

    public void initializePriceSeekBar(View root){
        priceSeekBar = (RangeSeekBar)root.findViewById(R.id.priceRangeSeekBar);
        priceSeekBar.setRangeValues(0,40000000);
        priceSeekBar.setTextAboveThumbsColorResource(R.color.bpTransparent);
        priceSeekBar.setNotifyWhileDragging(true);

        minimumTextView = (TextView)root.findViewById(R.id.min_price_text_view);
        maximumTextView = (TextView)root.findViewById(R.id.max_price_text_view);

        priceSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                minimumTextView.setText("RP " + minValue.toString());
                maximumTextView.setText("RP " + maxValue.toString());
            }
        });
    }

    public void initializePriceLayout(View root){
        Button priceFilterButton = (Button)root.findViewById(R.id.filter_price_button);
        priceFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterAdapter.updateValue("price", priceSeekBar.getSelectedMinValue().toString() + ";" + priceSeekBar.getSelectedMaxValue().toString());
                priceFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                mFragment.updateFilterQuery(Long.parseLong(priceSeekBar.getSelectedMinValue().toString()),Long.parseLong(priceSeekBar.getSelectedMaxValue().toString()));
            }
        });

        Button cancelPriceFilterButton = (Button) root.findViewById(R.id.cancel_filter_price_button);
        cancelPriceFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                priceFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
            }
        });
    }

    public void initializeFilterButton(View root, final FilterDialog filterDialog){
        Button filterButton = (Button) root.findViewById(R.id.filter_button);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFragment.requestQuery.resetPage();
                mFragment.clearData();
                filterDialog.dismiss();
            }
        });
    }

    public void initializeFilterCategory(View root,Bundle savedInstanceState){
        ListView categoryListView = (ListView) root.findViewById(R.id.category_filter_list);
        if(categoryFilterAdapter == null)
            categoryFilterAdapter  = new CategoryFilterAdapter(this.getLayoutInflater(savedInstanceState),this.getContext(), mFragment.vFilterQuery.getCategories());

        categoryListView.setAdapter(categoryFilterAdapter);
        categoryFilterAdapter.notifyDataSetChanged();

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((CategoryFilterAdapter.CategoryFilterModel) categoryFilterAdapter.getItem(position)).selected = !((CategoryFilterAdapter.CategoryFilterModel) categoryFilterAdapter.getItem(position)).selected;
                categoryFilterAdapter.notifyDataSetChanged();
            }
        });

        Button categoryFilterButton = (Button) root.findViewById(R.id.filter_category_button);
        categoryFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedCategory = categoryFilterAdapter.getSelectedItem();
                String strCategories = "";
                for (int i = 0; i < selectedCategory.size(); i++) {
                    strCategories += selectedCategory.get(i) + ((i == selectedCategory.size() - 1) ? "" : ",");
                }
                categoryFilterAdapter.updateSelected();
                filterAdapter.updateValue("category", strCategories);
                categoryFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                mFragment.updateFilterQuery("category", selectedCategory);
            }
        });

        Button cancelCategoryFilterButton = (Button) root.findViewById(R.id.cancel_filter_category_button);
        cancelCategoryFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryFilterAdapter.refreshSelected();
                categoryFilterAdapter.notifyDataSetChanged();
                categoryFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
            }
        });
    }

    public void initializeFilterColor(View root, Bundle savedInstanceState){
        ListView colorListView = (ListView) root.findViewById(R.id.color_filter_list);
        if(colorFilterAdapter == null)
            colorFilterAdapter  = new ColorFilterAdapter(this.getLayoutInflater(savedInstanceState),this.getContext(), mFragment.vFilterQuery.getColors());

        colorListView.setAdapter(colorFilterAdapter);
        colorFilterAdapter.notifyDataSetChanged();

        colorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((ColorFilterAdapter.ColorFilterModel) colorFilterAdapter.getItem(position)).selected = !((ColorFilterAdapter.ColorFilterModel) colorFilterAdapter.getItem(position)).selected;
                colorFilterAdapter.notifyDataSetChanged();
            }
        });

        Button colorFilterButton = (Button) root.findViewById(R.id.filter_color_button);
        colorFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedColor = colorFilterAdapter.getSelectedItem();
                String strColors = "";
                for (int i = 0; i < selectedColor.size(); i++) {
                    strColors += selectedColor.get(i) + ((i == selectedColor.size() - 1) ? "" : ",");
                }
                colorFilterAdapter.updateSelected();
                filterAdapter.updateValue("color", strColors);
                colorFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                mFragment.updateFilterQuery("color", selectedColor);
            }
        });

        Button cancelColorFilterButton = (Button) root.findViewById(R.id.cancel_filter_color_button);
        cancelColorFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorFilterAdapter.refreshSelected();
                colorFilterAdapter.notifyDataSetChanged();
                colorFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
            }
        });
    }

    public void initializeFilterSize(View root, Bundle savedInstanceState){
        ListView sizeListView = (ListView) root.findViewById(R.id.size_filter_list);
        if(sizeFilterAdapter == null)
            sizeFilterAdapter  = new SizeFilterAdapter(this.getLayoutInflater(savedInstanceState),this.getContext(), mFragment.vFilterQuery.getSizes());

        sizeListView.setAdapter(sizeFilterAdapter);
        sizeFilterAdapter.notifyDataSetChanged();

        sizeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ((SizeFilterAdapter.SizeFilterModel) sizeFilterAdapter.getItem(position)).selected = !((SizeFilterAdapter.SizeFilterModel) sizeFilterAdapter.getItem(position)).selected;
                sizeFilterAdapter.notifyDataSetChanged();
            }
        });

        Button sizeFilterButton = (Button) root.findViewById(R.id.filter_size_button);
        sizeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> selectedSize = sizeFilterAdapter.getSelectedItem();
                String strColors = "";
                for (int i = 0; i < selectedSize.size(); i++) {
                    strColors += selectedSize.get(i) + ((i == selectedSize.size() - 1) ? "" : ",");
                }
                sizeFilterAdapter.updateSelected();
                filterAdapter.updateValue("size", strColors);
                sizeFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
                mFragment.updateFilterQuery("size", selectedSize);
            }
        });

        Button cancelSizeFilterButton = (Button) root.findViewById(R.id.cancel_filter_size_button);
        cancelSizeFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeFilterAdapter.refreshSelected();
                sizeFilterAdapter.notifyDataSetChanged();
                sizeFilterLayout.setVisibility(View.GONE);
                mainFilterLayout.setVisibility(View.VISIBLE);
                mainTitle.setVisibility(View.VISIBLE);
                title.setVisibility(View.GONE);
            }
        });
    }
}
