package com.bateeqshop.fragments;


import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bateeqshop.R;
import com.bateeqshop.adapter.CatalogueAdapter;
import com.bateeqshop.model.CatalogueFilter;
import com.bateeqshop.model.CatalogueFilterQuery;
import com.bateeqshop.model.Product;
import com.bateeqshop.model.RequestObjectList;
import com.bateeqshop.model.RequestQuery;
import com.bateeqshop.rest.RestProvider;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogueFragment extends Fragment {

    CatalogueAdapter catalogueAdapter;
    GridView collectionView;
    public RequestQuery requestQuery;
    public CatalogueFilterQuery filterQuery;
    public CatalogueFilterQuery vFilterQuery;

    boolean onRequest = false;
    ProgressBar progressBar;

    SortDialog sortDialog;
    FilterDialog filterDialog;

    Button sortButton;
    Button filterButton;
    ImageView scrollUp;

    private static final String KeywordParam = "keyword";
    private static final String PathParam = "path";

    private String keyword = "";
    private String path = "";

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public CatalogueFragment() {
        // Required empty public constructor
    }

    public static CatalogueFragment newInstance(String keyword, String path) {
        CatalogueFragment catalogueFragment = new CatalogueFragment();
        Bundle args = new Bundle();
        args.putSerializable(KeywordParam, keyword);
        args.putSerializable(PathParam, path);
        catalogueFragment.setArguments(args);
        catalogueFragment.filterQuery = new CatalogueFilterQuery();
        catalogueFragment.vFilterQuery = new CatalogueFilterQuery();
        return catalogueFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            keyword = getArguments().getSerializable(KeywordParam).toString();
            path = getArguments().getSerializable(PathParam).toString();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_catalogue, container, false);

        catalogueAdapter = new CatalogueAdapter(getContext(), this.getLayoutInflater(savedInstanceState));

        collectionView = (GridView) rootView.findViewById(R.id.gridView);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        initializeScrollUpButton(rootView);

        collectionView.setAdapter(catalogueAdapter);
        if (requestQuery == null)
            requestQuery = new RequestQuery();

        collectionView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount >= totalItemCount && !onRequest) {
                    if (requestQuery.getCount() >= requestQuery.getPage() * requestQuery.getSize()) {
                        onRequest = true;
                        requestQuery.setPage(requestQuery.getPage() + 1);
                        UpdateData();
                    }
                }

                if (firstVisibleItem > 0) {
                    scrollUp.setVisibility(View.VISIBLE);
                } else {
                    scrollUp.setVisibility(View.GONE);
                }
            }
        });

        final CatalogueFragment mFragment = this;

        initializeSortButton(rootView, this);


        getFilterData(rootView, this);

        return rootView;
    }


    public void initializeScrollUpButton(View root){
        scrollUp = (ImageView) root.findViewById(R.id.scroll_up_button);
        scrollUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectionView.smoothScrollToPosition(0);
            }
        });
    }

    public void initializeSortButton(View root, final CatalogueFragment mFragment) {
        sortButton = (Button) root.findViewById(R.id.sortButton);
        sortButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sortDialog == null) {
                    sortDialog = SortDialog.newInstance(mFragment);
                }
                sortDialog.show(getFragmentManager(), "Sort");
            }
        });
    }

    public void initializeFilterButton(View root, final CatalogueFragment mFragment) {
        filterButton = (Button) root.findViewById(R.id.filterButton);
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterDialog == null) {
                    filterDialog = FilterDialog.newInstance(mFragment);
                }
                filterDialog.show(getFragmentManager(), "Filter");
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (requestQuery == null)
            requestQuery = new RequestQuery();
        UpdateData();
    }

    public void getFilterData(final View root, final CatalogueFragment mFragment) {
        if (!keyword.isEmpty()) {
            Call<CatalogueFilter> call = RestProvider.getResourceRest().getFilterQuery(keyword);
            call.enqueue(new Callback<CatalogueFilter>() {
                @Override
                public void onResponse(Call<CatalogueFilter> call, Response<CatalogueFilter> response) {
                    vFilterQuery = response.body().getData();
                    initializeFilterButton(root, mFragment);
                }

                @Override
                public void onFailure(Call<CatalogueFilter> call, Throwable t) {
                    Log.d("check", t.getMessage());
                }
            });
        }else{
            Call<CatalogueFilter> call = RestProvider.getResourceRest().getFilterQueryByCategory(path);
            call.enqueue(new Callback<CatalogueFilter>() {
                @Override
                public void onResponse(Call<CatalogueFilter> call, Response<CatalogueFilter> response) {
                    vFilterQuery = response.body().getData();
                    initializeFilterButton(root, mFragment);
                }

                @Override
                public void onFailure(Call<CatalogueFilter> call, Throwable t) {
                    Log.d("check", t.getMessage());
                }
            });
        }
    }

    public void UpdateData() {
        progressBar.setVisibility(View.VISIBLE);
        Call<RequestObjectList<Product>> call = ((keyword.isEmpty()) ? RestProvider.getResourceRest().getProductByCategory(path, requestQuery.getQuery(), filterQuery.getQuery()) : RestProvider.getResourceRest().getProduct(keyword, requestQuery.getQuery(), filterQuery.getQuery()));

        call.enqueue(new Callback<RequestObjectList<Product>>() {
            @Override
            public void onResponse(Call<RequestObjectList<Product>> call, Response<RequestObjectList<Product>> response) {
                List<Product> listProduct = ((RequestObjectList) response.body()).data.result;
                requestQuery = (((RequestObjectList) response.body()).data.query);
                Log.d("data query : ", "page : " + requestQuery.getPage() + ", sortBy: " + requestQuery.getOrderBy());
                catalogueAdapter.updateData(listProduct);
                onRequest = false;
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<RequestObjectList<Product>> call, Throwable t) {
                Log.d("check", t.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void clearData() {
        catalogueAdapter.updateData(null);
    }

    public void updateFilterQuery(long minPrice, long maxPrice) {
        this.filterQuery.setMinPrice(minPrice);
        this.filterQuery.setMaxPrice(maxPrice);
    }

    public void updateFilterQuery(String name, List<String> values) {
        if (name.equalsIgnoreCase("category")) {
            this.filterQuery.getCategories().clear();
            this.filterQuery.getCategories().addAll(values);
        } else if (name.equalsIgnoreCase("color")) {
            this.filterQuery.getColors().clear();
            this.filterQuery.getColors().addAll(values);
        } else if (name.equalsIgnoreCase("size")) {
            this.filterQuery.getSizes().clear();
            this.filterQuery.getSizes().addAll(values);
        }
    }
}
