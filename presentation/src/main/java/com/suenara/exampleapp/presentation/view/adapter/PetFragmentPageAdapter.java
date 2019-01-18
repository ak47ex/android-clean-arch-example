package com.suenara.exampleapp.presentation.view.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.suenara.exampleapp.presentation.view.fragment.CatListFragment;
import com.suenara.exampleapp.presentation.view.fragment.DogListFragment;

import java.util.Locale;

public class PetFragmentPageAdapter extends FragmentPagerAdapter {

    private static final int PAGE_COUNT = 2;

    private static final int CAT_POSITION = 0;
    private static final int DOG_POSITION = 1;


    public PetFragmentPageAdapter(FragmentManager fm, Context context) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case CAT_POSITION: return new CatListFragment();
            case DOG_POSITION: return new DogListFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return String.format(Locale.ENGLISH, "Page %d", (position + 1));
    }
}
