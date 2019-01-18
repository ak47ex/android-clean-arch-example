package com.suenara.exampleapp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.presenter.DogListPresenter;
import com.suenara.exampleapp.presentation.view.DogListView;
import com.suenara.exampleapp.presentation.view.adapter.DogsAdapter;
import com.suenara.exampleapp.presentation.view.adapter.DogsLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DogListFragment extends BaseFragment implements DogListView {

    public interface DogListListener {
        void onDogClicked(DogModel dogModel);
    }

    private static final String RV_STATE = "rv_state";

    private Unbinder unbinder;
    private Parcelable rvState;

    @Inject DogListPresenter dogListPresenter;
    @Inject DogsAdapter dogsAdapter;

    @BindView(R.id.rv_dogs) RecyclerView rv_dogs;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.bt_retry) Button bt_retry;

    private DogListListener dogListListener;

    private DogsAdapter.OnItemClickListener onItemClickListener = dogModel -> {
        if (dogListPresenter != null && dogModel != null) {
            dogListPresenter.onDogClicked(dogModel);
        }
    };

    public DogListFragment() { }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DogListListener) {
            this.dogListListener = (DogListListener)context;
        }
    }


    @Override
    protected boolean onInjectView() throws IllegalStateException {
        getComponent(PetComponent.class).inject(this);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_dog_list, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    protected void onViewInjected(Bundle savedInstanceState) {
        super.onViewInjected(savedInstanceState);
        setupRecyclerView();
        dogListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadDogList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dogListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dogListPresenter.pause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        Parcelable recylerViewState = rv_dogs.getLayoutManager().onSaveInstanceState();
        outState.putParcelable(RV_STATE, recylerViewState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            rvState = savedInstanceState.getParcelable(RV_STATE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_dogs.setAdapter(null);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dogListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        dogListListener = null;
    }

    @Override
    public void showLoading() {
        rl_progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        rl_progress.setVisibility(View.GONE);
    }

    @Override
    public void showRetry() {
        rl_retry.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideRetry() {
        rl_retry.setVisibility(View.GONE);
    }

    @Override
    public void renderDogList(Collection<DogModel> dogModelCollection) {
        if (dogModelCollection == null) {
            return;
        }

        dogsAdapter.setDogsCollection(dogModelCollection);
        if (rvState != null) {
            rv_dogs.getLayoutManager().onRestoreInstanceState(rvState);
            rvState = null;
        }
    }

    @Override
    public void viewDog(DogModel dogModel) {
        if (dogModel == null) {
            return;
        }

        dogListListener.onDogClicked(dogModel);
    }

    @Override
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    private void setupRecyclerView() {
        dogsAdapter.setOnItemClickListener(onItemClickListener);
        rv_dogs.setLayoutManager(new DogsLayoutManager(context()));
        rv_dogs.setAdapter(dogsAdapter);
    }

    private void loadDogList() {
        dogListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadDogList();
    }

}
