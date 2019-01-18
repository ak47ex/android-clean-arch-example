package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerPetComponent;
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.fragment.DogDetailsFragment;

public class DogDetailsActivity extends BaseActivity implements HasComponent<PetComponent> {

    private static final String INTENT_EXTRA_PARAM_URL = "com.suenara.INTENT_PARAM_URL";
    private static final String INTENT_EXTRA_PARAM_TITLE = "com.suenara.INTENT_PARAM_TITLE";

    private static final String INSTANCE_STATE_PARAM_URL = "com.suenara.STATE_PARAM_URL";
    private static final String INSTANCE_STATE_PARAM_TITLE = "com.suenara.STATE_PARAM_TITLE";


    public static Intent getCallingIntent(Context context, DogModel dogModel) {
        Intent intent = new Intent(context, DogDetailsActivity.class);
        intent.putExtra(INTENT_EXTRA_PARAM_TITLE, dogModel.getTitle());
        intent.putExtra(INTENT_EXTRA_PARAM_URL, dogModel.getUrl());
        return intent;
    }

    private DogModel dogModel;
    private PetComponent petComponent;

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
            outState.putString(INSTANCE_STATE_PARAM_TITLE, dogModel.getTitle());
            outState.putString(INSTANCE_STATE_PARAM_URL, dogModel.getUrl());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public PetComponent getComponent() {
        return petComponent;
    }

    private void initializeActivity(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            String title = getIntent().getStringExtra(INTENT_EXTRA_PARAM_TITLE);
            String url = getIntent().getStringExtra(INTENT_EXTRA_PARAM_URL);

            dogModel = new DogModel(title, url);
            addFragment(R.id.fragmentContainer, DogDetailsFragment.forDog(dogModel));
        } else {
            String title = savedInstanceState.getString(INSTANCE_STATE_PARAM_TITLE);
            String url = savedInstanceState.getString(INSTANCE_STATE_PARAM_URL);
            dogModel = new DogModel(title, url);
        }
    }

    private void initializeInjector() {
        petComponent = DaggerPetComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }
}
