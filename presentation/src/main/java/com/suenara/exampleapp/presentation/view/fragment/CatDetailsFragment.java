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
import com.suenara.exampleapp.presentation.internal.di.components.PetComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.presenter.CatDetailsPresenter;
import com.suenara.exampleapp.presentation.view.CatDetailsView;
import com.suenara.exampleapp.presentation.view.component.AutoLoadImageView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CatDetailsFragment extends BaseFragment implements CatDetailsView {

    private static final String PARAM_URL_KEY = "param_url";
    private static final String PARAM_TITLE_KEY = "param_title";

    private Unbinder unbinder;

    @Inject CatDetailsPresenter catDetailsPresenter;

    @BindView(R.id.iv_cover) AutoLoadImageView iv_cover;
    @BindView(R.id.tv_title) TextView tv_title;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.bt_retry) Button bt_retry;

    public static CatDetailsFragment forCat(CatModel catModel) {
        CatDetailsFragment catDetailsFragment = new CatDetailsFragment();
        Bundle arguments = new Bundle();
        arguments.putString(PARAM_TITLE_KEY, catModel.getTitle());
        arguments.putString(PARAM_URL_KEY, catModel.getUrl());
        catDetailsFragment.setArguments(arguments);
        return catDetailsFragment;
    }

    public CatDetailsFragment() { }


    @Override
    protected boolean onInjectView() throws IllegalStateException {
        getComponent(PetComponent.class).inject(this);
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cat_details, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    @Override
    protected void onViewInjected(Bundle savedInstanceState) {
        super.onViewInjected(savedInstanceState);
        catDetailsPresenter.setView(this);
        if (savedInstanceState == null) {
            loadCatDetails();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        catDetailsPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        catDetailsPresenter.pause();
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
        catDetailsPresenter.destroy();
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
    public void renderCat(CatModel cat) {
        if (cat == null) {
            return;
        }

        iv_cover.setImageUrl(cat.getUrl());
        tv_title.setText(cat.getTitle());
    }

    private void loadCatDetails() {
        if (catDetailsPresenter == null) {
            return;
        }

        catDetailsPresenter.initialize(currentCat());
    }

    private CatModel currentCat() {
        final Bundle arguments = getArguments();
        Objects.requireNonNull(arguments, "Fragment arguments cannot be null");

        CatModel model = new CatModel(arguments.getString(PARAM_TITLE_KEY), arguments.getString(PARAM_URL_KEY));
        return model;
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadCatDetails();
    }


}
