package com.example.abair.useretrofit2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by abair on 2016/11/29.
 */

public class ItemAdapter extends ArrayAdapter<String> {

    private int resource;
    private List<String> items;

    public ItemAdapter(Context context, int resource, List<String> items){
        super(context, resource, items);

        this.resource = resource;//儲存 自定義的 layout ID
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);

        LinearLayout itemView;
        String string = getItem(position);//得到目前項目位置的物件

        if( convertView == null){//若目前項目還沒有實現時，使用 layout inflater service 實現 自定義的 layout
            itemView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, itemView, true);
        }
        else {
            itemView = (LinearLayout) convertView;
        }

        return itemView;
    }
}
