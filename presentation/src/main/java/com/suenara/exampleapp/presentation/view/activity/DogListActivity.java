package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerPetComponent;
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

public class DogListActivity extends BaseActivity implements HasComponent<PetComponent>,
        DogListFragment.DogListListener {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, DogListActivity.class);
    }

    private PetComponent component;

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
    public PetComponent getComponent() {
        return component;
    }

    @Override
    public void onDogClicked(DogModel dogModel) {
        navigator.navigateToDogDetails(this, dogModel);
    }

    private void initializeInjector() {
        component = DaggerPetComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
