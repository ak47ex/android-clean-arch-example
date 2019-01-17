package com.suenara.exampleapp.presentation.internal.di.components;

import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.internal.di.modules.ActivityModule;
import com.suenara.exampleapp.presentation.internal.di.modules.CatModule;
import com.suenara.exampleapp.presentation.view.fragment.CatDetailsFragment;
import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;

import dagger.Component;

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, CatModule.class})
public interface CatComponent extends ActivityComponent {

    void inject(CatListFragment catListFragment);

    void inject(CatDetailsFragment catDetailsFragment);

}
