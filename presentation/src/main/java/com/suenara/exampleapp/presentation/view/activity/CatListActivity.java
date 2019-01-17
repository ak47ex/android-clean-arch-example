package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.CatComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerCatComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;

public class CatListActivity extends BaseActivity implements HasComponent<CatComponent>,
        CatListFragment.CatListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, CatListActivity.class);
    }

    private CatComponent component;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_layout);

        initializeInjector();
        if (savedInstance == null) {
            addFragment(R.id.fragmentContainer, new CatListFragment());
        }
    }

    @Override
    public CatComponent getComponent() {
        return component;
    }

    @Override
    public void onCatClicked(CatModel catModel) {
        navigator.navigateToCatDetails(this, catModel);
    }

    private void initializeInjector() {
        component = DaggerCatComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}