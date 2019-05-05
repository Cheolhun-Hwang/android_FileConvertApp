package com.hch.hooney.filecontrolproject.Views;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hch.hooney.filecontrolproject.Do.MainData;
import com.hch.hooney.filecontrolproject.R;

import java.util.ArrayList;

public class PageView extends LinearLayout {
    private LinearLayout parent;
//    private TextView page_num;
    private Context context;
    public PageView(Context context) {
        super(context);
        this.context = context;
        setView();
    }

    private void setView() {
        View view = LayoutInflater.from(context).inflate(R.layout.page_layout, this, false);
        parent = view.findViewById(R.id.page_parent);
//        page_num = view.findViewById(R.id.page_num);
        addView(view);
    }

    public void addItem(ArrayList<MainData> list, int page){
        for(MainData item : list){
            ItemView itemview = new ItemView(context);
            itemview.setContents(
                    item.getM_name(),
                    item.getM_postNumber(),
                    item.getM_address1(),
                    item.getM_address2()
            );
            parent.addView(itemview);
        }

        TextView pageText = new TextView(context);
        pageText.setTextSize(12);
        pageText.setGravity(Gravity.CENTER);
        pageText.setText("- "+page+" -");
        parent.addView(pageText);
    }
}
