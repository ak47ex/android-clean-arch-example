package com.suenara.exampleapp.presentation.internal.di.components;

import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.internal.di.modules.ActivityModule;
import com.suenara.exampleapp.presentation.internal.di.modules.DogModule;
import com.suenara.exampleapp.presentation.view.fragment.DogDetailsFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, DogModule.class})
public interface DogComponent extends ActivityComponent {

    void inject(DogListFragment dogListFragment);
    void inject(DogDetailsFragment dogDetailsFragment);
    
}
