package com.suenara.exampleapp.presentation.view.activity;

import android.os.Bundle;
import android.widget.Button;

import com.suenara.exampleapp.presentation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_LoadData) Button btn_LoadData;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_LoadData)
    void navigateToCatList() {
        navigator.navigateToCatList(this);
    }
}
