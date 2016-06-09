package com.bateeqshop.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bateeqshop.R;

/**
 * Created by yosua.petra on 6/7/2016.
 */
public class RemoveItemShoppingCartDialogFragment extends DialogFragment {
    public static String PARAM_1 = "kucing";

    private int mPosition;
    public RemoveItemShoppingCartDialogFragment()
    {

    }

    private void callParentRemoveFunction()
    {
        for(Fragment f : getFragmentManager().getFragments())
        {
            if(f.getClass() == ShoppingCartFragment.class) {
                ((ShoppingCartFragment) f).removeProduct(mPosition);
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_remove_from_shopping_cart, null);

        Button _but = (Button) view.findViewById(R.id.remove_item_from_shopping_cart_no);
        _but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mPosition = getArguments().getInt(PARAM_1);
        Button _buttonYes = (Button) view.findViewById(R.id.remove_item_from_shopping_cart_yes);
        _buttonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callParentRemoveFunction();
                dismiss();
            }
        });

        return view;
    }

    public static RemoveItemShoppingCartDialogFragment newInstance(int position)
    {
        Bundle a = new Bundle();
        a.putInt(PARAM_1, position);
        RemoveItemShoppingCartDialogFragment b = new RemoveItemShoppingCartDialogFragment();
        b.setArguments(a);
        return b;
    }
}
