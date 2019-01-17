package com.suenara.exampleapp.presentation.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.suenara.exampleapp.presentation.R;
import com.suenara.exampleapp.presentation.internal.di.components.CatComponent;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.presenter.CatListPresenter;
import com.suenara.exampleapp.presentation.view.CatListView;
import com.suenara.exampleapp.presentation.view.adapter.CatsAdapter;
import com.suenara.exampleapp.presentation.view.adapter.CatsLayoutManager;

import java.util.Collection;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class CatListFragment extends BaseFragment implements CatListView {

    public interface CatListListener {
        void onCatClicked(CatModel catModel);
    }

    private Unbinder unbinder;

    @Inject CatListPresenter catListPresenter;
    @Inject CatsAdapter catsAdapter;

    @BindView(R.id.rv_cats) RecyclerView rv_cats;
    @BindView(R.id.rl_progress) RelativeLayout rl_progress;
    @BindView(R.id.rl_retry) RelativeLayout rl_retry;
    @BindView(R.id.bt_retry) Button bt_retry;

    private CatListListener catListListener;

    private CatsAdapter.OnItemClickListener onItemClickListener = catModel -> {
        if (catListPresenter != null && catModel != null) {
            catListPresenter.onCatClicked(catModel);
        }
    };

    public CatListFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CatListListener) {
            this.catListListener = (CatListListener)context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getComponent(CatComponent.class).inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_cat_list, container, false);
        unbinder = ButterKnife.bind(this, fragmentView);
        setupRecyclerView();
        return fragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        catListPresenter.setView(this);
        if (savedInstanceState == null) {
            loadCatList();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        catListPresenter.resume();
    }

    @Override
    public void onPause() {
        super.onPause();
        catListPresenter.pause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rv_cats.setAdapter(null);
        if (unbinder != null) {
            unbinder.unbind();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        catListPresenter.destroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        catListListener = null;
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
    public void renderCatList(Collection<CatModel> catModelCollection) {
        if (catModelCollection == null) {
            return;
        }

        catsAdapter.setCatsCollection(catModelCollection);
        rv_cats.getAdapter().notifyDataSetChanged();
        rv_cats.scheduleLayoutAnimation();
    }

    @Override
    public void viewCat(CatModel catModel) {
        if (catModel == null) {
            return;
        }

        catListListener.onCatClicked(catModel);
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
        catsAdapter.setOnItemClickListener(onItemClickListener);
        rv_cats.setLayoutManager(new CatsLayoutManager(context()));
        rv_cats.setAdapter(catsAdapter);
        DividerItemDecoration divider = new DividerItemDecoration(rv_cats.getContext(), DividerItemDecoration.HORIZONTAL);
        rv_cats.addItemDecoration(divider);

        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(rv_cats.getContext(), resId);
        rv_cats.setLayoutAnimation(animation);
    }

    private void loadCatList() {
        catListPresenter.initialize();
    }

    @OnClick(R.id.bt_retry)
    void onButtonRetryClick() {
        loadCatList();
    }

}
