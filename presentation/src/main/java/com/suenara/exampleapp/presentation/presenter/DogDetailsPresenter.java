package com.suenara.exampleapp.presentation.presenter;

import androidx.annotation.NonNull;

import com.suenara.exampleapp.domain.entity.Dog;
import com.suenara.exampleapp.domain.exception.DefaultErrorBundle;
import com.suenara.exampleapp.domain.exception.ErrorBundle;
import com.suenara.exampleapp.domain.interactor.DefaultObserver;
import com.suenara.exampleapp.presentation.exception.ErrorMessageFactory;
import com.suenara.exampleapp.presentation.internal.di.PerActivity;
import com.suenara.exampleapp.presentation.mapper.DogDataMapper;
import com.suenara.exampleapp.presentation.model.DogModel;
import com.suenara.exampleapp.presentation.view.DogDetailsView;

import javax.inject.Inject;


@PerActivity
public class DogDetailsPresenter implements Presenter {

    private final DogDataMapper dogDataMapper;

    private DogDetailsView viewDetailsView;

    @Inject
    public DogDetailsPresenter(DogDataMapper dogDataMapper) {
        this.dogDataMapper = dogDataMapper;
    }

    public void setView(@NonNull DogDetailsView view) {
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

    public void initialize(DogModel dogModel) {
        hideViewRetry();
        showViewLoading();
        getDogDetails(dogModel);
    }

    private void getDogDetails(DogModel dogModel) {
        showDogDetailsInView(new Dog(dogModel.getTitle(), dogModel.getUrl()));
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

    private void showDogDetailsInView(Dog dog) {
        DogModel dogModel = dogDataMapper.map(dog);
        viewDetailsView.renderDog(dogModel);
    }

    private final class DogDetailsObserver extends DefaultObserver<Dog> {
        @Override
        public void onNext(Dog dog) {
            DogDetailsPresenter.this.showDogDetailsInView(dog);
        }

        @Override
        public void onError(Throwable e) {
            DogDetailsPresenter.this.hideViewLoading();
            DogDetailsPresenter.this.showErrorMessage(new DefaultErrorBundle((Exception)e));
            DogDetailsPresenter.this.showViewRetry();
        }

        @Override
        public void onComplete() {
            DogDetailsPresenter.this.hideViewLoading();
        }
    }
}
