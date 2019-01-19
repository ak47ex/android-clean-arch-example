package com.suenara.exampleapp.presentation.navigator;

import android.content.Context;
import android.content.Intent;

import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.activity.CatDetailsActivity;
import com.suenara.exampleapp.presentation.view.activity.DogDetailsActivity;
import com.suenara.exampleapp.presentation.view.activity.MainActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Navigator {

    @Inject
    public Navigator() {}

    public void navigateToPets(Context context) {
        if (context == null) {
            return;
        }

        Intent intentToLaunch = MainActivity.getCallingIntent(context);
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
