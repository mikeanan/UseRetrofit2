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

    public ItemAdapter(Context context, int resource/*, List<String> items*/){//第3個參數沒有用到，因為項目資料是後來動態加入的
        super(context, resource/*, items*/);

        this.resource = resource;//儲存 自定義的 layout ID
//        this.items = items;
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

        TextView textView = (TextView) itemView.findViewById(R.id.itemTextView);//實現後，設定 TextView 及按鈕
        textView.setText(string);

        Button updateButton = (Button) itemView.findViewById(R.id.itemButtonUpdate);//開始設定 button listener
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customListner != null)
                    customListner.onButtonClickListner(position, R.id.itemButtonUpdate);//傳給主頁面，執行按鈕後的動作
            }
        });

        Button deleteButton = (Button) itemView.findViewById(R.id.itemButtonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(customListner != null)
                    customListner.onButtonClickListner(position, R.id.itemButtonDelete);//傳給主頁面，執行按鈕後的動作
            }
        });

        return itemView;
    }

    @Override
    public void add(String object) {//可以在這樣新增項目做進一步的處理
        super.add(object);
    }

    customButtonListener customListner;

    public interface customButtonListener {//宣告介面，在 主頁面實現 可以執行按下按鈕之後所需的動作
        public void onButtonClickListner(int position, int id);
    }

    public void setCustomButtonListner(customButtonListener listener) {//在主頁面執行，綁定主頁面實現的 listener
        this.customListner = listener;
    }
}
