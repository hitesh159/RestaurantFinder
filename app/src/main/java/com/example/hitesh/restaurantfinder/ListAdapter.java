package com.example.hitesh.restaurantfinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hitesh on 1/18/2018.
 */

public class ListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ListModel> mListModel;

    public ListAdapter(Context mContext, List<ListModel> mListModel) {
        this.mContext = mContext;
        this.mListModel = mListModel;
    }

    @Override
    public int getCount() {
        return mListModel.size();
    }

    @Override
    public Object getItem(int i) {
        return mListModel.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v=View.inflate(mContext, R.layout.item_list,null);
        TextView name=(TextView)v.findViewById(R.id.name);
        TextView add=(TextView)v.findViewById(R.id.address);
        TextView loc=(TextView)v.findViewById(R.id.locality);
        name.setText(mListModel.get(i).getCity());
        add.setText(mListModel.get(i).getAddress());
        loc.setText(mListModel.get(i).getLocality());
        v.setTag(mListModel.get(i).getId());
        return v;
    }
}
