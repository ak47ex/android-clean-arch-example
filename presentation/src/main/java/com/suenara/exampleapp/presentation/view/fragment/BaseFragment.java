package com.suenara.exampleapp.presentation.view.fragment;

import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.suenara.exampleapp.presentation.internal.di.HasComponent;

public abstract class BaseFragment extends Fragment {

    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) {
        return componentType.cast(((HasComponent<C>)getActivity()).getComponent());
    }
}
