package com.codekul.listview;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aniruddha on 9/6/17.
 */

public class CustomAdapter extends BaseAdapter {

    private final Context context;
    private final List<CustomItem> items;
    private LayoutInflater inflater;

    public CustomAdapter(Context context, List<CustomItem> items) {
        this.context = context;
        this.items = items;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return items.size(); // number of items from list cursor items
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = null;
        if (convertView == null)
            view = inflater.inflate(R.layout.custom_item, parent, false);
        else view = convertView;

        ((ImageView)view.findViewById(R.id.imgIcn)).setImageResource(items.get(position).img);
        view.findViewById(R.id.imgIcn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomItem item = items.get(position);

                DialogUtil util = new DialogUtil();

                if(context instanceof  FragmentActivity) {
                    FragmentActivity activity = (FragmentActivity) context;
                    util.show(activity.getSupportFragmentManager(), DialogUtil.TAG_ADDRESS);
                }
            }
        });
        ((TextView)view.findViewById(R.id.txtNm)).setText(items.get(position).nm);

        ((TextView)view.findViewById(R.id.txtVal1)).setText(items.get(position).val1);
        ((TextView)view.findViewById(R.id.txtVal2)).setText(items.get(position).val2);
        ((TextView)view.findViewById(R.id.txtVal3)).setText(items.get(position).val3);

        return view;
    }
}
