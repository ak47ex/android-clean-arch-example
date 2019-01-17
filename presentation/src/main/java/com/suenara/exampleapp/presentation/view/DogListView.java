package com.suenara.exampleapp.presentation.view;

import com.suenara.exampleapp.presentation.model.DogModel;

import java.util.Collection;

public interface DogListView extends LoadDataView {

    void renderDogList(Collection<DogModel> dogModelCollection);

    void viewDog(DogModel dogModel);
    
}
