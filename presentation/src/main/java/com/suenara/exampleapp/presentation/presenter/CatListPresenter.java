package com.suenara.exampleapp.presentation.presenter;

import com.suenara.exampleapp.domain.entity.Cat;
import com.suenara.exampleapp.domain.exception.DefaultErrorBundle;
import com.suenara.exampleapp.domain.exception.ErrorBundle;
import com.suenara.exampleapp.domain.interactor.DefaultObserver;
import com.suenara.exampleapp.domain.interactor.GetCatList;
import com.suenara.exampleapp.presentation.exception.ErrorMessageFactory;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.mapper.CatDataMapper;
import com.suenara.exampleapp.presentation.model.CatModel;
import com.suenara.exampleapp.presentation.view.CatListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

@PerActivity
public class CatListPresenter implements Presenter {

    private CatListView viewListView;

    private final GetCatList getCatListUseCase;
    private final CatDataMapper catDataMapper;

    @Inject
    public CatListPresenter(GetCatList getCatListUseCase, CatDataMapper catDataMapper) {
        this.getCatListUseCase = getCatListUseCase;
        this.catDataMapper = catDataMapper;
    }

    public void setView(@NonNull CatListView view) {
        viewListView = view;
    }

    @Override
    public void resume() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void destroy() {
        getCatListUseCase.dispose();
        viewListView = null;
    }

    public void initialize() {
        loadCatList();
    }

    public void onCatClicked(CatModel catModel) {
        viewListView.viewCat(catModel);
    }

    private void loadCatList() {
        hideViewRetry();
        showViewLoading();
        getCatList();
    }

    private void showViewLoading() {
        viewListView.showLoading();
    }

    private void hideViewLoading() {
        viewListView.hideLoading();
    }

    private void showViewRetry() {
        viewListView.showRetry();
    }

    private void hideViewRetry() {
        viewListView.hideRetry();
    }

    private void showErrorMessage(ErrorBundle errorBundle) {
        String errorMessage = ErrorMessageFactory.create(viewListView.context(), errorBundle.getException());
        viewListView.showError(errorMessage);
    }

    private void showCatCollectionInView(Collection<Cat> catsCollection) {
        Collection<CatModel> catModelCollection = catDataMapper.map(catsCollection);
        viewListView.renderCatList(catModelCollection);
    }

    private void getCatList() {
        getCatListUseCase.execute(new CatListObserver(), null);
    }

    private final class CatListObserver extends DefaultObserver<List<Cat>> {
        @Override
        public void onNext(List<Cat> cats) {
            CatListPresenter.this.showCatCollectionInView(cats);
        }

        @Override
        public void onError(Throwable e) {
            CatListPresenter.this.hideViewLoading();
            CatListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception)e));
            CatListPresenter.this.showViewRetry();
        }

        @Override
        public void onComplete() {
            CatListPresenter.this.hideViewLoading();
        }
    }
}
