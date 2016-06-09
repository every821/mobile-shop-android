package com.bateeqshop.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.bateeqshop.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by yosua.petra on 6/1/2016.
 */
public class SizeColorShoppingCartPickerFragment extends Fragment {
    Context mContext;
    List<String> mInputList;
    final static String PARAM_1 = "param1";
    final static String PARAM_2 = "param2";
    View mView;
    boolean hasSelected = true;
    String mPreviouslySelected ;
    boolean firstTime = true;

    public boolean isNothingSelected(){
        return !hasSelected;
    }

    private void populateView(int position)
    {
        LinearLayout _ly = (LinearLayout) mView.findViewById(R.id.containerPicker);
        _ly.removeAllViews();
        int counter = 0;
        counter++;
        for(String sizeOrColor : mInputList)
        {
            CheckedTextView cb = new CheckedTextView(getContext());

            // set 10 DP padding kiri
            float scale = getResources().getDisplayMetrics().density;
            int tenDp = (int) (10*scale + 0.5f);
            int eightDp = (int) (8.5*scale + 0.5f);
            cb.setPadding(tenDp,eightDp,eightDp,tenDp);
            cb.setCheckMarkDrawable(R.drawable.size_color_checkbox_selector);
            cb.setBackgroundResource(R.drawable.product_size_color_row_border_selector);
            cb.setGravity(Gravity.CENTER_VERTICAL);
            cb.setTextSize(15);
            cb.setClickable(true);
            cb.setTag(counter);
            if(firstTime && mPreviouslySelected.equals(sizeOrColor))
            {
                cb.setChecked(true);
                firstTime = false;
            }
            else
                cb.setChecked(counter == position);
            cb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!((CheckedTextView)v).isChecked()) {
                        hasSelected = true;
                        populateView((int) v.getTag());
                    }
                    else {
                        ((CheckedTextView) v).toggle();
                        hasSelected = false;
                    }
                    ((ChangeColorSizeShoppingCartDialogFragment)getParentFragment()).refreshButton();
                }
            });
            cb.setText(sizeOrColor);

            LinearLayout ll = (LinearLayout)mView.findViewById(R.id.containerPicker);
            ll.addView(cb);
            counter++;
        }
    }

    public static SizeColorShoppingCartPickerFragment newInstance(ArrayList<String> inputs, String selected)
    {
        SizeColorShoppingCartPickerFragment frag = new SizeColorShoppingCartPickerFragment();
        Bundle bundle = new Bundle();

        Collections.sort(inputs);
        bundle.putStringArrayList(PARAM_1, inputs);
        bundle.putString(PARAM_2, selected);

        frag.setArguments(bundle);
        return frag;
    }

    public SizeColorShoppingCartPickerFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        mContext = getContext();
        if(getArguments() != null) {
            mInputList = getArguments().getStringArrayList(PARAM_1);
            mPreviouslySelected = getArguments().getString(PARAM_2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View result=inflater.inflate(R.layout.fragment_tab_color_size_picker, container, false);
        mView = result;
        populateView(-1);
        return(result);
    }
}
