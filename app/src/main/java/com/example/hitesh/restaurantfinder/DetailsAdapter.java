package com.example.hitesh.restaurantfinder;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by hitesh on 1/23/2018.
 */

public class DetailsAdapter extends BaseAdapter {
    private Context context;
    private List<Details> mDetails;

    public DetailsAdapter(Context context, List<Details> mDetails) {
        this.context = context;
        this.mDetails = mDetails;
    }

    @Override

    public int getCount() {
        return mDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return mDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.detail_list, null);
        TextView key = (TextView) v.findViewById(R.id.key);
        TextView value = (TextView) v.findViewById(R.id.value);
        key.setText(mDetails.get(i).getKey());
        value.setText(mDetails.get(i).getValue());
        return v;
    }
}
