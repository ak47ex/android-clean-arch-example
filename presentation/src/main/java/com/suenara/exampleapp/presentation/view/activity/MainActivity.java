package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerPetComponent;
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.adapter.PetFragmentPageAdapter;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements HasComponent<PetComponent>,
        CatListFragment.CatListListener, DogListFragment.DogListListener {

    private Unbinder unbinder;

    private PetComponent catComponent;


    @BindView(R.id.vp_tabContainer) ViewPager vp_tabContainer;
    @BindView(R.id.tl_main) TabLayout tl_main;


    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        initializeInjector();

        vp_tabContainer.setAdapter(new PetFragmentPageAdapter(getSupportFragmentManager(), this));
        tl_main.setupWithViewPager(vp_tabContainer);


    }

    @Override
    public PetComponent getComponent() {
        return catComponent;
    }

    @Override
    public void onCatClicked(CatModel catModel) {
        navigator.navigateToCatDetails(this, catModel);
    }

    @Override
    public void onDogClicked(DogModel dogModel) {
        navigator.navigateToDogDetails(this, dogModel);
    }

    private void initializeInjector() {
        catComponent = DaggerPetComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
