package com.suenara.exampleapp.presentation.presenter;

import androidx.annotation.NonNull;

import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.domain.exception.DefaultErrorBundle;
import com.suenara.exampleapp.domain.exception.ErrorBundle;
import com.suenara.exampleapp.domain.interactor.DefaultObserver;
import com.suenara.exampleapp.presentation.exception.ErrorMessageFactory;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.mapper.CatDataMapper;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.view.CatDetailsView;

import javax.inject.Inject;


@PerActivity
public class CatDetailsPresenter implements Presenter {

    private final CatDataMapper catDataMapper;

    private CatDetailsView viewDetailsView;

    @Inject
    public CatDetailsPresenter(CatDataMapper catDataMapper) {
        this.catDataMapper = catDataMapper;
    }

    public void setView(@NonNull CatDetailsView view) {
        viewDetailsView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        viewDetailsView = null;
    }

    public void initialize(CatModel catModel) {
        hideViewRetry();
        showViewLoading();
        getCatDetails(catModel);
    }

    private void getCatDetails(CatModel catModel) {
        showCatDetailsInView(new Cat(catModel.getTitle(), catModel.getUrl()));
        hideViewLoading();
    }

    private void showViewLoading() {
        viewDetailsView.showLoading();
    }

    private void hideViewLoading() {
        viewDetailsView.hideLoading();
    }

    private void showViewRetry() {
        viewDetailsView.showRetry();
    }

    private void hideViewRetry() {
        viewDetailsView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(viewDetailsView.context(), errorBundle.getException());
        viewDetailsView.showError(errorMessage);
    }

    private void showCatDetailsInView(Cat cat) {
        CatModel catModel = catDataMapper.map(cat);
        viewDetailsView.renderCat(catModel);
    }

    private final class CatDetailsObserver extends DefaultObserver<Cat> {
        @Override
        public void onNext(Cat cat) {
            CatDetailsPresenter.this.showCatDetailsInView(cat);
        }

        @Override
        public void onError(Throwable e) {
            CatDetailsPresenter.this.hideViewLoading();
            CatDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception)e));
            CatDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onComplete() {
            CatDetailsPresenter.this.hideViewLoading();
        }
    }
}
