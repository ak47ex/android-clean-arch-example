package com.suenara.exampleapp.presentation.navigator;

import android.content.Context;
import android.content.Intent;

import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.activity.CatDetailsActivity;
import com.suenara.exampleapp.presentation.view.activity.CatListActivity;
import com.suenara.exampleapp.presentation.view.activity.DogDetailsActivity;
import com.suenara.exampleapp.presentation.view.activity.DogListActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToCatList(Context context) {
        if (context == null) {
            return;
        }

        Intent intentToLaunch = CatListActivity.getCallingIntent(context);
        context.startActivity(intentToLaunch);
    }

    public void navigateToDogList(Context context) {
        if (context == null) {
            return;
        }

        Intent intentToLaunch = DogListActivity.getCallingIntent(context);
        context.startActivity(intentToLaunch);
    }

    public void navigateToCatDetails(Context context, CatModel catModel) {
        if (context == null) {
            return;
        }

        Intent intentToLaunch = CatDetailsActivity.getCallingIntent(context, catModel);
        context.startActivity(intentToLaunch);
    }

    public void navigateToDogDetails(Context context, DogModel dogModel) {
        if (context == null) {
            return;
        }

        Intent intentToLaunch = DogDetailsActivity.getCallingIntent(context, dogModel);
        context.startActivity(intentToLaunch);
    }
}
