package com.suenara.exampleapp.presentation.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;
import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.HasComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerPetComponent;
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements HasComponent<PetComponent>,
        CatListFragment.CatListListener, DogListFragment.DogListListener,
        TabLayout.OnTabSelectedListener {

    private static final String STATE_ACTIVE_TAB = "tl_active_tab";
    private static final String STATE_CAT_FRAGMENT = "fl_cat_fragment";
    private static final String STATE_DOG_FRAGMENT = "fl_dog_fragment";

    private Unbinder unbinder;

    private PetComponent petComponent;


    @BindView(R.id.fl_container) FrameLayout fl_container;
    @BindView(R.id.tl_main) TabLayout tl_main;

    private Fragment catList;
    private Fragment dogList;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);

        if (savedInstance == null) {
            catList = new CatListFragment();
            dogList = new DogListFragment();
            addFragment(fl_container, catList);
            addFragment(fl_container, dogList);
            hideFragment(dogList);
            hideFragment(catList);
        } else {
            catList = getSupportFragmentManager().getFragment(savedInstance, STATE_CAT_FRAGMENT);
            dogList = getSupportFragmentManager().getFragment(savedInstance, STATE_DOG_FRAGMENT);
        }

        initializeInjector();

        prepareTabLayout();

        if (savedInstance != null) {
            if (savedInstance.containsKey(STATE_ACTIVE_TAB)) {
                tl_main.getTabAt(savedInstance.getInt(STATE_ACTIVE_TAB)).select();
            }
        }

    }

    private void prepareTabLayout() {
        tl_main.addOnTabSelectedListener(this);
        tl_main.addTab(tl_main.newTab().setText(R.string.tab_cats).setIcon(R.drawable.cat_icon));
        tl_main.addTab(tl_main.newTab().setText(R.string.tab_dogs).setIcon(R.drawable.dog_icon));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_ACTIVE_TAB, tl_main.getSelectedTabPosition());

        getSupportFragmentManager().putFragment(outState, STATE_CAT_FRAGMENT, catList);
        getSupportFragmentManager().putFragment(outState, STATE_DOG_FRAGMENT, dogList);
    }

    @Override
    public PetComponent getComponent() {
        return petComponent;
    }

    //region Listeners

    @Override
    public void onCatClicked(CatModel catModel) {
        navigator.navigateToCatDetails(this, catModel);
    }

    @Override
    public void onDogClicked(DogModel dogModel) {
        navigator.navigateToDogDetails(this, dogModel);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                hideFragment(dogList);
                showFragment(catList);
                break;
            case 1:
                hideFragment(catList);
                showFragment(dogList);
                break;
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        switch (tab.getPosition()) {
            case 0:
                hideFragment(dogList);
                break;
            case 1:
                hideFragment(catList);
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    //endregion

    private void initializeInjector() {
        petComponent = DaggerPetComponent.builder()
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
