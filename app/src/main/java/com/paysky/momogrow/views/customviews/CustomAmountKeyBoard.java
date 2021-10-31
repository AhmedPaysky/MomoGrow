package com.paysky.momogrow.views.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.paysky.momogrow.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Abdulrahman Rudwan on 20/02/2018.
 */

public class CustomAmountKeyBoard extends LinearLayout {
    public View rootView;
    Unbinder unbinder;

    @BindView(R.id.text_1)
    public TextView txt_1;

    @BindView(R.id.text_2)
    public TextView txt_2;

    @BindView(R.id.text_3)
    public TextView txt_3;

    @BindView(R.id.text_4)
    public TextView txt_4;

    @BindView(R.id.text_5)
    public TextView txt_5;

    @BindView(R.id.text_6)
    public TextView txt_6;

    @BindView(R.id.text_7)
    public TextView txt_7;

    @BindView(R.id.text_8)
    public TextView txt_8;


    @BindView(R.id.text_9)
    public TextView txt_9;


    @BindView(R.id.text_0)
    public TextView txt_0;


    @BindView(R.id.text_00)
    public TextView txt_00;

    @BindView(R.id.RemoveLast)
    public ImageView RemoveLast;


    public String val = "";
    public String value = "";
    public int lenght = 9;

    ItemClickListener itemClickListener;

    public CustomAmountKeyBoard(Context context) {
        super(context);
        init();
    }

    public void SetitemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public CustomAmountKeyBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomAmountKeyBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.custom_keyboard, this);
        unbinder = ButterKnife.bind(this, rootView);
        txt_1 = rootView.findViewById(R.id.text_1);
        txt_2 = rootView.findViewById(R.id.text_2);
        txt_3 = rootView.findViewById(R.id.text_3);
        txt_4 = rootView.findViewById(R.id.text_4);
        txt_5 = rootView.findViewById(R.id.text_5);
        txt_6 = rootView.findViewById(R.id.text_6);
        txt_7 = rootView.findViewById(R.id.text_7);
        txt_8 = rootView.findViewById(R.id.text_8);
        txt_9 = rootView.findViewById(R.id.text_9);
        txt_0 = rootView.findViewById(R.id.text_0);
        RemoveLast = rootView.findViewById(R.id.RemoveLast);
        txt_1.setOnClickListener(this::clicked);
        txt_2.setOnClickListener(this::clicked);
        txt_3.setOnClickListener(this::clicked);
        txt_4.setOnClickListener(this::clicked);
        txt_5.setOnClickListener(this::clicked);
        txt_6.setOnClickListener(this::clicked);
        txt_7.setOnClickListener(this::clicked);
        txt_8.setOnClickListener(this::clicked);
        txt_9.setOnClickListener(this::clicked);
        txt_0.setOnClickListener(this::clicked);
        RemoveLast.setOnClickListener(this::RemoveLast);

    }


    @OnClick({R.id.text_1, R.id.text_2, R.id.text_3, R.id.text_4, R.id.text_5, R.id.text_6, R.id.text_7, R.id.text_8, R.id.text_9, R.id.text_0, R.id.text_00})
    void clicked(View view) {
        if (value.length() >= lenght) {
            return;
        }


        switch (view.getId()) {

            case R.id.text_1:
                val = "1";

                value = value + "" + val;
                TextInput(value);


                break;
            case R.id.text_2:
                val = "2";
                value = value + "" + val;
                TextInput(value);


                break;
            case R.id.text_3:
                val = "3";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_4:
                val = "4";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_5:
                val = "5";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_6:
                val = "6";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_7:
                val = "7";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_8:
                val = "8";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_9:
                val = "9";
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_0:
                val = "0";
                if (value.isEmpty()) {
                    return;
                }
                value = value + "" + val;

                TextInput(value);

                break;
            case R.id.text_00:
                val = "00";
                if (value.isEmpty()) {
                    return;
                }
                //fixing number losing format bug
                if (value.length() == 8) {
                    value = value + "" + "0";
                } else {
                    value = value + "" + val;
                }


                TextInput(value);

                break;
        }
    }


    @OnClick(R.id.RemoveLast)
    public void RemoveLast(View view) {
        String str = value;
        if (str != null && str.length() > 0) {
            str = str.substring(0, str.length() - 1);
        }
        value = str;

        try {
            TextInput(str);

        } catch (Exception e) {

        }

    }

    public void TextInput(String view) {
        try {
            itemClickListener.setPriceInEditText(view);

        } catch (Exception e) {
            System.out.println(e);
        }
    }


    public interface ItemClickListener {
        void setPriceInEditText(String s);


    }
}
