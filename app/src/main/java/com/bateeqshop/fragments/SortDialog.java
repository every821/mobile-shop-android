package com.bateeqshop.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.bateeqshop.R;
import com.bateeqshop.adapter.CatalogueSortAdapter;
import com.bateeqshop.model.SortModel;

/**
 * Created by Daniel.Nababan on 5/30/2016.
 */
public class SortDialog extends DialogFragment {
    ListView listSortItem;
    CatalogueSortAdapter sortAdapter;
    public CatalogueFragment mFragment;

    public static SortDialog newInstance(CatalogueFragment fragment){
        SortDialog sortDialog = new SortDialog();
        sortDialog.mFragment = fragment;

        return sortDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        return dialog;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_sort_catalogue,container,false);

        listSortItem = (ListView) rootView.findViewById(R.id.list_sort_option);

        if(sortAdapter == null)
        sortAdapter = new CatalogueSortAdapter(getContext(), this.getLayoutInflater(savedInstanceState), this);

        listSortItem.setAdapter(sortAdapter);

        final Button sortButton = (Button) rootView.findViewById(R.id.sort_button);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancel_button);

        final SortDialog sortDialog = this;
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SortModel selectedItem = (SortModel)sortAdapter.getItem(sortAdapter.getSelected());
                sortAdapter.selected = sortAdapter.getSelected();

                if (selectedItem.getSortBy().equalsIgnoreCase("price")) {
                    mFragment.requestQuery.setOrderBy("price");
                } else if (selectedItem.getSortBy().equalsIgnoreCase("name"))
                    mFragment.requestQuery.setOrderBy("name");


                if (selectedItem.isAsc()) {
                    mFragment.requestQuery.setSort("asc");
                } else {
                    mFragment.requestQuery.setSort("desc");
                }

                mFragment.requestQuery.resetPage();

                mFragment.clearData();

                sortDialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortAdapter.updateSelected(sortAdapter.selected);
                sortDialog.dismiss();
            }
        });

        listSortItem.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                sortAdapter.updateSelected(position);
            }
        });

        return rootView;
    }
}
