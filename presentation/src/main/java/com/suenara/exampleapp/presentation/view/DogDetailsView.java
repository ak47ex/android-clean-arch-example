package com.suenara.exampleapp.presentation.view;

import com.suenara.exampleapp.presentation.model.DogModel;

public interface DogDetailsView extends LoadDataView {

    void renderDog(DogModel dog);

}
