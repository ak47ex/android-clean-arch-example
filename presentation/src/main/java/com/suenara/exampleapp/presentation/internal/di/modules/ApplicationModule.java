package com.suenara.exampleapp.presentation.internal.di.modules;

import android.content.Context;

import com.suenara.exampleapp.data.executor.JobExecutor;
import com.suenara.exampleapp.data.repository.PetDataRepository;
import com.suenara.exampleapp.domain.executor.PostExecutionThread;
import com.suenara.exampleapp.domain.executor.ThreadExecutor;
import com.suenara.exampleapp.domain.repository.PetRepository;
import com.suenara.exampleapp.presentation.AndroidApplication;
import com.suenara.exampleapp.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final AndroidApplication application;

    public ApplicationModule(AndroidApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    PetRepository providePetRepository(PetDataRepository petDataRepository) {
        return petDataRepository;
    }


}
