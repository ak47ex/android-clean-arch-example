package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.CatComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerDogComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DogComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

public class DogListActivity extends BaseActivity implements HasComponent<DogComponent>,
        DogListFragment.DogListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DogListActivity.class);
    }

    private DogComponent component;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_layout);

        initializeInjector();
        if (savedInstance == null) {
            addFragment(R.id.fragmentContainer, new DogListFragment());
        }
    }

    @Override
    public DogComponent getComponent() {
        return component;
    }

    @Override
    public void onDogClicked(DogModel dogModel) {
        navigator.navigateToDogDetails(this, dogModel);
    }

    private void initializeInjector() {
        component = DaggerDogComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
