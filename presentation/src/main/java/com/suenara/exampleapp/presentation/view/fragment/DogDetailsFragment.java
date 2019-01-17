package com.suenara.exampleapp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.annimon.stream.Objects;
import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.components.DogComponent;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.presenter.DogDetailsPresenter;
import com.suenara.exampleapp.presentation.view.DogDetailsView;
import com.suenara.exampleapp.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class DogDetailsFragment extends BaseFragment implements DogDetailsView {

    private static final String PARAM_URL_KEY = "param_url";
    private static final String PARAM_TITLE_KEY = "param_title";

    private Unbinder unbinder;

    @Inject DogDetailsPresenter dogDetailsPresenter;

    @BindView(R.id.iv_cover) AutoLoadImageView iv_cover;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.bt_retry) Button bt_retry;

    public static DogDetailsFragment forDog(DogModel dogModel) {
        DogDetailsFragment dogDetailsFragment = new DogDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(PARAM_TITLE_KEY, dogModel.getTitle());
        arguments.putString(PARAM_URL_KEY, dogModel.getUrl());
        dogDetailsFragment.setArguments(arguments);
        return dogDetailsFragment;
    }

    public DogDetailsFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(DogComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_dog_details, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dogDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            loadDogDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        dogDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dogDetailsPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dogDetailsPresenter.destroy();
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
    public void showError(String message) {
        showToastMessage(message);
    }

    @Override
    public Context context() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void renderDog(DogModel dog) {
        if (dog == null) {
            return;
        }

        iv_cover.setImageUrl(dog.getUrl());
        tv_title.setText(dog.getTitle());
    }

    private void loadDogDetails() {
        if (dogDetailsPresenter == null) {
            return;
        }

        dogDetailsPresenter.initialize(currentDog());
    }

    private DogModel currentDog() {
        final Bundle arguments = getArguments();
        Objects.requireNonNull(arguments, "Fragment arguments cannot be null");

        DogModel model = new DogModel(arguments.getString(PARAM_TITLE_KEY), arguments.getString(PARAM_URL_KEY));
        return model;
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadDogDetails();
    }


}
