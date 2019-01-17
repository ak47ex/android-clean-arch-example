package com.suenara.exampleapp.presentation;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.suenara.exampleapp.presentation.internal.di.components.ApplicationComponent;
import com.suenara.exampleapp.presentation.internal.di.components.DaggerApplicationComponent;
import com.suenara.exampleapp.presentation.internal.di.modules.ApplicationModule;

public class AndroidApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initializeInjector();
        initializeLeakDetection();
    }

    private void initializeInjector() {
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    private void initializeLeakDetection() {
        if (BuildConfig.DEBUG) {
            LeakCanary.install(this);
        }
    }

}
