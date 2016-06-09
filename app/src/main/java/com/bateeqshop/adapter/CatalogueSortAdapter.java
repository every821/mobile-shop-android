package com.bateeqshop.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bateeqshop.R;
import com.bateeqshop.fragments.SortDialog;
import com.bateeqshop.model.SortModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Daniel.Nababan on 5/30/2016.
 */
public class CatalogueSortAdapter extends BaseAdapter {

    Context mContext;
    LayoutInflater mInflater;
    List<SortModel> listSortModel;
    public String selectedSortText;

    public int selected;
    SortDialog parent;

    public CatalogueSortAdapter(Context mContext, LayoutInflater mInflater,  SortDialog parent) {

        List<SortModel> listSortText = new ArrayList<SortModel>();
        listSortText.add(new SortModel("price", "Price Low to High", true,false));
        listSortText.add(new SortModel("price", "Price High to Low", false,false));
        listSortText.add(new SortModel("name", "Name A - Z", true,true));
        listSortText.add(new SortModel("name", "Name Z - A", false,false));

        this.mContext = mContext;
        this.mInflater = mInflater;
        this.listSortModel = listSortText;
        this.parent = parent;
    }

    @Override
    public int getCount() {
        return listSortModel.size();
    }

    @Override
    public Object getItem(int position) {
        return listSortModel.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.sort_catalogue_item,null);
            viewHolder = new ViewHolder();

            viewHolder.sortTextView = (TextView) convertView.findViewById(R.id.textSortBy);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.checkImage);
            viewHolder.ascDescTextView= (TextView)convertView.findViewById(R.id.ascdesc);
            viewHolder.sortColumnTextView = (TextView)convertView.findViewById(R.id.sort_column);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        SortModel sort = (SortModel)getItem(position);
        viewHolder.sortTextView.setText(sort.getSortText());
        viewHolder.ascDescTextView.setText((sort.isAsc()) ? "asc" : "desc");
        viewHolder.sortColumnTextView.setText(sort.getSortBy());

//        if(tempSelectedSortText.equalsIgnoreCase(viewHolder.sortTextView.getText().toString())){
////            Log.d(CatalogueSortAdapter.class.getSimpleName(), ((TextView) viewHolder.sortTextView).getText().toString() + " : VISIBLE!!");
//            viewHolder.imageView.setVisibility(View.VISIBLE);
//        }else{
////            Log.d(CatalogueSortAdapter.class.getSimpleName(),((TextView)viewHolder.sortTextView).getText().toString() + " : GONE!!");
//            viewHolder.imageView.setVisibility(View.INVISIBLE);
//        }
        viewHolder.imageView.setVisibility(sort.isSelected() ? View.VISIBLE : View.GONE);

        return convertView;
    }

    public void updateSelected(int position){
        for(int i = 0; i< getCount();i++){
            ((SortModel)getItem(i)).setSelected(i==position);
        }
        notifyDataSetChanged();
    }

    public int getSelected(){
        for(int i = 0; i< getCount();i++){
            if(((SortModel)getItem(i)).isSelected()){
                return i;
            }
        }
        return 0;
    }

    public static class ViewHolder{
        public TextView sortTextView;
        public TextView ascDescTextView;
        public ImageView imageView;
        public TextView sortColumnTextView;
    }
}
