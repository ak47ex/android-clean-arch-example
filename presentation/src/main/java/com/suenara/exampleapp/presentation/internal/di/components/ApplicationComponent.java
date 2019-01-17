package com.suenara.exampleapp.presentation.internal.di.components;

import android.content.Context;

import com.suenara.exampleapp.domain.executor.PostExecutionThread;
import com.suenara.exampleapp.domain.executor.ThreadExecutor;
import com.suenara.exampleapp.domain.repository.PetRepository;
import com.suenara.exampleapp.presentation.internal.di.modules.ApplicationModule;
import com.suenara.exampleapp.presentation.view.activity.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(BaseActivity baseActivity);

    Context context();

    ThreadExecutor threadExecutor();

    PostExecutionThread postExecutionThread();

    PetRepository petRepository();

}
