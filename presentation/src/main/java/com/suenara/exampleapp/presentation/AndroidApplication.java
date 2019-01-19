package com.suenara.exampleapp.presentation;

import android.app.Application;

import com.suenara.exampleapp.presentation.internal.di.components.ApplicationComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerApplicationComponent;
import com.suenara.exampleapp.presentation.internal.di.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

}
