package com.suenara.exampleapp.presentation.view.activity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.suenara.exampleapp.presentation.AndroidApplication;
import com.suenara.exampleapp.presentation.internal.di.components.ApplicationComponent;
import com.suenara.exampleapp.presentation.internal.di.modules.ActivityModule;
import com.suenara.exampleapp.presentation.navigator.Navigator;

import javax.inject.Inject;

public abstract class BaseActivity extends FragmentActivity {

    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        getApplicationComponent().inject(this);
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
