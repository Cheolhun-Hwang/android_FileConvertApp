package com.hch.hooney.filecontrolproject.Views;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hch.hooney.filecontrolproject.R;

public class ItemView extends LinearLayout {
    private TextView i_name, i_post, i_adr1, i_adr2;

    public ItemView(Context context) {
        super(context);

        setView(context);
    }

    private void setView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.page_item_layout, this, false);
        i_name = view.findViewById(R.id.item_name);
        i_post = view.findViewById(R.id.item_post);
        i_adr1 = view.findViewById(R.id.item_adr1);
        i_adr2 = view.findViewById(R.id.item_adr2);
        addView(view);
    }

    public void setContents(String name, String post, String adr1, String adr2){
        i_name.setText(name);
        i_post.setText("[ "+post+" ]");
        i_adr1.setText(adr1);
        i_adr2.setText(adr2);
    }
}
