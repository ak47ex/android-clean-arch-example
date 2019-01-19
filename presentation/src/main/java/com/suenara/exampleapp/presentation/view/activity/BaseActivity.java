package com.suenara.exampleapp.presentation.view.activity;

import android.os.Bundle;
import android.view.ViewGroup;

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

    //region Fragment interaction

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected void addFragment(ViewGroup containerView, Fragment fragment) {
        addFragment(containerView.getId(), fragment);
    }

    protected void replaceFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(containerViewId, fragment);
        fragmentTransaction.commit();
    }

    protected void replaceFragment(ViewGroup containerView, Fragment fragment) {
        replaceFragment(containerView.getId(), fragment);
    }

    protected void hideFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.hide(fragment);
        fragmentTransaction.commit();
    }

    protected void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.show(fragment);
        fragmentTransaction.commit();
    }

    //endregion

    protected ApplicationComponent getApplicationComponent() {
        return ((AndroidApplication)getApplication()).getApplicationComponent();
    }

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
