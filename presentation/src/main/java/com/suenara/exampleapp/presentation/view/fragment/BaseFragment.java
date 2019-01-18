package com.suenara.exampleapp.presentation.view.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.suenara.exampleapp.presentation.internal.di.HasComponent;

public abstract class BaseFragment extends Fragment {

    private boolean isInjected = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            isInjected = onInjectView();
        } catch (IllegalStateException e) {
            Log.e(e.getClass().getSimpleName(), e.getMessage());
            isInjected = false;
        }
    }

    @Override public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (isInjected) onViewInjected(savedInstanceState);
    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (!isInjected) {
            isInjected = onInjectView();
            if (isInjected) onViewInjected(null);
        }
    }


    protected void showToastMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @SuppressWarnings("unchecked")
    protected <C> C getComponent(Class<C> componentType) throws IllegalStateException {
        C component = componentType.cast(((HasComponent<C>) getActivity()).getComponent());
        if (component == null) {
            throw new IllegalStateException(componentType.getSimpleName() + " has not been initialized yet.");
        }
        return component;
    }

    protected boolean onInjectView() throws IllegalStateException {
        // Return false by default.
        return false;
    }

    protected void onViewInjected(Bundle savedInstanceState) {
        // Intentionally left empty.
    }
}
