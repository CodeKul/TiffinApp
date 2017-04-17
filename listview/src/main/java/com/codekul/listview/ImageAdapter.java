package com.codekul.listview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aniruddha on 17/4/17.
 */

public class ImageAdapter extends BaseAdapter {

    private final Context context;
    private final List<ImageItem> dataSet;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, List<ImageItem> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return dataSet.get(position).itmId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final View view = inflater.inflate(R.layout.image_item, parent, false);

        ImageView imgVw = (ImageView) view.findViewById(R.id.imgVw);
        imgVw.setImageResource(dataSet.get(position).img);

        TextView txtVw = (TextView) view.findViewById(R.id.txtVw);
        txtVw.setText(dataSet.get(position).imgNm);

        return view;
    }

    public void addNewItem(ImageItem item) {
        dataSet.add(item);
        notifyDataSetChanged();
    }
}
