package com.suenara.exampleapp.presentation.internal.di.components;

import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.internal.di.modules.ActivityModule;
import com.suenara.exampleapp.presentation.internal.di.modules.CatModule;
import com.suenara.exampleapp.presentation.internal.di.modules.DogModule;
import com.suenara.exampleapp.presentation.view.fragment.CatDetailsFragment;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogDetailsFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CatModule.class, DogModule.class})
public interface PetComponent extends ActivityComponent {

    void inject(CatListFragment catListFragment);

    void inject(CatDetailsFragment catDetailsFragment);

    void inject(DogListFragment dogListFragment);

    void inject(DogDetailsFragment dogDetailsFragment);

}
