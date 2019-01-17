package com.suenara.exampleapp.presentation.presenter;

import com.suenara.exampleapp.domain.entity.Dog;
import com.suenara.exampleapp.domain.exception.DefaultErrorBundle;
import com.suenara.exampleapp.domain.exception.ErrorBundle;
import com.suenara.exampleapp.domain.interactor.DefaultObserver;
import com.suenara.exampleapp.domain.interactor.GetDogList;
import com.suenara.exampleapp.presentation.exception.ErrorMessageFactory;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.mapper.DogDataMapper;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.DogListView;

import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;

@PerActivity
public class DogListPresenter implements Presenter {

    private DogListView viewListView;

    private final GetDogList getDogListUseCase;
    private final DogDataMapper dogDataMapper;

    @Inject
    public DogListPresenter(GetDogList getDogListUseCase, DogDataMapper dogDataMapper) {
        this.getDogListUseCase = getDogListUseCase;
        this.dogDataMapper = dogDataMapper;
    }

    public void setView(@NonNull DogListView view) {
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
        getDogListUseCase.dispose();
        viewListView = null;
    }

    public void initialize() {
        loadDogList();
    }

    public void onDogClicked(DogModel dogModel) {
        viewListView.viewDog(dogModel);
    }

    private void loadDogList() {
        hideViewRetry();
        showViewLoading();
        getDogList();
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

    private void showDogCollectionInView(Collection<Dog> dogsCollection) {
        Collection<DogModel> dogModelCollection = dogDataMapper.map(dogsCollection);
        viewListView.renderDogList(dogModelCollection);
    }

    private void getDogList() {
        getDogListUseCase.execute(new DogListObserver(), null);
    }

    private final class DogListObserver extends DefaultObserver<List<Dog>> {
        @Override
        public void onNext(List<Dog> dogs) {
            DogListPresenter.this.showDogCollectionInView(dogs);
        }

        @Override
        public void onError(Throwable e) {
            DogListPresenter.this.hideViewLoading();
            DogListPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception)e));
            DogListPresenter.this.showViewRetry();
        }

        @Override
        public void onComplete() {
            DogListPresenter.this.hideViewLoading();
        }
    }
}
