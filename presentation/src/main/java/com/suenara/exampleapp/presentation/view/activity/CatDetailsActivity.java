package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.CatComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerCatComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.view.fragment.CatDetailsFragment;

public class CatDetailsActivity extends BaseActivity implements HasComponent<CatComponent> {

    private static final String INTENT_EXTRA_PARAM_URL = "com.suenara.INTENT_PARAM_URL";
    private static final String INTENT_EXTRA_PARAM_TITLE = "com.suenara.INTENT_PARAM_TITLE";

    private static final String INSTANCE_STATE_PARAM_URL = "com.suenara.STATE_PARAM_URL";
    private static final String INSTANCE_STATE_PARAM_TITLE = "com.suenara.STATE_PARAM_TITLE";


    public static Intent getCallingIntent(Context context, CatModel catModel) {
        Intent intent = new Intent(context, CatDetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_TITLE, catModel.getTitle());
        intent.putExtra(INTENT_EXTRA_PARAM_URL, catModel.getUrl());
        return intent;
    }

    private CatModel catModel;
    private CatComponent catComponent;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_layout);

        initializeActivity(savedInstance);
        initializeInjector();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_TITLE, catModel.getTitle());
            outState.putString(INSTANCE_STATE_PARAM_URL, catModel.getUrl());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public CatComponent getComponent() {
        return catComponent;
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            String title = getIntent().getStringExtra(INTENT_EXTRA_PARAM_TITLE);
            String url = getIntent().getStringExtra(INTENT_EXTRA_PARAM_URL);

            catModel = new CatModel(title, url);
            addFragment(R.id.fragmentContainer, CatDetailsFragment.forCat(catModel));
        } else {
            String title = savedInstanceState.getString(INSTANCE_STATE_PARAM_TITLE);
            String url = savedInstanceState.getString(INSTANCE_STATE_PARAM_URL);
            catModel = new CatModel(title, url);
        }
    }

    private void initializeInjector() {
        catComponent = DaggerCatComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
